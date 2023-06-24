package net.runelite.client.plugins.randall.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.runelite.api.Client;
import net.runelite.api.Item;
import net.runelite.api.ItemComposition;

public class ItemModel implements DataModel {

    private final JsonObject data;

    public ItemModel(Client client, Item item) {
        ItemComposition itemComposition = client.getItemDefinition(item.getId());

        data = new JsonObject();
        data.addProperty("id", item.getId());
        data.addProperty("quantity", item.getQuantity());
        data.addProperty("name", itemComposition.getName());
        data.addProperty("members_name", itemComposition.getMembersName());
        data.addProperty("price", itemComposition.getPrice());
        data.addProperty("is_tradable", itemComposition.isTradeable());
        data.addProperty("is_stackable", itemComposition.isStackable());
        data.addProperty("is_members", itemComposition.isMembers());

        JsonArray inventoryActions = new JsonArray();
        for (String action : itemComposition.getInventoryActions()) {
            if (action != null) {
                inventoryActions.add(action);
            }
        }

        data.add("inventory_actions", inventoryActions);
    }

    @Override
    public JsonObject toJson() {
        return data;
    }
}
