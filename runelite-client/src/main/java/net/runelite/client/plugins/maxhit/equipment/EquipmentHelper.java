package net.runelite.client.plugins.maxhit.equipment;

import net.runelite.api.Client;
import net.runelite.api.Item;
import net.runelite.client.plugins.maxhit.config.EquipmentSlotConfig;

import java.util.List;

public class EquipmentHelper {

    public static boolean wearsItemSet(Client client, Item[] equipedItems, List<EquipmentSlotItem> items) {
        return items.stream().allMatch(item -> wearsItem(client, equipedItems, item.getEquipmentSlot(), item.getItem()));
    }

    public static boolean wearsItem(Client client, Item[] equipedItems, EquipmentSlotConfig slot, String item){
        return client.getItemDefinition(equipedItems[slot.getId()].getId()).getName().toLowerCase().contains(item);
    }

}
