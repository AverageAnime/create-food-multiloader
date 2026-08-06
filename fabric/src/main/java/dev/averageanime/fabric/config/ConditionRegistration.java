package dev.averageanime.fabric.config;

import dev.averageanime.fabric.config.condition.EnabledCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.resources.ResourceLocation;

public class ConditionRegistration {

    public static void register() {
        EnabledCondition.TYPE = ResourceConditionType.create(
                ResourceLocation.fromNamespaceAndPath("createfood", "enabled"),
                EnabledCondition.CODEC
        );
        ResourceConditions.register(EnabledCondition.TYPE);
    }
}
