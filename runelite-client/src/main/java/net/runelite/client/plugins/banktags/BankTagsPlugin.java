/*
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.banktags;

import com.google.common.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.IntegerNode;
import net.runelite.api.Item;
import net.runelite.api.ItemComposition;
import net.runelite.api.ItemContainer;
import net.runelite.api.InventoryID;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.MenuOpened;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.ScriptCallbackEvent;
import net.runelite.api.events.WidgetHiddenChanged;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetConfig;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ChatboxInputManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
	name = "Bank Tags",
	description = "Enable tagging of bank items and searching of bank tags",
	tags = {"searching", "tagging"}
)
@Slf4j
public class BankTagsPlugin extends Plugin
{
	private static final String CONFIG_GROUP = "banktags";

	private static final String ITEM_KEY_PREFIX = "item_";
	private static final String PREVIOUS_TAG_KEY = "add_tag";

	private static final String SEARCH_BANK_INPUT_TEXT =
		"Show items whose names or tags contain the following text:<br>" +
		"(To show only tagged items, start your search with 'tag:')";

	private static final String SEARCH_BANK_INPUT_TEXT_FOUND =
		"Show items whose names or tags contain the following text: (%d found)<br>" +
			"(To show only tagged items, start your search with 'tag:')";

	private static final String TAG_SEARCH = "tag:";

	private static final String EDIT_TAGS_MENU_OPTION = "Edit-tags";
	private static final String ADD_PREVIOUS_TAG_MENU_OPTION = "Add-tag";

	private static final int EDIT_TAGS_MENU_INDEX = 8;
	private static final int ADD_PREVIOUS_TAG_MENU_INDEX = 9;

	@Inject
	private Client client;

	@Inject
	private ItemManager itemManager;

	@Inject
	private ConfigManager configManager;

	@Inject
	private ChatboxInputManager chatboxInputManager;

	private String getTags(int itemId)
	{
		String config = configManager.getConfiguration(CONFIG_GROUP, ITEM_KEY_PREFIX + itemId);
		if (config == null)
		{
			return "";
		}
		return config;
	}

	private void setTags(int itemId, String tags)
	{
		if (tags == null || tags.isEmpty())
		{
			configManager.unsetConfiguration(CONFIG_GROUP, ITEM_KEY_PREFIX + itemId);
		}
		else
		{
			configManager.setConfiguration(CONFIG_GROUP, ITEM_KEY_PREFIX + itemId, tags);
		}
	}

	private String getPrevTag()
	{
		String config = configManager.getConfiguration(CONFIG_GROUP, PREVIOUS_TAG_KEY);
		if (config == null)
		{
			return "";
		}
		return config;
	}

	private void setPrevTag(String tag)
	{
		if (tag == null || tag.isEmpty())
		{
			configManager.unsetConfiguration(CONFIG_GROUP, PREVIOUS_TAG_KEY);
		}
		else
		{
			configManager.setConfiguration(CONFIG_GROUP, PREVIOUS_TAG_KEY, tag);
		}
	}

	private int getTagCount(int itemId)
	{
		String tags = getTags(itemId);
		if (tags.length() > 0)
		{
			return tags.split(",").length;
		}
		return 0;
	}

	@Subscribe
	public void onWidgetHiddenChanged(WidgetHiddenChanged event)
	{
		Widget widget = event.getWidget();
		if (widget.getId() == WidgetInfo.BANK_TITLE_BAR.getId())
		{
			setPrevTag(null);
		}
	}

	@Subscribe
	public void onScriptEvent(ScriptCallbackEvent event)
	{
		String eventName = event.getEventName();

		int[] intStack = client.getIntStack();
		String[] stringStack = client.getStringStack();
		int intStackSize = client.getIntStackSize();
		int stringStackSize = client.getStringStackSize();

		switch (eventName)
		{
			case "bankTagsActive":
				// tell the script the bank tag plugin is active
				intStack[intStackSize - 1] = 1;
				break;
			case "setSearchBankInputText":
				stringStack[stringStackSize - 1] = SEARCH_BANK_INPUT_TEXT;
				break;
			case "setSearchBankInputTextFound":
			{
				int matches = intStack[intStackSize - 1];
				stringStack[stringStackSize - 1] = String.format(SEARCH_BANK_INPUT_TEXT_FOUND, matches);
				break;
			}
			case "setBankItemMenu":
			{
				// set menu action index so the edit tags option will not be overridden
				intStack[intStackSize - 3] = EDIT_TAGS_MENU_INDEX;
				intStack[intStackSize - 4] = ADD_PREVIOUS_TAG_MENU_INDEX;

				int itemId = intStack[intStackSize - 2];
				int tagCount = getTagCount(itemId);
				if (tagCount > 0)
				{
					stringStack[stringStackSize - 1] += " (" + tagCount + ")";
				}

				int index = intStack[intStackSize - 2];
				long key = (long) index + ((long) WidgetInfo.BANK_ITEM_CONTAINER.getId() << 32);
				IntegerNode flagNode = (IntegerNode) client.getWidgetFlags().get(key);
				if (flagNode != null && flagNode.getValue() != 0)
				{
					flagNode.setValue(flagNode.getValue() | WidgetConfig.SHOW_MENU_OPTION_NINE);
				}
				break;
			}
			case "bankSearchFilter":
				int itemId = intStack[intStackSize - 1];
				String itemName = stringStack[stringStackSize - 2];
				String searchInput = stringStack[stringStackSize - 1];

				ItemComposition itemComposition = itemManager.getItemComposition(itemId);
				if (itemComposition.getPlaceholderTemplateId() != -1)
				{
					// if the item is a placeholder then get the item id for the normal item
					itemId = itemComposition.getPlaceholderId();
				}

				String tagsConfig = configManager.getConfiguration(CONFIG_GROUP, ITEM_KEY_PREFIX + itemId);
				if (tagsConfig == null || tagsConfig.length() == 0)
				{
					intStack[intStackSize - 2] = itemName.contains(searchInput) ? 1 : 0;
					return;
				}

				boolean tagSearch = searchInput.startsWith(TAG_SEARCH);
				String search;
				if (tagSearch)
				{
					search = searchInput.substring(TAG_SEARCH.length()).trim();
				}
				else
				{
					search = searchInput;
				}

				List<String> tags = Arrays.asList(tagsConfig.toLowerCase().split(","));

				if (tags.stream().anyMatch(tag -> tag.contains(search.toLowerCase())))
				{
					// return true
					intStack[intStackSize - 2] = 1;
				}
				else if (!tagSearch)
				{
					intStack[intStackSize - 2] = itemName.contains(search) ? 1 : 0;
				}
				break;
		}
	}

	@Subscribe
	public void onMenuOpened(MenuOpened event)
	{
		MenuEntry firstEntry = event.getFirstEntry();
		int widgetId = firstEntry.getParam1();
		if (widgetId != WidgetInfo.BANK_ITEM_CONTAINER.getId())
		{
			return;
		}

		MenuEntry[] entries = event.getMenuEntries();
		if (entries.length <= ADD_PREVIOUS_TAG_MENU_INDEX)
		{
			return;
		}

		String prevTag = getPrevTag();
		int inventoryIndex = firstEntry.getParam0();
		Item item = getItemForInventoryIndex(inventoryIndex);
		String itemTags = getTags(item.getId());

		List<MenuEntry> entryList = Arrays.asList(entries);
		entryList = new ArrayList<>(entryList);

		// Remove Add Tag menu option if prevTag doesnt exist
		if (prevTag == null || prevTag.isEmpty())
		{
			entryList.remove(entries.length - ADD_PREVIOUS_TAG_MENU_INDEX);
		}
		// Remove Add Tag menu option if prevTag already is on item
		else if (Arrays.asList(itemTags.replaceAll("\\s", "").split(",")).contains(prevTag))
		{
			entryList.remove(entries.length - ADD_PREVIOUS_TAG_MENU_INDEX);
		}
		else
		{
			MenuEntry prevTagMenuOption = entries[entries.length - ADD_PREVIOUS_TAG_MENU_INDEX];
			if (prevTagMenuOption.getOption().startsWith(ADD_PREVIOUS_TAG_MENU_OPTION))
			{
				prevTagMenuOption.setOption(ADD_PREVIOUS_TAG_MENU_OPTION + " (" + getPrevTag() + ")");
				entries[entries.length - ADD_PREVIOUS_TAG_MENU_INDEX] = prevTagMenuOption;
			}
		}

		MenuEntry[] newEntries = new MenuEntry[entryList.size()];
		client.setMenuEntries(entryList.toArray(newEntries));
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event)
	{
		if (event.getWidgetId() == WidgetInfo.BANK_ITEM_CONTAINER.getId()
			&& event.getMenuAction() == MenuAction.EXAMINE_ITEM_BANK_EQ
			&& event.getId() == EDIT_TAGS_MENU_INDEX
			&& event.getMenuOption().startsWith(EDIT_TAGS_MENU_OPTION))
		{
			event.consume();
			int inventoryIndex = event.getActionParam();

			Item item = getItemForInventoryIndex(inventoryIndex);
			ItemComposition itemComposition = itemManager.getItemComposition(item.getId());

			int itemId = getItemIdForItem(item);

			String itemName = itemComposition.getName();
			String initialValue = getTags(itemId);

			chatboxInputManager.openInputWindow(itemName + " tags:", initialValue, (newTags) ->
			{
				if (newTags == null)
				{
					return;
				}

				String changedTag = getChangedTag(initialValue, newTags);
				if (!changedTag.isEmpty())
				{
					setPrevTag(changedTag);
				}

				setTags(itemId, newTags);

				updateTagCount(itemId, inventoryIndex);
			});
		}
		// Check if menu option clicked is add-tags
		if (event.getWidgetId() == WidgetInfo.BANK_ITEM_CONTAINER.getId()
				&& event.getMenuAction() == MenuAction.EXAMINE_ITEM_BANK_EQ
				&& event.getId() == ADD_PREVIOUS_TAG_MENU_INDEX
				&& event.getMenuOption().startsWith(ADD_PREVIOUS_TAG_MENU_OPTION))
		{
			event.consume();
			int inventoryIndex = event.getActionParam();
			Item item = getItemForInventoryIndex(inventoryIndex);
			if (item == null)
			{
				return;
			}

			// Get Item Id for Bank Inventory Location
			int itemId = getItemIdForItem(item);

			// Add new Tag to old Tags
			String oldTags = getTags(itemId);
			String newTag = getPrevTag();
			if (getTagCount(itemId) == 0)
			{
				setTags(itemId, newTag);
			}
			else if (!Arrays.asList(oldTags.replaceAll("\\s", "").split(",")).contains(newTag))
			{
				setTags(itemId, oldTags + "," + newTag);
			}
			updateTagCount(itemId, inventoryIndex);
		}
	}

	/**
	 * Update Tag Count On Bank Interface
	 *
	 * @param itemId   ID of item to get tag count for
	 * @param inventoryIndex index of inventory stack to update on bank interface
	 */
	private void updateTagCount(int itemId, int inventoryIndex)
	{
		Widget bankContainerWidget = client.getWidget(WidgetInfo.BANK_ITEM_CONTAINER);
		if (bankContainerWidget == null)
		{
			return;
		}
		Widget[] bankItemWidgets = bankContainerWidget.getDynamicChildren();
		if (bankItemWidgets == null || inventoryIndex >= bankItemWidgets.length)
		{
			return;
		}
		Widget bankItemWidget = bankItemWidgets[inventoryIndex];
		String[] actions = bankItemWidget.getActions();
		if (actions == null || EDIT_TAGS_MENU_INDEX - 1 >= actions.length
				|| itemId != bankItemWidget.getItemId())
		{
			return;
		}
		int tagCount = getTagCount(itemId);
		actions[EDIT_TAGS_MENU_INDEX - 1] = EDIT_TAGS_MENU_OPTION;
		if (tagCount > 0)
		{
			actions[EDIT_TAGS_MENU_INDEX - 1] += " (" + tagCount + ")";
		}
	}

	/**
	 * Determine new changed tag to save for add-tag
	 *
	 * @param prevValue   Old Value to compare new value to
	 * @param newValue    New Input to get tags from
	 */
	private String getChangedTag(String prevValue, String newValue)
	{
		String[] newValues = newValue.replaceAll("\\s", "").split(",");
		if (prevValue.isEmpty() || newValues.length == 1)
		{
			return newValue;
		}
		else if (newValue.startsWith(prevValue))
		{
			String[] newTags = newValue.replace("\\s", "").split(",");
			return newTags[newTags.length - 1];
		}
		return "";
	}

	/**
	 * Get Item for bank inventory index
	 *
	 * @param inventoryIndex   Bank Inventory Index
	 */
	private Item getItemForInventoryIndex(int inventoryIndex)
	{
		ItemContainer bankContainer = client.getItemContainer(InventoryID.BANK);
		if (bankContainer == null)
		{
			return null;
		}
		Item[] items = bankContainer.getItems();
		if (inventoryIndex < 0 || inventoryIndex >= items.length)
		{
			return null;
		}
		return bankContainer.getItems()[inventoryIndex];
	}

	/**
	 * Get ItemId for Bank Item stack
	 *
	 * @param item   Item Stack to retrieve Id For
	 */
	private int getItemIdForItem(Item item)
	{
		ItemComposition itemComposition = itemManager.getItemComposition(item.getId());
		if (itemComposition.getPlaceholderTemplateId() != -1)
		{
			// if the item is a placeholder then get the item id for the normal item
			return itemComposition.getPlaceholderId();
		}
		else
		{
			return item.getId();
		}
	}

}
