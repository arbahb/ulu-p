/*
 * Copyright (c) 2016-2018, Adam <Adam@sigterm.info>
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
package net.runelite.client.plugins.itemstats.potions;

import com.google.common.collect.ImmutableSet;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.api.ItemID;
import net.runelite.client.plugins.itemstats.StatBoost;
import static net.runelite.client.plugins.itemstats.stats.Stats.PRAYER;
import net.runelite.client.util.ItemUtil;

public class PrayerPotion extends StatBoost
{
	private final int delta;

	public PrayerPotion(int delta)
	{
		super(PRAYER, false);
		this.delta = delta;
	}

	private static final int RING_SLOT = 12;
	private static final int CAPE_SLOT = 1;
	private static final ImmutableSet<Integer> HOLY_WRENCH_IDS = ImmutableSet.of(
		ItemID.HOLY_WRENCH, ItemID.PRAYER_CAPE, ItemID.PRAYER_CAPET, ItemID.MAX_CAPE, ItemID.MAX_CAPE_13342,
		// No idea what these are
		ItemID.PRAYER_CAPE_10643, ItemID.MAX_CAPE_13282
	);

	@Override
	public int heals(Client client)
	{
		boolean hasHolyWrench = false;

		ItemContainer equipContainer = client.getItemContainer(InventoryID.EQUIPMENT);
		if (equipContainer != null)
		{
			Item[] equip = equipContainer.getItems();

			hasHolyWrench |= equip.length > RING_SLOT && equip[RING_SLOT].getId() == ItemID.RING_OF_THE_GODS_I;
			if (equip.length > CAPE_SLOT)
			{
				hasHolyWrench |= HOLY_WRENCH_IDS.contains(equip[CAPE_SLOT].getId());
			}
		}
		if (!hasHolyWrench)
		{
			ItemContainer invContainer = client.getItemContainer(InventoryID.INVENTORY);
			if (invContainer != null)
			{
				hasHolyWrench = ItemUtil.containsAnyItemId(invContainer.getItems(), HOLY_WRENCH_IDS);
			}
		}

		double perc = hasHolyWrench ? .27 : .25;
		int max = getStat().getMaximum(client);
		return (((int) (max * perc)) * (delta >= 0 ? 1 : -1)) + delta;
	}

}
