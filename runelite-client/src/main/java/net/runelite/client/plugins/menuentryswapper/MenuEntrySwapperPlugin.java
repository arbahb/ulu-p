/*
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
 * Copyright (c) 2018, Kamiel
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
package net.runelite.client.plugins.menuentryswapper;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Provides;

import java.util.*;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.*;
import net.runelite.api.events.ConfigChanged;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.events.MenuOpened;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.PostItemComposition;
import net.runelite.api.events.WidgetMenuOptionClicked;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.input.KeyManager;
import net.runelite.client.menus.MenuManager;
import net.runelite.client.menus.WidgetMenuOption;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.Text;
import org.apache.commons.lang3.ArrayUtils;

@PluginDescriptor(
	name = "Menu Entry Swapper",
	enabledByDefault = false
)
public class MenuEntrySwapperPlugin extends Plugin
{
	private static final String CONFIGURE = "Configure";
	private static final String SAVE = "Save";
	private static final String RESET = "Reset";
	private static final String MENU_TARGET = "<col=ff9040>Shift-click";

	private static final String CONFIG_GROUP = "shiftclick";
	private static final String ITEM_KEY_PREFIX = "item_";

	private static final WidgetMenuOption FIXED_INVENTORY_TAB_CONFIGURE = new WidgetMenuOption(CONFIGURE,
		MENU_TARGET, WidgetInfo.FIXED_VIEWPORT_INVENTORY_TAB);

	private static final WidgetMenuOption FIXED_INVENTORY_TAB_SAVE = new WidgetMenuOption(SAVE,
		MENU_TARGET, WidgetInfo.FIXED_VIEWPORT_INVENTORY_TAB);

	private static final WidgetMenuOption RESIZABLE_INVENTORY_TAB_CONFIGURE = new WidgetMenuOption(CONFIGURE,
		MENU_TARGET, WidgetInfo.RESIZABLE_VIEWPORT_INVENTORY_TAB);

	private static final WidgetMenuOption RESIZABLE_INVENTORY_TAB_SAVE = new WidgetMenuOption(SAVE,
		MENU_TARGET, WidgetInfo.RESIZABLE_VIEWPORT_INVENTORY_TAB);

	private static final WidgetMenuOption RESIZABLE_BOTTOM_LINE_INVENTORY_TAB_CONFIGURE = new WidgetMenuOption(CONFIGURE,
		MENU_TARGET, WidgetInfo.RESIZABLE_VIEWPORT_BOTTOM_LINE_INVENTORY_TAB);

	private static final WidgetMenuOption RESIZABLE_BOTTOM_LINE_INVENTORY_TAB_SAVE = new WidgetMenuOption(SAVE,
		MENU_TARGET, WidgetInfo.RESIZABLE_VIEWPORT_BOTTOM_LINE_INVENTORY_TAB);

	@Inject
	private Client client;

	@Inject
	private MenuEntrySwapperConfig config;

	@Inject
	private ShiftClickInputListener inputListener;

	@Inject
	private ConfigManager configManager;

	@Inject
	private ItemManager itemManager;

	@Inject
	private KeyManager keyManager;

	@Inject
	private MenuManager menuManager;

	@Getter
	private boolean configuringShiftClick = false;

	@Setter
	private boolean shiftModifier = false;

	@Provides
	MenuEntrySwapperConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(MenuEntrySwapperConfig.class);
	}

	@Override
	public void startUp()
	{
		if (config.shiftClickCustomization())
		{
			enableCustomization();
		}
	}

	@Override
	public void shutDown()
	{
		disableCustomization();
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (event.getKey().equals("shiftClickCustomization"))
		{
			if (config.shiftClickCustomization())
			{
				enableCustomization();
			}
			else
			{
				disableCustomization();
			}
		}
	}

	private Integer getSwapConfig(int itemId)
	{
		String config = configManager.getConfiguration(CONFIG_GROUP, ITEM_KEY_PREFIX + itemId);
		if (config == null || config.isEmpty())
		{
			return null;
		}

		return Integer.parseInt(config);
	}

	private void setSwapConfig(int itemId, int index)
	{
		configManager.setConfiguration(CONFIG_GROUP, ITEM_KEY_PREFIX + itemId, index);
	}

	private void unsetSwapConfig(int itemId)
	{
		configManager.unsetConfiguration(CONFIG_GROUP, ITEM_KEY_PREFIX + itemId);
	}

	private void enableCustomization()
	{
		keyManager.registerKeyListener(inputListener);
		refreshShiftClickCustomizationMenus();
	}

	private void disableCustomization()
	{
		keyManager.unregisterKeyListener(inputListener);
		removeShiftClickCustomizationMenus();
		configuringShiftClick = false;
	}

	@Subscribe
	public void onWidgetMenuOptionClicked(WidgetMenuOptionClicked event)
	{
		if (event.getWidget() == WidgetInfo.FIXED_VIEWPORT_INVENTORY_TAB
			|| event.getWidget() == WidgetInfo.RESIZABLE_VIEWPORT_INVENTORY_TAB
			|| event.getWidget() == WidgetInfo.RESIZABLE_VIEWPORT_BOTTOM_LINE_INVENTORY_TAB)
		{
			configuringShiftClick = event.getMenuOption().equals(CONFIGURE);
			refreshShiftClickCustomizationMenus();
		}
	}

	@Subscribe
	public void onMenuOpened(MenuOpened event)
	{
		if (!configuringShiftClick)
		{
			return;
		}

		MenuEntry firstEntry = event.getFirstEntry();
		if (firstEntry == null)
		{
			return;
		}

		int widgetId = firstEntry.getParam1();
		if (widgetId != WidgetInfo.INVENTORY.getId())
		{
			return;
		}

		int itemId = firstEntry.getIdentifier();
		if (itemId == -1)
		{
			return;
		}

		ItemComposition itemComposition = client.getItemDefinition(itemId);
		String itemName = itemComposition.getName();
		String option = "Use";
		int shiftClickActionindex = itemComposition.getShiftClickActionIndex();
		String[] inventoryActions = itemComposition.getInventoryActions();

		if (shiftClickActionindex >= 0 && shiftClickActionindex < inventoryActions.length)
		{
			option = inventoryActions[shiftClickActionindex];
		}

		MenuEntry[] entries = event.getMenuEntries();

		for (MenuEntry entry : entries)
		{
			if (itemName.equals(Text.removeTags(entry.getTarget())))
			{
				entry.setType(MenuAction.RUNELITE.getId());

				if (option.equals(entry.getOption()))
				{
					entry.setOption("* " + option);
				}
			}
		}

		final MenuEntry resetShiftClickEntry = new MenuEntry();
		resetShiftClickEntry.setOption(RESET);
		resetShiftClickEntry.setTarget(MENU_TARGET);
		resetShiftClickEntry.setIdentifier(itemId);
		resetShiftClickEntry.setParam1(widgetId);
		resetShiftClickEntry.setType(MenuAction.RUNELITE.getId());
		client.setMenuEntries(ArrayUtils.addAll(entries, resetShiftClickEntry));
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event)
	{
		if (event.getMenuAction() != MenuAction.RUNELITE || event.getWidgetId() != WidgetInfo.INVENTORY.getId())
		{
			return;
		}

		int itemId = event.getId();

		if (itemId == -1)
		{
			return;
		}

		String option = event.getMenuOption();
		String target = event.getMenuTarget();
		ItemComposition itemComposition = client.getItemDefinition(itemId);

		if (option.equals(RESET) && target.equals(MENU_TARGET))
		{
			unsetSwapConfig(itemId);
			itemComposition.resetShiftClickActionIndex();
			return;
		}

		if (!itemComposition.getName().equals(Text.removeTags(target)))
		{
			return;
		}

		int index = -1;
		boolean valid = false;

		if (option.equals("Use")) //because "Use" is not in inventoryActions
		{
			valid = true;
		}
		else
		{
			String[] inventoryActions = itemComposition.getInventoryActions();

			for (index = 0; index < inventoryActions.length; index++)
			{
				if (option.equals(inventoryActions[index]))
				{
					valid = true;
					break;
				}
			}
		}

		if (valid)
		{
			setSwapConfig(itemId, index);
			itemComposition.setShiftClickActionIndex(index);
		}
	}

	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded event)
	{
		if (client.getGameState() != GameState.LOGGED_IN)
		{
			return;
		}

		int itemId = event.getIdentifier();
		String option = Text.removeTags(event.getOption()).toLowerCase();
		String target = Text.removeTags(event.getTarget()).toLowerCase();

		if (option.equals("talk-to"))
		{
			if (config.swapPickpocket() && target.contains("h.a.m."))
			{
				swap("pickpocket", option, target, true);
			}

			if (config.swapAbyssTeleport() && target.contains("mage of zamorak"))
			{
				swap("teleport", option, target, true);
			}

			if (config.swapBank())
			{
				swap("bank", option, target, true);
			}

			if (config.swapExchange())
			{
				swap("exchange", option, target, true);
			}

			if (config.swapTrade())
			{
				swap("trade", option, target, true);
			}

			if (config.claimSlime() && target.equals("robin"))
			{
				swap("claim-slime", option, target, true);
			}

			if (config.swapTravel())
			{
				swap("travel", option, target, true);
				swap("pay-fare", option, target, true);
				swap("charter", option, target, true);
				swap("take-boat", option, target, true);
				swap("fly", option, target, true);
				swap("jatizso", option, target, true);
				swap("neitiznot", option, target, true);
				swap("rellekka", option, target, true);
				swap("follow", option, target, true);
				swap("transport", option, target, true);
			}

			if (config.swapPay())
			{
				swap("pay", option, target, true);
			}
		}
		else if (config.swapTravel() && option.equals("pass") && target.equals("energy barrier"))
		{
			swap("pay-toll(2-ecto)", option, target, true);
		}
		else if (config.swapTravel() && option.equals("open") && target.equals("gate"))
		{
			swap("pay-toll(10gp)", option, target, true);
		}
		else if (config.swapHarpoon() && option.equals("cage"))
		{
			swap("harpoon", option, target, true);
		}
		else if (config.swapHarpoon() && (option.equals("big net") || option.equals("net")))
		{
			swap("harpoon", option, target, true);
		}
		else if (config.swapHome() && option.equals("enter"))
		{
			swap("home", option, target, true);
		}
		else if (config.swapLastDestination() && (option.equals("zanaris") || option.equals("tree")))
		{
			swap("last-destination (", option, target, false);
		}
		else if(config.removeBA() && client.getSetting(Varbits.IN_GAME_BA) == 1 && !option.contains("tell-"))//if in barbarian assault and menu isnt from a horn
		{
			if(itemId == ItemID.LOGS && !target.contains("healing vial"))
			{
				if(client.getWidget(WidgetInfo.BA_DEF_ROLE_TEXT) == null)
					remove(new String[]{"take", "light"}, target, true);
				else//remove "Light" option (and "Take" option if not defender).
					remove("light", target, true);
			}
			else if(option.equals("use"))
			{
				Widget healer = client.getWidget(WidgetInfo.BA_HEAL_LISTEN_TEXT);
				if (healer != null) {
					String item = target.split("-")[0].trim();
					List<String> poison = Arrays.asList("poisoned tofu", "poisoned meat", "poisoned worms");
					List<String> vials = Arrays.asList("healing vial", "healing vial(1)", "healing vial(2)", "healing vial(3)","healing vial(4)");//"healing vial(4)"
					if (poison.contains(item)){//if item is a poison item
						int calledPoison = 0;
						switch (healer.getText())//choose which poison to hide the use/destroy option for
						{
							case "Pois. Tofu":
								calledPoison = ItemID.POISONED_TOFU;
								break;
							case "Pois. Meat":
								calledPoison = ItemID.POISONED_MEAT;
								break;
							case "Pois. Worms":
								calledPoison = ItemID.POISONED_WORMS;
								break;
						}
						System.out.println(target.equals(item));
						if(target.equals(item))//if targeting the item itself
						{
							if(calledPoison != 0 && itemId != calledPoison)//if no call or chosen item is not the called one
							{
								remove(new String[]{"use", "destroy", "examine"}, target, true);//remove options
							}
						}
						else if(!target.contains("penance healer"))
						{
							remove(option, target, true);
						}
					}
					else if(vials.contains(item))//if item is the healer's healing vial
					{

						if(!target.equals(item))//if target is not the vial itself
						{

							if(!target.contains("level") || target.contains("penance") || target.contains("queen spawn"))//if someone has "penance" or "queen spawn" in their name, gg...
							{
								remove(option, target, true);
							}
						}
					}
				}
			}
			else if(option.equals("attack") && client.getWidget(WidgetInfo.BA_ATK_ROLE_TEXT) == null && !target.equals("queen spawn"))//if not attacker
			{//remove attack option from everything but queen spawns
				remove(option, target, true);
			}
			else if((option.equals("fix") || (option.equals("block") && target.equals("penance cave"))) && client.getWidget(WidgetInfo.BA_DEF_ROLE_TEXT) == null)//if not defender
			{//the check for option requires checking target as well because defensive attack style option is also called "block".
				remove(option, target, true);
			}
			else if((option.equals("load")) && client.getWidget(WidgetInfo.BA_COLL_ROLE_TEXT) == null)//if not collector, remove hopper options
			{
				remove(new String[]{option, "look-in"}, target, true);
			}
			else if(option.equals("take"))
			{
				Widget eggToColl = client.getWidget(WidgetInfo.BA_COLL_LISTEN_TEXT);
				if(eggToColl != null)//if we're a collector
				{
					List<Integer> eggsToHide = new ArrayList<>();
					eggsToHide.add(ItemID.HAMMER);
					switch(eggToColl.getText())//choose which eggs to hide take option for
					{
						case "Red eggs":
							eggsToHide.add(ItemID.BLUE_EGG);
							eggsToHide.add(ItemID.GREEN_EGG);
							break;
						case "Blue eggs":
							eggsToHide.add(ItemID.RED_EGG);
							eggsToHide.add(ItemID.GREEN_EGG);
							break;
						case "Green eggs":
							eggsToHide.add(ItemID.RED_EGG);
							eggsToHide.add(ItemID.BLUE_EGG);
							break;
					}
					if(eggsToHide.contains(itemId))
					{
						remove(option, target, true);//hide wrong eggs
					}
				}
				else
				{
					List<Integer> defenderItems = Arrays.asList(ItemID.HAMMER, ItemID.TOFU, ItemID.CRACKERS, ItemID.WORMS);//logs are handled separately due to hiding "light" option too.
					if(client.getWidget(WidgetInfo.BA_DEF_ROLE_TEXT) == null || !defenderItems.contains(itemId))//if not defender, or item is not a defenderItem
					{
						remove(option, target, true);//hide everything except hammer/logs and bait if Defender
					}
				}
			}
		}
		else if (config.swapBoxTrap() && (option.equals("check") || option.equals("dismantle")))
		{
			swap("reset", option, target, true);
		}
		else if (config.swapBoxTrap() && option.equals("take"))
		{
			swap("lay", option, target, true);
		}
		else if (config.swapCatacombEntrance() && option.equals("read"))
		{
			swap("investigate", option, target, true);
		}
		else if (config.swapChase() && option.equals("pick-up"))
		{
			swap("chase", option, target, true);
		}
		else if (config.shiftClickCustomization() && shiftModifier && !option.equals("use"))
		{
			Integer customOption = getSwapConfig(itemId);

			if (customOption != null && customOption == -1)
			{
				swap("use", option, target, true);
			}
		}
		// Put all item-related swapping after shift-click
		else if (config.swapTeleportItem() && option.equals("wear"))
		{
			swap("rub", option, target, true);
			swap("teleport", option, target, true);
		}
		else if (option.equals("wield"))
		{
			if (config.swapTeleportItem())
			{
				swap("teleport", option, target, true);
			}
		}
		else if (config.swapBones() && option.equals("bury"))
		{
			swap("use", option, target, true);
		}
		else if(config.swapBAHorn() && option.equals("tell-red"))
		{
			Widget roleToCall = client.getWidget(WidgetInfo.BA_ATK_CALL_TEXT);
			switch(roleToCall != null ? roleToCall.getText() : "")
			{
				case "Blue egg":
					swap("tell-blue", option, target, true);
					break;
				case "Green egg":
					swap("tell-green", option, target, true);
					break;
			}
		}
		else if(config.swapBAHorn() && option.equals("tell-controlled"))
		{
			Widget roleToCall = client.getWidget(WidgetInfo.BA_COLL_CALL_TEXT);
			switch(roleToCall != null ? roleToCall.getText() : "")
			{
				case "Accurate/Field/Water":
					swap("tell-accurate", option, target, true);
					break;
				case "Aggressive/Blunt/Earth":
					swap("tell-aggressive", option, target, true);
					break;
				case "Defensive/Barbed/Fire":
					swap("tell-defensive", option, target, true);
					break;
			}
		}
		else if(config.swapBAHorn() && option.equals("tell-tofu"))
		{
			//healer and defender horns share first option so we handle them in the same if statement
			Widget roleToCall = client.getWidget(WidgetInfo.BA_HEAL_CALL_TEXT) != null ? client.getWidget(WidgetInfo.BA_HEAL_CALL_TEXT) : client.getWidget(WidgetInfo.BA_DEF_CALL_TEXT);
			switch(roleToCall != null ? roleToCall.getText() : "")
			{
				case "Pois. Meat":
					swap("tell-meat", option, target, true);
					break;
				case "Crackers":
					swap("tell-crackers", option, target, true);
					break;
				case "Worms":
				case "Pois. Worms":
					swap("tell-worms", option, target, true);
					break;
			}
		}
	}

	@Subscribe
	public void onPostItemComposition(PostItemComposition event)
	{
		ItemComposition itemComposition = event.getItemComposition();
		Integer option = getSwapConfig(itemComposition.getId());

		if (option != null)
		{
			itemComposition.setShiftClickActionIndex(option);

			// Update our cached item composition too
			ItemComposition ourItemComposition = itemManager.getItemComposition(itemComposition.getId());
			ourItemComposition.setShiftClickActionIndex(option);
		}
	}

	private int searchIndex(MenuEntry[] entries, String option, String target, boolean strict)
	{
		for (int i = entries.length - 1; i >= 0; i--)
		{
			MenuEntry entry = entries[i];
			String entryOption = Text.removeTags(entry.getOption()).toLowerCase();
			String entryTarget = Text.removeTags(entry.getTarget()).toLowerCase();

			if (strict)
			{
				if (entryOption.equals(option) && entryTarget.equals(target))
				{
					return i;
				}
			}
			else
			{
				if (entryOption.contains(option.toLowerCase()) && entryTarget.equals(target))
				{
					return i;
				}
			}
		}

		return -1;
	}

	private void remove(String option, String target, boolean strict)
	{
		MenuEntry[] entries = client.getMenuEntries();
		int idx = searchIndex(entries, option, target, strict);
        if(idx >= 0 && entries[idx] != null)
        {
            entries = ArrayUtils.removeElement(entries, entries[idx]);
            client.setMenuEntries(entries);
        }
	}

	private void remove(String[] options, String target, boolean strict)
	{
		MenuEntry[] entries = client.getMenuEntries();
		for(int i = 0; i < options.length; i++)
		{
			int idx = searchIndex(entries, options[i], target, strict);
			if(idx >= 0 && entries[idx] != null)
			    entries = ArrayUtils.removeElement(entries, entries[idx]);
		}

		client.setMenuEntries(entries);
	}

	private void swap(String optionA, String optionB, String target, boolean strict)
	{
		MenuEntry[] entries = client.getMenuEntries();

		int idxA = searchIndex(entries, optionA, target, strict);
		int idxB = searchIndex(entries, optionB, target, strict);

		if (idxA >= 0 && idxB >= 0)
		{
			MenuEntry entry = entries[idxA];
			entries[idxA] = entries[idxB];
			entries[idxB] = entry;

			client.setMenuEntries(entries);
		}
	}

	private void removeShiftClickCustomizationMenus()
	{
		menuManager.removeManagedCustomMenu(FIXED_INVENTORY_TAB_CONFIGURE);
		menuManager.removeManagedCustomMenu(FIXED_INVENTORY_TAB_SAVE);
		menuManager.removeManagedCustomMenu(RESIZABLE_BOTTOM_LINE_INVENTORY_TAB_CONFIGURE);
		menuManager.removeManagedCustomMenu(RESIZABLE_BOTTOM_LINE_INVENTORY_TAB_SAVE);
		menuManager.removeManagedCustomMenu(RESIZABLE_INVENTORY_TAB_CONFIGURE);
		menuManager.removeManagedCustomMenu(RESIZABLE_INVENTORY_TAB_SAVE);
	}

	private void refreshShiftClickCustomizationMenus()
	{
		removeShiftClickCustomizationMenus();
		if (configuringShiftClick)
		{
			menuManager.addManagedCustomMenu(FIXED_INVENTORY_TAB_SAVE);
			menuManager.addManagedCustomMenu(RESIZABLE_BOTTOM_LINE_INVENTORY_TAB_SAVE);
			menuManager.addManagedCustomMenu(RESIZABLE_INVENTORY_TAB_SAVE);
		}
		else
		{
			menuManager.addManagedCustomMenu(FIXED_INVENTORY_TAB_CONFIGURE);
			menuManager.addManagedCustomMenu(RESIZABLE_BOTTOM_LINE_INVENTORY_TAB_CONFIGURE);
			menuManager.addManagedCustomMenu(RESIZABLE_INVENTORY_TAB_CONFIGURE);
		}
	}

	Collection<WidgetItem> getInventoryItems()
	{
		return Collections.unmodifiableCollection(client.getWidget(WidgetInfo.INVENTORY).getWidgetItems());
	}
}
