package dev.averageanime.neoforge.mixin;

import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class CreateCompatMixinPlugin implements IMixinConfigPlugin {

    private boolean createPresent;

    @Override
    public void onLoad(String mixinPackage) {
        createPresent = classExists("com.simibubi.create.content.kinetics.fan.processing.AllFanProcessingTypes");
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return createPresent;
    }

    private static boolean classExists(String name) {
        try {
            Class.forName(name, false, CreateCompatMixinPlugin.class.getClassLoader());
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, org.objectweb.asm.tree.ClassNode targetClass,
                         String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, org.objectweb.asm.tree.ClassNode targetClass,
                          String mixinClassName, IMixinInfo mixinInfo) {
    }
}
