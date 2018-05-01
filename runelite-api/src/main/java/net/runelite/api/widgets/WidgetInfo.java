/*
 * Copyright (c) 2017, Adam <Adam@sigterm.info>
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
package net.runelite.api.widgets;

public enum WidgetInfo
{
	FAIRY_QUEEN_HIDEOUT_CODE(WidgetID.FAIRY_RING_CODE_GROUP_ID, WidgetID.FairyRingCode.FAIRY_QUEEN_HIDEOUT),

	FAIRY_RING_LEFT_ORB_CLOCKWISE(WidgetID.FAIRY_RING_GROUP_ID, WidgetID.FairyRing.LEFT_ORB_CLOCKWISE),
	FAIRY_RING_LEFT_ORB_COUNTER_CLOCKWISE(WidgetID.FAIRY_RING_GROUP_ID, WidgetID.FairyRing.LEFT_ORB_COUNTER_CLOCKWISE),
	FAIRY_RING_MIDDLE_ORB_CLOCKWISE(WidgetID.FAIRY_RING_GROUP_ID, WidgetID.FairyRing.MIDDLE_ORB_CLOCKWISE),
	FAIRY_RING_MIDDLE_ORB_COUNTER_CLOCKWISE(WidgetID.FAIRY_RING_GROUP_ID, WidgetID.FairyRing.MIDDLE_ORB_COUNTER_CLOCKWISE),
	FAIRY_RING_RIGHT_ORB_CLOCKWISE(WidgetID.FAIRY_RING_GROUP_ID, WidgetID.FairyRing.RIGHT_ORB_CLOCKWISE),
	FAIRY_RING_RIGHT_ORB_COUNTER_CLOCKWISE(WidgetID.FAIRY_RING_GROUP_ID, WidgetID.FairyRing.RIGHT_ORB_COUNTER_CLOCKWISE),

	FAIRY_RING_TELEPORT_BUTTON(WidgetID.FAIRY_RING_GROUP_ID, WidgetID.FairyRing.TELEPORT_BUTTON),

	LOGOUT_BUTTON(WidgetID.LOGOUT_PANEL_ID, WidgetID.LogoutPanel.LOGOUT_BUTTON),

	INVENTORY(WidgetID.INVENTORY_GROUP_ID, 0),
	FRIENDS_LIST(WidgetID.FRIENDS_LIST_GROUP_ID, 0),
	CLAN_CHAT(WidgetID.CLAN_CHAT_GROUP_ID, 0),
	RAIDING_PARTY(WidgetID.RAIDING_PARTY_GROUP_ID, 0),

	WORLD_MAP(WidgetID.WORLD_MAP_MENU_GROUP_ID, WidgetID.WorldMap.OPTION),

	CLUE_SCROLL_TEXT(WidgetID.CLUE_SCROLL_GROUP_ID, WidgetID.Cluescroll.CLUE_TEXT),

	QUICK_PRAYER_ORB(WidgetID.MINIMAP_GROUP_ID, WidgetID.Minimap.QUICK_PRAYER_ORB),

	EQUIPMENT(WidgetID.EQUIPMENT_GROUP_ID, 0),
	EQUIPMENT_INVENTORY_ITEMS_CONTAINER(WidgetID.EQUIPMENT_INVENTORY_GROUP_ID, WidgetID.Equipment.INVENTORY_ITEM_CONTAINER),

	EQUIPMENT_HELMET(WidgetID.EQUIPMENT_GROUP_ID, WidgetID.Equipment.HELMET),
	EQUIPMENT_CAPE(WidgetID.EQUIPMENT_GROUP_ID, WidgetID.Equipment.CAPE),
	EQUIPMENT_AMULET(WidgetID.EQUIPMENT_GROUP_ID, WidgetID.Equipment.AMULET),
	EQUIPMENT_WEAPON(WidgetID.EQUIPMENT_GROUP_ID, WidgetID.Equipment.WEAPON),
	EQUIPMENT_BODY(WidgetID.EQUIPMENT_GROUP_ID, WidgetID.Equipment.BODY),
	EQUIPMENT_SHIELD(WidgetID.EQUIPMENT_GROUP_ID, WidgetID.Equipment.SHIELD),
	EQUIPMENT_LEGS(WidgetID.EQUIPMENT_GROUP_ID, WidgetID.Equipment.LEGS),
	EQUIPMENT_GLOVES(WidgetID.EQUIPMENT_GROUP_ID, WidgetID.Equipment.GLOVES),
	EQUIPMENT_BOOTS(WidgetID.EQUIPMENT_GROUP_ID, WidgetID.Equipment.BOOTS),
	EQUIPMENT_RING(WidgetID.EQUIPMENT_GROUP_ID, WidgetID.Equipment.RING),
	EQUIPMENT_AMMO(WidgetID.EQUIPMENT_GROUP_ID, WidgetID.Equipment.AMMO),

	EMOTE_WINDOW(WidgetID.EMOTES_GROUP_ID, WidgetID.Emotes.EMOTE_WINDOW),
	EMOTE_CONTAINER(WidgetID.EMOTES_GROUP_ID, WidgetID.Emotes.EMOTE_CONTAINER),

	DIARY_LIST(WidgetID.DIARY_GROUP_ID, 4),

	PEST_CONTROL_PURPLE_SHIELD(WidgetID.PEST_CONTROL_GROUP_ID, WidgetID.PestControl.PURPLE_SHIELD),
	PEST_CONTROL_BLUE_SHIELD(WidgetID.PEST_CONTROL_GROUP_ID, WidgetID.PestControl.BLUE_SHIELD),
	PEST_CONTROL_YELLOW_SHIELD(WidgetID.PEST_CONTROL_GROUP_ID, WidgetID.PestControl.YELLOW_SHIELD),
	PEST_CONTROL_RED_SHIELD(WidgetID.PEST_CONTROL_GROUP_ID, WidgetID.PestControl.RED_SHIELD),
	PEST_CONTROL_PURPLE_HEALTH(WidgetID.PEST_CONTROL_GROUP_ID, WidgetID.PestControl.PURPLE_HEALTH),
	PEST_CONTROL_BLUE_HEALTH(WidgetID.PEST_CONTROL_GROUP_ID, WidgetID.PestControl.BLUE_HEALTH),
	PEST_CONTROL_YELLOW_HEALTH(WidgetID.PEST_CONTROL_GROUP_ID, WidgetID.PestControl.YELLOW_HEALTH),
	PEST_CONTROL_RED_HEALTH(WidgetID.PEST_CONTROL_GROUP_ID, WidgetID.PestControl.RED_HEALTH),
	PEST_CONTROL_PURPLE_ICON(WidgetID.PEST_CONTROL_GROUP_ID, WidgetID.PestControl.PURPLE_ICON),
	PEST_CONTROL_BLUE_ICON(WidgetID.PEST_CONTROL_GROUP_ID, WidgetID.PestControl.BLUE_ICON),
	PEST_CONTROL_YELLOW_ICON(WidgetID.PEST_CONTROL_GROUP_ID, WidgetID.PestControl.YELLOW_ICON),
	PEST_CONTROL_RED_ICON(WidgetID.PEST_CONTROL_GROUP_ID, WidgetID.PestControl.RED_ICON),
	PEST_CONTROL_ACTIVITY_BAR(WidgetID.PEST_CONTROL_GROUP_ID, WidgetID.PestControl.ACTIVITY_BAR),
	PEST_CONTROL_ACTIVITY_PROGRESS(WidgetID.PEST_CONTROL_GROUP_ID, WidgetID.PestControl.ACTIVITY_PROGRESS),

	VOLCANIC_MINE_GENERAL_INFOBOX_GROUP(WidgetID.VOLCANIC_MINE_GROUP_ID, WidgetID.VolcanicMine.GENERAL_INFOBOX_GROUP_ID),
	VOLCANIC_MINE_TIME_LEFT(WidgetID.VOLCANIC_MINE_GROUP_ID, WidgetID.VolcanicMine.TIME_LEFT),
	VOLCANIC_MINE_POINTS(WidgetID.VOLCANIC_MINE_GROUP_ID, WidgetID.VolcanicMine.POINTS),
	VOLCANIC_MINE_STABILITY(WidgetID.VOLCANIC_MINE_GROUP_ID, WidgetID.VolcanicMine.STABILITY),
	VOLCANIC_MINE_PLAYER_COUNT(WidgetID.VOLCANIC_MINE_GROUP_ID, WidgetID.VolcanicMine.PLAYER_COUNT),
	VOLCANIC_MINE_VENTS_INFOBOX_GROUP(WidgetID.VOLCANIC_MINE_GROUP_ID, WidgetID.VolcanicMine.VENTS_INFOBOX_GROUP_ID),
	VOLCANIC_MINE_VENT_A_PERCENTAGE(WidgetID.VOLCANIC_MINE_GROUP_ID, WidgetID.VolcanicMine.VENT_A_PERCENTAGE),
	VOLCANIC_MINE_VENT_B_PERCENTAGE(WidgetID.VOLCANIC_MINE_GROUP_ID, WidgetID.VolcanicMine.VENT_B_PERCENTAGE),
	VOLCANIC_MINE_VENT_C_PERCENTAGE(WidgetID.VOLCANIC_MINE_GROUP_ID, WidgetID.VolcanicMine.VENT_C_PERCENTAGE),
	VOLCANIC_MINE_VENT_A_STATUS(WidgetID.VOLCANIC_MINE_GROUP_ID, WidgetID.VolcanicMine.VENT_A_STATUS),
	VOLCANIC_MINE_VENT_B_STATUS(WidgetID.VOLCANIC_MINE_GROUP_ID, WidgetID.VolcanicMine.VENT_B_STATUS),
	VOLCANIC_MINE_VENT_C_STATUS(WidgetID.VOLCANIC_MINE_GROUP_ID, WidgetID.VolcanicMine.VENT_C_STATUS),

	CLAN_CHAT_TITLE(WidgetID.CLAN_CHAT_GROUP_ID, WidgetID.ClanChat.TITLE),
	CLAN_CHAT_NAME(WidgetID.CLAN_CHAT_GROUP_ID, WidgetID.ClanChat.NAME),
	CLAN_CHAT_OWNER(WidgetID.CLAN_CHAT_GROUP_ID, WidgetID.ClanChat.OWNER),

	BANK_ITEM_CONTAINER(WidgetID.BANK_GROUP_ID, WidgetID.Bank.ITEM_CONTAINER),
	BANK_INVENTORY_ITEMS_CONTAINER(WidgetID.BANK_INVENTORY_GROUP_ID, WidgetID.Bank.INVENTORY_ITEM_CONTAINER),
	BANK_TITLE_BAR(WidgetID.BANK_GROUP_ID, WidgetID.Bank.BANK_TITLE_BAR),
	BANK_ITEM_COUNT(WidgetID.BANK_GROUP_ID, WidgetID.Bank.BANK_ITEM_COUNT),

	GRAND_EXCHANGE_INVENTORY_ITEMS_CONTAINER(WidgetID.GRAND_EXCHANGE_INVENTORY_GROUP_ID, WidgetID.GrandExchange.INVENTORY_ITEM_CONTAINER),

	DEPOSIT_BOX_INVENTORY_ITEMS_CONTAINER(WidgetID.DEPOSIT_BOX_GROUP_ID, WidgetID.DepositBox.INVENTORY_ITEM_CONTAINER),

	SHOP_ITEMS_CONTAINER(WidgetID.SHOP_GROUP_ID, WidgetID.Shop.ITEMS_CONTAINER),
	SHOP_INVENTORY_ITEMS_CONTAINER(WidgetID.SHOP_INVENTORY_GROUP_ID, WidgetID.Shop.INVENTORY_ITEM_CONTAINER),

	GUIDE_PRICES_ITEMS_CONTAINER(WidgetID.GUIDE_PRICES_GROUP_ID, WidgetID.GuidePrices.ITEM_CONTAINER),
	GUIDE_PRICES_INVENTORY_ITEMS_CONTAINER(WidgetID.GUIDE_PRICES_INVENTORY_GROUP_ID, WidgetID.GuidePrices.INVENTORY_ITEM_CONTAINER),

	RUNE_POUCH_ITEM_CONTAINER(WidgetID.RUNE_POUCH_GROUP_ID, 0),

	MINIMAP_XP_ORB(WidgetID.MINIMAP_GROUP_ID, WidgetID.Minimap.XP_ORB),
	MINIMAP_PRAYER_ORB(WidgetID.MINIMAP_GROUP_ID, WidgetID.Minimap.PRAYER_ORB),
	MINIMAP_RUN_ORB(WidgetID.MINIMAP_GROUP_ID, WidgetID.Minimap.RUN_ORB),
	MINIMAP_HEALTH_ORB(WidgetID.MINIMAP_GROUP_ID, WidgetID.Minimap.HEALTH_ORB),
	MINIMAP_SPEC_ORB(WidgetID.MINIMAP_GROUP_ID, WidgetID.Minimap.SPEC_ORB),

	LOGIN_CLICK_TO_PLAY_SCREEN(WidgetID.LOGIN_CLICK_TO_PLAY_GROUP_ID, 0),

	FIXED_VIEWPORT(WidgetID.FIXED_VIEWPORT_GROUP_ID, WidgetID.Viewport.FIXED_VIEWPORT),
	FIXED_VIEWPORT_INVENTORY_TAB(WidgetID.FIXED_VIEWPORT_GROUP_ID, WidgetID.FixedViewport.INVENTORY_TAB),
	FIXED_VIEWPORT_PRAYER_TAB(WidgetID.FIXED_VIEWPORT_GROUP_ID, WidgetID.FixedViewport.PRAYER_TAB),
	MINIMAP_WIDGET(WidgetID.RESIZABLE_VIEWPORT_OLD_SCHOOL_BOX_GROUP_ID, WidgetID.Viewport.MINIMAP_WIDGET),
	RESIZABLE_VIEWPORT_OLD_SCHOOL_BOX(WidgetID.RESIZABLE_VIEWPORT_OLD_SCHOOL_BOX_GROUP_ID, WidgetID.Viewport.RESIZABLE_VIEWPORT_OLD_SCHOOL_BOX),
	RESIZABLE_VIEWPORT_INVENTORY_TAB(WidgetID.RESIZABLE_VIEWPORT_OLD_SCHOOL_BOX_GROUP_ID, WidgetID.ResizableViewport.INVENTORY_TAB),
	RESIZABLE_VIEWPORT_PRAYER_TAB(WidgetID.RESIZABLE_VIEWPORT_OLD_SCHOOL_BOX_GROUP_ID, WidgetID.ResizableViewport.PRAYER_TAB),
	RESIZABLE_VIEWPORT_BOTTOM_LINE(WidgetID.RESIZABLE_VIEWPORT_BOTTOM_LINE_GROUP_ID, WidgetID.Viewport.RESIZABLE_VIEWPORT_BOTTOM_LINE),
	RESIZABLE_VIEWPORT_BOTTOM_LINE_INVENTORY_TAB(WidgetID.RESIZABLE_VIEWPORT_BOTTOM_LINE_GROUP_ID, WidgetID.ResizableViewportBottomLine.INVENTORY_TAB),
	RESIZABLE_VIEWPORT_BOTTOM_LINE_PRAYER_TAB(WidgetID.RESIZABLE_VIEWPORT_BOTTOM_LINE_GROUP_ID, WidgetID.ResizableViewportBottomLine.PRAYER_TAB),

	PRAYER_THICK_SKIN(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.THICK_SKIN),
	PRAYER_BURST_OF_STRENGTH(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.BURST_OF_STRENGTH),
	PRAYER_CLARITY_OF_THOUGHT(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.CLARITY_OF_THOUGHT),
	PRAYER_SHARP_EYE(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.SHARP_EYE),
	PRAYER_MYSTIC_WILL(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.MYSTIC_WILL),
	PRAYER_ROCK_SKIN(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.ROCK_SKIN),
	PRAYER_SUPERHUMAN_STRENGTH(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.SUPERHUMAN_STRENGTH),
	PRAYER_IMPROVED_REFLEXES(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.IMPROVED_REFLEXES),
	PRAYER_RAPID_RESTORE(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.RAPID_RESTORE),
	PRAYER_RAPID_HEAL(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.RAPID_HEAL),
	PRAYER_PROTECT_ITEM(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.PROTECT_ITEM),
	PRAYER_HAWK_EYE(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.HAWK_EYE),
	PRAYER_MYSTIC_LORE(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.MYSTIC_LORE),
	PRAYER_STEEL_SKIN(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.STEEL_SKIN),
	PRAYER_ULTIMATE_STRENGTH(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.ULTIMATE_STRENGTH),
	PRAYER_INCREDIBLE_REFLEXES(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.INCREDIBLE_REFLEXES),
	PRAYER_PROTECT_FROM_MAGIC(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.PROTECT_FROM_MAGIC),
	PRAYER_PROTECT_FROM_MISSILES(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.PROTECT_FROM_MISSILES),
	PRAYER_PROTECT_FROM_MELEE(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.PROTECT_FROM_MELEE),
	PRAYER_EAGLE_EYE(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.EAGLE_EYE),
	PRAYER_MYSTIC_MIGHT(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.MYSTIC_MIGHT),
	PRAYER_RETRIBUTION(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.RETRIBUTION),
	PRAYER_REDEMPTION(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.REDEMPTION),
	PRAYER_SMITE(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.SMITE),
	PRAYER_PRESERVE(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.PRESERVE),
	PRAYER_CHIVALRY(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.CHIVALRY),
	PRAYER_PIETY(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.PIETY),
	PRAYER_RIGOUR(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.RIGOUR),
	PRAYER_AUGURY(WidgetID.PRAYER_GROUP_ID, WidgetID.Prayer.AUGURY),

	QUICK_PRAYER_PRAYERS(WidgetID.QUICK_PRAYERS_GROUP_ID, WidgetID.QuickPrayer.PRAYERS),

	COMBAT_LEVEL(WidgetID.COMBAT_GROUP_ID, WidgetID.Combat.LEVEL),
	COMBAT_STYLE_ONE(WidgetID.COMBAT_GROUP_ID, WidgetID.Combat.STYLE_ONE),
	COMBAT_STYLE_TWO(WidgetID.COMBAT_GROUP_ID, WidgetID.Combat.STYLE_TWO),
	COMBAT_STYLE_THREE(WidgetID.COMBAT_GROUP_ID, WidgetID.Combat.STYLE_THREE),
	COMBAT_STYLE_FOUR(WidgetID.COMBAT_GROUP_ID, WidgetID.Combat.STYLE_FOUR),
	COMBAT_SPELLS(WidgetID.COMBAT_GROUP_ID, WidgetID.Combat.SPELLS),
	COMBAT_DEFENSIVE_SPELL_BOX(WidgetID.COMBAT_GROUP_ID, WidgetID.Combat.DEFENSIVE_SPELL_BOX),
	COMBAT_DEFENSIVE_SPELL_ICON(WidgetID.COMBAT_GROUP_ID, WidgetID.Combat.DEFENSIVE_SPELL_ICON),
	COMBAT_DEFENSIVE_SPELL_SHIELD(WidgetID.COMBAT_GROUP_ID, WidgetID.Combat.DEFENSIVE_SPELL_SHIELD),
	COMBAT_DEFENSIVE_SPELL_TEXT(WidgetID.COMBAT_GROUP_ID, WidgetID.Combat.DEFENSIVE_SPELL_TEXT),
	COMBAT_SPELL_BOX(WidgetID.COMBAT_GROUP_ID, WidgetID.Combat.SPELL_BOX),
	COMBAT_SPELL_ICON(WidgetID.COMBAT_GROUP_ID, WidgetID.Combat.SPELL_ICON),
	COMBAT_SPELL_TEXT(WidgetID.COMBAT_GROUP_ID, WidgetID.Combat.SPELL_TEXT),

	DIALOG_SPRITE(WidgetID.DIALOG_SPRITE_GROUP_ID, 0),
	DIALOG_SPRITE_SPRITE(WidgetID.DIALOG_SPRITE_GROUP_ID, WidgetID.DialogSprite.SPRITE),
	DIALOG_SPRITE_TEXT(WidgetID.DIALOG_SPRITE_GROUP_ID, WidgetID.DialogSprite.TEXT),

	DIALOG_NPC_NAME(WidgetID.DIALOG_NPC_GROUP_ID, WidgetID.DialogNPC.NAME),
	DIALOG_NPC_TEXT(WidgetID.DIALOG_NPC_GROUP_ID, WidgetID.DialogNPC.TEXT),
	DIALOG_NPC_HEAD_MODEL(WidgetID.DIALOG_NPC_GROUP_ID, WidgetID.DialogNPC.HEAD_MODEL),
	DIALOG_NPC_CONTINUE(WidgetID.DIALOG_NPC_GROUP_ID, WidgetID.DialogNPC.CONTINUE),

	DIALOG_PLAYER_NAME(WidgetID.DIALOG_PLAYER_GROUP_ID, WidgetID.DialogPlayer.NAME),
	DIALOG_PLAYER_TEXT(WidgetID.DIALOG_PLAYER_GROUP_ID, WidgetID.DialogPlayer.TEXT),
	DIALOG_PLAYER_HEAD_MODEL(WidgetID.DIALOG_PLAYER_GROUP_ID, WidgetID.DialogPlayer.HEAD_MODEL),
	DIALOG_PLAYER_CONTINUE(WidgetID.DIALOG_PLAYER_GROUP_ID, WidgetID.DialogPlayer.CONTINUE),

	DIALOG_NOTIFICATION_TEXT(WidgetID.DIALOG_NOTIFICATION_GROUP_ID,WidgetID.DialogNotification.TEXT),
	DIALOG_NOTIFICATION_CONTINUE(WidgetID.DIALOG_NOTIFICATION_GROUP_ID, WidgetID.DialogNotification.CONTINUE),

	DIALOG_OPTION_TEXT(WidgetID.DIALOG_OPTION_GROUP_ID,WidgetID.DialogOption.TEXT),
	DIALOG_OPTION_OPTION1(WidgetID.DIALOG_OPTION_GROUP_ID,WidgetID.DialogOption.OPTION1),
	DIALOG_OPTION_OPTION2(WidgetID.DIALOG_OPTION_GROUP_ID,WidgetID.DialogOption.OPTION2),
	DIALOG_OPTION_OPTION3(WidgetID.DIALOG_OPTION_GROUP_ID,WidgetID.DialogOption.OPTION3),
	DIALOG_OPTION_OPTION4(WidgetID.DIALOG_OPTION_GROUP_ID,WidgetID.DialogOption.OPTION4),
	DIALOG_OPTION_OPTION5(WidgetID.DIALOG_OPTION_GROUP_ID,WidgetID.DialogOption.OPTION5),


	PRIVATE_CHAT_MESSAGE(WidgetID.PRIVATE_CHAT, 0),

	SLAYER_REWARDS_TOPBAR(WidgetID.SLAYER_REWARDS_GROUP_ID, WidgetID.SlayerRewards.TOP_BAR),

	CHATBOX(WidgetID.CHATBOX_GROUP_ID, WidgetID.Chatbox.CHATBOX_FRAME),
	CHATBOX_MESSAGES(WidgetID.CHATBOX_GROUP_ID, WidgetID.Chatbox.CHATBOX_MESSAGES),
	CHATBOX_BUTTONS(WidgetID.CHATBOX_GROUP_ID, WidgetID.Chatbox.CHATBOX_BUTTONS),
	CHATBOX_REPORT_TEXT(WidgetID.CHATBOX_GROUP_ID, WidgetID.Chatbox.CHATBOX_REPORT_TEXT),

	BA_HEAL_WAVE_TEXT(WidgetID.BA_HEALER_GROUP_ID, WidgetID.BarbarianAssault.CURRENT_WAVE),
	BA_HEAL_CALL_TEXT(WidgetID.BA_HEALER_GROUP_ID, WidgetID.BarbarianAssault.TO_CALL),
	BA_HEAL_LISTEN_TEXT(WidgetID.BA_HEALER_GROUP_ID, WidgetID.BarbarianAssault.CORRECT_STYLE),
	BA_HEAL_ROLE_TEXT(WidgetID.BA_HEALER_GROUP_ID, WidgetID.BarbarianAssault.ROLE),
	BA_HEAL_ROLE_SPRITE(WidgetID.BA_HEALER_GROUP_ID, WidgetID.BarbarianAssault.ROLE_SPRITE),

	BA_COLL_WAVE_TEXT(WidgetID.BA_COLLECTOR_GROUP_ID, WidgetID.BarbarianAssault.CURRENT_WAVE),
	BA_COLL_CALL_TEXT(WidgetID.BA_COLLECTOR_GROUP_ID, WidgetID.BarbarianAssault.TO_CALL),
	BA_COLL_LISTEN_TEXT(WidgetID.BA_COLLECTOR_GROUP_ID, WidgetID.BarbarianAssault.CORRECT_STYLE),
	BA_COLL_ROLE_TEXT(WidgetID.BA_COLLECTOR_GROUP_ID, WidgetID.BarbarianAssault.ROLE),
	BA_COLL_ROLE_SPRITE(WidgetID.BA_COLLECTOR_GROUP_ID, WidgetID.BarbarianAssault.ROLE_SPRITE),

	BA_ATK_WAVE_TEXT(WidgetID.BA_ATTACKER_GROUP_ID, WidgetID.BarbarianAssault.CURRENT_WAVE),
	BA_ATK_CALL_TEXT(WidgetID.BA_ATTACKER_GROUP_ID, WidgetID.BarbarianAssault.ATK.TO_CALL),
	BA_ATK_LISTEN_TEXT(WidgetID.BA_ATTACKER_GROUP_ID, WidgetID.BarbarianAssault.CORRECT_STYLE),
	BA_ATK_ROLE_TEXT(WidgetID.BA_ATTACKER_GROUP_ID, WidgetID.BarbarianAssault.ATK.ROLE),
	BA_ATK_ROLE_SPRITE(WidgetID.BA_ATTACKER_GROUP_ID, WidgetID.BarbarianAssault.ATK.ROLE_SPRITE),

	BA_DEF_WAVE_TEXT(WidgetID.BA_DEFENDER_GROUP_ID, WidgetID.BarbarianAssault.CURRENT_WAVE),
	BA_DEF_CALL_TEXT(WidgetID.BA_DEFENDER_GROUP_ID, WidgetID.BarbarianAssault.TO_CALL),
	BA_DEF_LISTEN_TEXT(WidgetID.BA_DEFENDER_GROUP_ID, WidgetID.BarbarianAssault.CORRECT_STYLE),
	BA_DEF_ROLE_TEXT(WidgetID.BA_DEFENDER_GROUP_ID, WidgetID.BarbarianAssault.ROLE),
	BA_DEF_ROLE_SPRITE(WidgetID.BA_DEFENDER_GROUP_ID, WidgetID.BarbarianAssault.ROLE_SPRITE),

	LEVEL_UP(WidgetID.LEVEL_UP_GROUP_ID, 0),
	LEVEL_UP_SKILL(WidgetID.LEVEL_UP_GROUP_ID, WidgetID.LevelUp.SKILL),
	LEVEL_UP_LEVEL(WidgetID.LEVEL_UP_GROUP_ID, WidgetID.LevelUp.LEVEL),

	QUEST_COMPLETED(WidgetID.QUEST_COMPLETED_GROUP_ID, 0),
	QUEST_COMPLETED_NAME_TEXT(WidgetID.QUEST_COMPLETED_GROUP_ID, WidgetID.QuestCompleted.NAME_TEXT),

	MOTHERLODE_MINE(WidgetID.MOTHERLODE_MINE_GROUP_ID, 0),

	PUZZLE_BOX(WidgetID.PUZZLE_BOX_GROUP_ID, WidgetID.PuzzleBox.VISIBLE_BOX),

	NIGHTMARE_ZONE(WidgetID.NIGHTMARE_ZONE_GROUP_ID, 1),

	RAIDS_POINTS_INFOBOX(WidgetID.RAIDS_GROUP_ID, WidgetID.Raids.POINTS_INFOBOX),

	BLAST_FURNACE_COFFER(WidgetID.BLAST_FURNACE_GROUP_ID, 0),

	PYRAMID_PLUNDER_DATA(WidgetID.PYRAMID_PLUNDER_GROUP_ID, 0),

	EXPERIENCE_TRACKER(WidgetID.EXPERIENCE_TRACKER_GROUP_ID, WidgetID.ExperienceTracker.WIDGET),
	EXPERIENCE_TRACKER_BOTTOM_BAR(WidgetID.EXPERIENCE_TRACKER_GROUP_ID, WidgetID.ExperienceTracker.BOTTOM_BAR),

	TITHE_FARM(WidgetID.TITHE_FARM_GROUP_ID, 1),

	BARROWS_INFO(WidgetID.BARROWS_GROUP_ID, 0),
	BARROWS_BROTHERS(WidgetID.BARROWS_GROUP_ID, WidgetID.Barrows.BARROWS_BROTHERS),
	BARROWS_POTENTIAL(WidgetID.BARROWS_GROUP_ID, WidgetID.Barrows.BARROWS_POTENTIAL);

	private final int groupId;
	private final int childId;

	WidgetInfo(int groupId, int childId)
	{
		this.groupId = groupId;
		this.childId = childId;
	}

	public int getId()
	{
		return groupId << 16 | childId;
	}

	public int getGroupId()
	{
		return groupId;
	}

	public int getChildId()
	{
		return childId;
	}

	public int getPackedId()
	{
		return groupId << 16 | childId;
	}

	public static int TO_GROUP(int id)
	{
		return id >>> 16;
	}

	public static int TO_CHILD(int id)
	{
		return id & 0xFFFF;
	}

	public static int PACK(int groupId, int childId)
	{
		return groupId << 16 | childId;
	}

}
