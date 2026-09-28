package dev.averageanime.neoforge.mixin;

import dev.averageanime.createfood.lib.mixin.ModPresenceMixinPlugin;

public class CreateCompatMixinPlugin extends ModPresenceMixinPlugin {

    @Override
    protected String requiredModId() {
        return "create";
    }
}
