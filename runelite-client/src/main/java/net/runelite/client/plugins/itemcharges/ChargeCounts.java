/*
 * Copyright (c) 2017, Seth <Sethtroll3@gmail.com>
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
package net.runelite.client.plugins.itemcharges;

import java.util.HashMap;
import java.util.Map;

import net.runelite.api.ItemID;

public class ChargeCounts
{
	private static final Map<Integer, Integer> MAP = new HashMap<>();

	static
	{
		MAP.put(ItemID.AMULET_OF_GLORY6, 6);
		MAP.put(ItemID.AMULET_OF_GLORY5, 5);
		MAP.put(ItemID.AMULET_OF_GLORY4, 4);
		MAP.put(ItemID.AMULET_OF_GLORY3, 3);
		MAP.put(ItemID.AMULET_OF_GLORY2, 2);
		MAP.put(ItemID.AMULET_OF_GLORY1, 1);

		MAP.put(ItemID.AMULET_OF_GLORY_T6, 6);
		MAP.put(ItemID.AMULET_OF_GLORY_T5, 5);
		MAP.put(ItemID.AMULET_OF_GLORY_T4, 4);
		MAP.put(ItemID.AMULET_OF_GLORY_T3, 3);
		MAP.put(ItemID.AMULET_OF_GLORY_T2, 2);
		MAP.put(ItemID.AMULET_OF_GLORY_T1, 1);

		MAP.put(ItemID.RING_OF_DUELING8, 8);
		MAP.put(ItemID.RING_OF_DUELING7, 7);
		MAP.put(ItemID.RING_OF_DUELING6, 6);
		MAP.put(ItemID.RING_OF_DUELING5, 5);
		MAP.put(ItemID.RING_OF_DUELING4, 4);
		MAP.put(ItemID.RING_OF_DUELING3, 3);
		MAP.put(ItemID.RING_OF_DUELING2, 2);
		MAP.put(ItemID.RING_OF_DUELING1, 1);

		MAP.put(ItemID.GAMES_NECKLACE8, 8);
		MAP.put(ItemID.GAMES_NECKLACE7, 7);
		MAP.put(ItemID.GAMES_NECKLACE6, 6);
		MAP.put(ItemID.GAMES_NECKLACE5, 5);
		MAP.put(ItemID.GAMES_NECKLACE4, 4);
		MAP.put(ItemID.GAMES_NECKLACE3, 3);
		MAP.put(ItemID.GAMES_NECKLACE2, 2);
		MAP.put(ItemID.GAMES_NECKLACE1, 1);

		MAP.put(ItemID.RING_OF_WEALTH_5, 5);
		MAP.put(ItemID.RING_OF_WEALTH_4, 4);
		MAP.put(ItemID.RING_OF_WEALTH_3, 3);
		MAP.put(ItemID.RING_OF_WEALTH_2, 2);
		MAP.put(ItemID.RING_OF_WEALTH_1, 1);

		MAP.put(ItemID.SLAYER_RING_6, 6);
		MAP.put(ItemID.SLAYER_RING_5, 5);
		MAP.put(ItemID.SLAYER_RING_4, 4);
		MAP.put(ItemID.SLAYER_RING_3, 3);
		MAP.put(ItemID.SLAYER_RING_2, 2);
		MAP.put(ItemID.SLAYER_RING_1, 1);

		MAP.put(ItemID.SKILLS_NECKLACE6, 6);
		MAP.put(ItemID.SKILLS_NECKLACE5, 5);
		MAP.put(ItemID.SKILLS_NECKLACE4, 4);
		MAP.put(ItemID.SKILLS_NECKLACE3, 3);
		MAP.put(ItemID.SKILLS_NECKLACE2, 2);
		MAP.put(ItemID.SKILLS_NECKLACE1, 1);

		MAP.put(ItemID.COMBAT_BRACELET6, 6);
		MAP.put(ItemID.COMBAT_BRACELET5, 5);
		MAP.put(ItemID.COMBAT_BRACELET4, 4);
		MAP.put(ItemID.COMBAT_BRACELET3, 3);
		MAP.put(ItemID.COMBAT_BRACELET2, 2);
		MAP.put(ItemID.COMBAT_BRACELET1, 1);

		MAP.put(ItemID.DIGSITE_PENDANT_5, 5);
		MAP.put(ItemID.DIGSITE_PENDANT_4, 4);
		MAP.put(ItemID.DIGSITE_PENDANT_3, 3);
		MAP.put(ItemID.DIGSITE_PENDANT_2, 2);
		MAP.put(ItemID.DIGSITE_PENDANT_1, 1);

		MAP.put(ItemID.NECKLACE_OF_PASSAGE5, 5);
		MAP.put(ItemID.NECKLACE_OF_PASSAGE4, 4);
		MAP.put(ItemID.NECKLACE_OF_PASSAGE3, 3);
		MAP.put(ItemID.NECKLACE_OF_PASSAGE2, 2);
		MAP.put(ItemID.NECKLACE_OF_PASSAGE1, 1);

		MAP.put(ItemID.BURNING_AMULET5, 5);
		MAP.put(ItemID.BURNING_AMULET4, 4);
		MAP.put(ItemID.BURNING_AMULET3, 3);
		MAP.put(ItemID.BURNING_AMULET2, 2);
		MAP.put(ItemID.BURNING_AMULET1, 1);

		MAP.put(ItemID.RING_OF_RETURNING5, 5);
		MAP.put(ItemID.RING_OF_RETURNING4, 4);
		MAP.put(ItemID.RING_OF_RETURNING3, 3);
		MAP.put(ItemID.RING_OF_RETURNING2, 2);
		MAP.put(ItemID.RING_OF_RETURNING1, 1);

		MAP.put(ItemID.TELEPORT_CRYSTAL_5, 5);
		MAP.put(ItemID.TELEPORT_CRYSTAL_4, 4);
		MAP.put(ItemID.TELEPORT_CRYSTAL_3, 3);
		MAP.put(ItemID.TELEPORT_CRYSTAL_2, 2);
		MAP.put(ItemID.TELEPORT_CRYSTAL_1, 1);

		MAP.put(ItemID.PHARAOHS_SCEPTRE_8, 8);
		MAP.put(ItemID.PHARAOHS_SCEPTRE_7, 7);
		MAP.put(ItemID.PHARAOHS_SCEPTRE_6, 6);
		MAP.put(ItemID.PHARAOHS_SCEPTRE_5, 5);
		MAP.put(ItemID.PHARAOHS_SCEPTRE_4, 4);
		MAP.put(ItemID.PHARAOHS_SCEPTRE_3, 3);
		MAP.put(ItemID.PHARAOHS_SCEPTRE_2, 2);
		MAP.put(ItemID.PHARAOHS_SCEPTRE_1, 1);

		MAP.put(ItemID.WATERSKIN4, 4);
		MAP.put(ItemID.WATERSKIN3, 3);
		MAP.put(ItemID.WATERSKIN2, 2);
		MAP.put(ItemID.WATERSKIN1, 1);

		MAP.put(ItemID.IMPINABOX2, 2);
		MAP.put(ItemID.IMPINABOX1, 1);

		MAP.put(ItemID.ENCHANTED_LYRE5, 5);
		MAP.put(ItemID.ENCHANTED_LYRE4, 4);
		MAP.put(ItemID.ENCHANTED_LYRE3, 3);
		MAP.put(ItemID.ENCHANTED_LYRE2, 2);
		MAP.put(ItemID.ENCHANTED_LYRE1, 1);
	}

	public static Integer getCharges(int itemId)
	{
		return MAP.get(itemId);
	}
}
