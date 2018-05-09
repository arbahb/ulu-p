/*
 * Copyright (c) 2018 kulers
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
package net.runelite.client.plugins.inventorytagger;

import com.google.common.eventbus.Subscribe;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.MenuOpened;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.util.Text;
import org.apache.commons.lang3.ArrayUtils;

@PluginDescriptor(
	name = "Inventory Item Tagging",
	enabledByDefault = true
)
public class InventoryTaggerPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private InventoryTaggerPlugin plugin;

	@Inject
	private InventoryTaggerOverlay overlay;

	public Map<String, TaggedItems> taggedItems = new HashMap<>();

	@Override
	public Overlay getOverlay()
	{
		return overlay;
	}

	private boolean editorMode = false;

	private static final String SETNAME_ALPHA = "Red";
	private static final String SETNAME_BETA = "Lime";
	private static final String SETNAME_GAMMA = "Blue";
	private static final String SETNAME_DELTA = "Yellow";
	private static final String SETNAME_EPSILON = "Cyan";
	private static final String SETNAME_ZETA = "Magenta";

	private static final String CONFIGURE = "Configure";
	private static final String SAVE = "Save";
	private static final String MENU_TARGET = "<col=ff9040>Inventory Tagger";
	private static final String MENU_TARGET_CLEAR = "<col=ff0000>Inventory Tagger";

	private static final String MENU_SET = "Mark";
	private static final String MENU_REMOVE = "Remove";
	private static final String MENU_CLEAR_TAGS = "Clear Tags";

	@Override
	protected void startUp() throws Exception
	{
		taggedItems = new HashMap<>();
		taggedItems.put(SETNAME_ALPHA, new TaggedItems(SETNAME_ALPHA, new Color(255, 0, 0, 255)));
		taggedItems.put(SETNAME_BETA, new TaggedItems(SETNAME_BETA, new Color(0, 255, 0, 255)));
		taggedItems.put(SETNAME_GAMMA, new TaggedItems(SETNAME_GAMMA, new Color(0, 0, 255, 255)));
		taggedItems.put(SETNAME_DELTA, new TaggedItems(SETNAME_DELTA, new Color(255, 255, 0, 255)));
		taggedItems.put(SETNAME_EPSILON, new TaggedItems(SETNAME_EPSILON, new Color(0, 255, 255, 255)));
		taggedItems.put(SETNAME_ZETA, new TaggedItems(SETNAME_ZETA, new Color(255, 0, 255, 255)));
	}

	@Subscribe
	public void onClickMenu(MenuOptionClicked event)
	{
		if (event.getMenuAction() != MenuAction.RUNELITE)
		{
			return;
		}

		if (event.getMenuOption().equals(CONFIGURE) && event.getMenuTarget().equals(MENU_TARGET))
		{
			editorMode = true;
		}

		if (event.getMenuOption().equals(SAVE) && event.getMenuTarget().equals(MENU_TARGET))
		{
			editorMode = false;
		}

		if (event.getMenuOption().equals(MENU_CLEAR_TAGS) && event.getMenuTarget().equals(MENU_TARGET_CLEAR))
		{
			clearAllTags();
		}

		String selectedMenu = Text.removeTags(event.getMenuTarget());
		if (event.getMenuOption().equals(MENU_SET) && taggedItems.containsKey(selectedMenu))
		{
			addItemToGroup(selectedMenu, event.getId());
		}

		if (event.getMenuOption().equals(MENU_REMOVE) && taggedItems.containsKey(selectedMenu))
		{
			removeItemFromGroup(selectedMenu, event.getId());
		}

	}

	@Subscribe
	public void onMenuOpened(MenuOpened event)
	{
		MenuEntry firstEntry = event.getFirstEntry();
		if (firstEntry == null)
		{
			return;
		}

		int widgetId = firstEntry.getParam1();
		if (widgetId == WidgetInfo.FIXED_VIEWPORT_INVENTORY_TAB.getId() ||
			widgetId == WidgetInfo.RESIZABLE_VIEWPORT_INVENTORY_TAB.getId() ||
			widgetId == WidgetInfo.RESIZABLE_VIEWPORT_BOTTOM_LINE_INVENTORY_TAB.getId())
		{
			//Configure Options
			int itemId = firstEntry.getIdentifier();
			if (itemId == -1)
			{
				return;
			}

			MenuEntry[] entries = event.getMenuEntries();

			if (editorMode)
			{
				final MenuEntry configureClearTags = new MenuEntry();
				configureClearTags.setOption(MENU_CLEAR_TAGS);
				configureClearTags.setTarget(MENU_TARGET_CLEAR);
				configureClearTags.setIdentifier(itemId);
				configureClearTags.setParam1(widgetId);
				configureClearTags.setType(MenuAction.RUNELITE.getId());

				entries = ArrayUtils.addAll(entries, configureClearTags);
			}

			final MenuEntry configureOption = new MenuEntry();
			configureOption.setOption(editorMode ? SAVE : CONFIGURE);
			configureOption.setTarget(MENU_TARGET);
			configureOption.setIdentifier(itemId);
			configureOption.setParam1(widgetId);
			configureOption.setType(MenuAction.RUNELITE.getId());

			entries = ArrayUtils.addAll(entries, configureOption);

			client.setMenuEntries(entries);
		}

		if (widgetId == WidgetInfo.INVENTORY.getId() && editorMode)
		{
			//Configure Options
			int itemId = firstEntry.getIdentifier();
			if (itemId == -1)
			{
				return;
			}

			MenuEntry[] menuList = new MenuEntry[taggedItems.size()];
			int num = 0;
			for (Map.Entry<String, TaggedItems> i : taggedItems.entrySet())
			{
				MenuEntry newMenu = new MenuEntry();
				newMenu.setOption(i.getValue().containsItem(itemId) ? MENU_REMOVE : MENU_SET);
				newMenu.setTarget("<col=" + i.getValue().getHexColor() + ">" + i.getKey());
				newMenu.setIdentifier(itemId);
				newMenu.setParam1(widgetId);
				newMenu.setType(MenuAction.RUNELITE.getId());
				menuList[num] = newMenu;
				num++;
			}
			client.setMenuEntries(menuList);
		}


	}

	public void addItemToGroup(String groupName, Integer itemId)
	{
		//Remove form others if exist
		for (String f : taggedItems.keySet())
		{
			removeItemFromGroup(f, itemId);
		}

		TaggedItems t = taggedItems.get(groupName);
		t.addItem(itemId);
		taggedItems.put(groupName, t);
	}

	public void removeItemFromGroup(String groupName, Integer itemId)
	{
		TaggedItems t = taggedItems.get(groupName);
		t.removeItem(itemId);
		taggedItems.put(groupName, t);
	}

	public void clearAllTags()
	{
		for (String f : taggedItems.keySet())
		{
			taggedItems.get(f).clearItem();
			ItemOutline.storedOutlines = new HashMap<>();
		}
	}

}
