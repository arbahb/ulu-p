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
package net.runelite.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Varbits
{
	/*
	 * If chatbox is transparent or not
	 */
	TRANSPARANT_CHATBOX(4608),

	/**
	 * Runecraft pouches
	 */
	POUCH_SMALL(603),
	POUCH_MEDIUM(604),
	POUCH_LARGE(605),
	POUCH_GIANT(606),

	/**
	 * Runepouch
	 */
	RUNE_POUCH_RUNE1(29),
	RUNE_POUCH_RUNE2(1622),
	RUNE_POUCH_RUNE3(1623),
	RUNE_POUCH_AMOUNT1(1624),
	RUNE_POUCH_AMOUNT2(1625),
	RUNE_POUCH_AMOUNT3(1626),

	/**
	 * Prayers
	 */
	PRAYER_THICK_SKIN(4104),
	PRAYER_BURST_OF_STRENGTH(4105),
	PRAYER_CLARITY_OF_THOUGHT(4106),
	PRAYER_SHARP_EYE(4122),
	PRAYER_MYSTIC_WILL(4123),
	PRAYER_ROCK_SKIN(4107),
	PRAYER_SUPERHUMAN_STRENGTH(4108),
	PRAYER_IMPROVED_REFLEXES(4109),
	PRAYER_RAPID_RESTORE(4110),
	PRAYER_RAPID_HEAL(4111),
	PRAYER_PROTECT_ITEM(4112),
	PRAYER_HAWK_EYE(4124),
	PRAYER_MYSTIC_LORE(4125),
	PRAYER_STEEL_SKIN(4113),
	PRAYER_ULTIMATE_STRENGTH(4114),
	PRAYER_INCREDIBLE_REFLEXES(4115),
	PRAYER_PROTECT_FROM_MAGIC(4116),
	PRAYER_PROTECT_FROM_MISSILES(4117),
	PRAYER_PROTECT_FROM_MELEE(4118),
	PRAYER_EAGLE_EYE(4126),
	PRAYER_MYSTIC_MIGHT(4127),
	PRAYER_RETRIBUTION(4119),
	PRAYER_REDEMPTION(4120),
	PRAYER_SMITE(4121),
	PRAYER_CHIVALRY(4128),
	PRAYER_PIETY(4129),
	PRAYER_PRESERVE(5466),
	PRAYER_RIGOUR(5464),
	PRAYER_AUGURY(5465),

	/**
	 * Diary Entries
	 */
	DIARY_ARDOUGNE_EASY(4458),
	DIARY_ARDOUGNE_MEDIUM(4459),
	DIARY_ARDOUGNE_HARD(4460),
	DIARY_ARDOUGNE_ELITE(4461),

	DIARY_DESERT_EASY(4483),
	DIARY_DESERT_MEDIUM(4484),
	DIARY_DESERT_HARD(4485),
	DIARY_DESERT_ELITE(4486),

	DIARY_FALADOR_EASY(4462),
	DIARY_FALADOR_MEDIUM(4463),
	DIARY_FALADOR_HARD(4464),
	DIARY_FALADOR_ELITE(4465),

	DIARY_FREMENNIK_EASY(4491),
	DIARY_FREMENNIK_MEDIUM(4492),
	DIARY_FREMENNIK_HARD(4493),
	DIARY_FREMENNIK_ELITE(4494),

	DIARY_KANDARIN_EASY(4475),
	DIARY_KANDARIN_MEDIUM(4476),
	DIARY_KANDARIN_HARD(4477),
	DIARY_KANDARIN_ELITE(4478),

	DIARY_KARAMJA_EASY(3578),
	DIARY_KARAMJA_MEDIUM(3599),
	DIARY_KARAMJA_HARD(3611),
	DIARY_KARAMJA_ELITE(4566),

	DIARY_LUMBRIDGE_EASY(4495),
	DIARY_LUMBRIDGE_MEDIUM(4496),
	DIARY_LUMBRIDGE_HARD(4497),
	DIARY_LUMBRIDGE_ELITE(4498),

	DIARY_MORYTANIA_EASY(4487),
	DIARY_MORYTANIA_MEDIUM(4488),
	DIARY_MORYTANIA_HARD(4489),
	DIARY_MORYTANIA_ELITE(4490),

	DIARY_VARROCK_EASY(4479),
	DIARY_VARROCK_MEDIUM(4480),
	DIARY_VARROCK_HARD(4481),
	DIARY_VARROCK_ELITE(4482),

	DIARY_WESTERN_EASY(4471),
	DIARY_WESTERN_MEDIUM(4472),
	DIARY_WESTERN_HARD(4473),
	DIARY_WESTERN_ELITE(4474),

	DIARY_WILDERNESS_EASY(4466),
	DIARY_WILDERNESS_MEDIUM(4467),
	DIARY_WILDERNESS_HARD(4468),
	DIARY_WILDERNESS_ELITE(4469),

	/**
	 * Equipped weapon type
	 */
	EQUIPPED_WEAPON_TYPE(357),

	/**
	 * Defensive casting mode
	 */
	DEFENSIVE_CASTING_MODE(2668),

	/**
	 * Options
	 */
	SIDE_PANELS(4607),

	/**
	 * Herbiboar Trails
	 */
	HB_TRAIL_31303(5737),
	HB_TRAIL_31306(5738),
	HB_TRAIL_31309(5739),
	HB_TRAIL_31312(5740),
	HB_TRAIL_31315(5741),
	HB_TRAIL_31318(5742),
	HB_TRAIL_31321(5743),
	HB_TRAIL_31324(5744),
	HB_TRAIL_31327(5745),
	HB_TRAIL_31330(5746),

	HB_TRAIL_31333(5768),
	HB_TRAIL_31336(5769),
	HB_TRAIL_31339(5770),
	HB_TRAIL_31342(5771),
	HB_TRAIL_31345(5772),
	HB_TRAIL_31348(5773),
	HB_TRAIL_31351(5774),
	HB_TRAIL_31354(5775),
	HB_TRAIL_31357(5776),
	HB_TRAIL_31360(5777),

	HB_TRAIL_31363(5747),
	HB_TRAIL_31366(5748),
	HB_TRAIL_31369(5749),
	HB_TRAIL_31372(5750),

	HB_FINISH(5766),
	HB_STARTED(5767), //not working

	/**
	 * Barbarian Assault
	 */
	IN_GAME_BA(3923),

	/**
	 * Motherlode mine sack
	 */
	SACK_NUMBER(5558),

	/**
	 * Experience tracker
	 */
	EXPERIENCE_TRACKER_POSITION(4692),
	EXPERIENCE_TRACKER_COUNTER(4697),
	EXPERIENCE_TRACKER_PROGRESS_BAR(4698),

	/**
	 * Experience drop color
	 */
	EXPERIENCE_DROP_COLOR(4695),

	/**
	 * Blast Mine
	 */
	BLAST_MINE_COAL(4924, 694, 5, 13),
	BLAST_MINE_GOLD(4925, 694, 14, 22),
	BLAST_MINE_MITHRIL(4926, 694, 23, 31),
	BLAST_MINE_ADAMANTITE(4921, 203, 5, 13),
	BLAST_MINE_RUNITE(4922, 203, 14, 22),

	/**
	 * Raids
	 */
	IN_RAID(5432),
	TOTAL_POINTS(5431),
	PERSONAL_POINTS(5422),

	/**
	 * Nightmare Zone
	 */
	NMZ_ABSORPTION(3956),
	NMZ_POINTS(3949),

	/**
	 * Blast Furnace
	 */
	BLAST_FURNACE_COPPER_ORE(959),
	BLAST_FURNACE_TIN_ORE(950),
	BLAST_FURNACE_IRON_ORE(951),
	BLAST_FURNACE_COAL(949),
	BLAST_FURNACE_MITHRIL_ORE(952),
	BLAST_FURNACE_ADAMANTITE_ORE(953),
	BLAST_FURNACE_RUNITE_ORE(954),
	BLAST_FURNACE_SILVER_ORE(956),
	BLAST_FURNACE_GOLD_ORE(955),

	BLAST_FURNACE_BRONZE_BAR(941),
	BLAST_FURNACE_IRON_BAR(942),
	BLAST_FURNACE_STEEL_BAR(943),
	BLAST_FURNACE_MITHRIL_BAR(944),
	BLAST_FURNACE_ADAMANTITE_BAR(945),
	BLAST_FURNACE_RUNITE_BAR(946),
	BLAST_FURNACE_SILVER_BAR(948),
	BLAST_FURNACE_GOLD_BAR(947),

	BLAST_FURNACE_COFFER(5357),

	/**
	 * Pyramid plunder
	 */
	PYRAMID_PLUNDER_TIMER(2375),
	PYRAMID_PLUNDER_ROOM(2377),

	/**
	 * Multicombat area
	 */
	MULTICOMBAT_AREA(4605),

	/**
	 * Kingdom Management
	 */
	KINGDOM_FAVOR(72),
	KINGDOM_COFFER(74);

	/**
	 * varbit id
	 */
	private final int id;
}
