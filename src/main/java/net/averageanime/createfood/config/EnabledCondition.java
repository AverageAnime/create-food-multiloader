package net.averageanime.createfood.config;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Recipe condition that disables a recipe when one or more item IDs are in the
 * {@code hideItems} client config list.
 *
 * JSON usage:
 * <pre>
 *   "conditions": [{"type": "createfood:enabled", "id": "pumpkin_pie_slice"}]
 *   "conditions": [{"type": "createfood:enabled", "ids": ["pumpkin_pie_slice", "ube_cake_base"]}]
 * </pre>
 */
public record EnabledCondition(List<String> itemIds) implements ICondition {

    public static final ResourceLocation ID = new ResourceLocation("createfood", "enabled");

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public boolean test(IContext context) {
        if (itemIds.isEmpty()) return true;
        if (CreateFoodConfig.CLIENT == null) return true;
        List<? extends String> hideList = CreateFoodConfig.CLIENT.hideItems.get();
        for (String itemId : itemIds) {
            if (hideList.contains(itemId)) return false;
        }
        return true;
    }

    public static class Serializer implements IConditionSerializer<EnabledCondition> {

        public static final Serializer INSTANCE = new Serializer();

        @Override
        public void write(JsonObject json, EnabledCondition value) {
            // write not required for server-side loading
        }

        @Override
        public EnabledCondition read(JsonObject json) {
            List<String> ids = new ArrayList<>();
            if (json.has("ids")) {
                json.getAsJsonArray("ids").forEach(e -> ids.add(e.getAsString()));
            } else if (json.has("id")) {
                ids.add(json.get("id").getAsString());
            }
            return new EnabledCondition(ids);
        }

        @Override
        public ResourceLocation getID() {
            return ID;
        }
    }
}
