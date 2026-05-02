package dev.averageanime.fabric.config.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.averageanime.fabric.config.ModConfig;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.minecraft.core.HolderLookup;

import java.util.List;

public record EnabledCondition(List<String> itemIds) implements ResourceCondition {

    public static final MapCodec<EnabledCondition> CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                    Codec.STRING.listOf().optionalFieldOf("ids", List.of()).forGetter(EnabledCondition::itemIds),
                    Codec.STRING.optionalFieldOf("id", "").forGetter(c -> c.itemIds.isEmpty() ? "" : c.itemIds.getFirst())
            ).apply(builder, (ids, singleId) -> {
                List<String> finalIds = !ids.isEmpty() ? ids :
                        (!singleId.isEmpty() ? List.of(singleId) : List.of());
                return new EnabledCondition(finalIds);
            })
    );

    public static ResourceConditionType<EnabledCondition> TYPE;

    @Override
    public ResourceConditionType<?> getType() {
        return TYPE;
    }

    @Override
    public boolean test(HolderLookup.Provider registries) {
        if (itemIds.isEmpty()) return true;
        for (String itemId : itemIds) {
            if (!ModConfig.isItemEnabled(itemId)) return false;
        }
        return true;
    }
}
