package net.runelite.client.plugins.rechargeableitems;

import net.runelite.client.plugins.rechargeableitems.items.Arclight;
import net.runelite.client.plugins.rechargeableitems.items.RechargeableItem;
import net.runelite.client.plugins.rechargeableitems.items.TridentOfTheSeas;
import net.runelite.client.plugins.rechargeableitems.items.TridentOfTheSwamp;

public class RechargeableItemFactory
{
	public static RechargeableItem createRechargeableItem(RechargeableItemEnum itemEnum)
	{
		RechargeableItem rechargeableItem = null;
		switch (itemEnum)
		{
			case ARCLIGHT:
				rechargeableItem = new Arclight();
				break;
			case TRIDENT_OF_THE_SWAMP:
			case TRIDENT_OF_THE_SWAMP_E:
				rechargeableItem = new TridentOfTheSwamp();
				break;
			case TRIDENT_OF_THE_SEAS:
			case TRIDENT_OF_THE_SEAS_E:
			case TRIDENT_OF_THE_SEAS_FULL:
				rechargeableItem = new TridentOfTheSeas();
				break;
		}
		return rechargeableItem;
	}
}
