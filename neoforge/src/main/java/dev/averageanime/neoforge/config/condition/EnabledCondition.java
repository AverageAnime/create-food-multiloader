package dev.averageanime.neoforge.config.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.averageanime.neoforge.config.ModConfig;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record EnabledCondition(List<String> itemIds) implements ICondition {

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

    @Override
    public boolean test(@NotNull IContext context) {
        if (itemIds.isEmpty()) {
            return true;
        }

        for (String itemId : itemIds) {
            if (!ModConfig.isItemEnabled(itemId)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public @NotNull MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}