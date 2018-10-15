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
package net.runelite.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * An enumeration of local player variables.
 */
@AllArgsConstructor
@Getter
public enum VarPlayer
{
	ATTACK_STYLE(43),

	BANK_TAB(115),

	MEMBERSHIP_DAYS(1780),

	SPECIAL_ATTACK_PERCENT(300),
	SPECIAL_ATTACK_ENABLED(301),

	IN_RAID_PARTY(1427),

	NMZ_REWARD_POINTS(1060),

	SLAYER_TARGETS_LEFT(261),

	/**
	 * 0 : not started
	 * greater than 0 : in progress
	 * greater than 99 : completed
	 */
	THRONE_OF_MISCELLANIA(359),

	/**
	 * Experience tracker goal start.
	 */
	ATTACK_GOAL_START(1229),
	STRENGTH_GOAL_START(1230),
	RANGED_GOAL_START(1231),
	MAGIC_GOAL_START(1232),
	DEFENCE_GOAL_START(1233),
	HITPOINTS_GOAL_START(1234),
	PRAYER_GOAL_START(1235),
	AGILITY_GOAL_START(1236),
	HERBLORE_GOAL_START(1237),
	THIEVING_GOAL_START(1238),
	CRAFTING_GOAL_START(1239),
	RUNECRAFT_GOAL_START(1240),
	MINING_GOAL_START(1241),
	SMITHING_GOAL_START(1242),
	FISHING_GOAL_START(1243),
	COOKING_GOAL_START(1244),
	FIREMAKING_GOAL_START(1245),
	WOODCUTTING_GOAL_START(1246),
	FLETCHING_GOAL_START(1247),
	SLAYER_GOAL_START(1248),
	FARMING_GOAL_START(1249),
	CONSTRUCTION_GOAL_START(1250),
	HUNTER_GOAL_START(1251),

	/**
	 * Experience tracker goal end.
	 */
	ATTACK_GOAL_END(1253),
	STRENGTH_GOAL_END(1254),
	RANGED_GOAL_END(1255),
	MAGIC_GOAL_END(1256),
	DEFENCE_GOAL_END(1257),
	HITPOINTS_GOAL_END(1258),
	PRAYER_GOAL_END(1259),
	AGILITY_GOAL_END(1260),
	HERBLORE_GOAL_END(1261),
	THIEVING_GOAL_END(1262),
	CRAFTING_GOAL_END(1263),
	RUNECRAFT_GOAL_END(1264),
	MINING_GOAL_END(1265),
	SMITHING_GOAL_END(1266),
	FISHING_GOAL_END(1267),
	COOKING_GOAL_END(1268),
	FIREMAKING_GOAL_END(1269),
	WOODCUTTING_GOAL_END(1270),
	FLETCHING_GOAL_END(1271),
	SLAYER_GOAL_END(1272),
	FARMING_GOAL_END(1273),
	CONSTRUCTION_GOAL_END(1274),
	HUNTER_GOAL_END(1275),

	/**
	 * Bird house states
	 */
	BIRD_HOUSE_MEADOW_NORTH(1626),
	BIRD_HOUSE_MEADOW_SOUTH(1627),
	BIRD_HOUSE_VALLEY_NORTH(1628),
	BIRD_HOUSE_VALLEY_SOUTH(1629),

	/**
	 * Slayer Rewards interface states
	 */
	DOUBLE_TROUBLE(1344);

	private final int id;
}
