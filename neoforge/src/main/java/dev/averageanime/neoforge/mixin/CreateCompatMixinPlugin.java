package dev.averageanime.neoforge.mixin;

import net.neoforged.fml.loading.LoadingModList;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class CreateCompatMixinPlugin implements IMixinConfigPlugin {

    private boolean createPresent;

    /**
     * Detects Create through FML's loading mod list rather than by probing for one of its classes.
     * {@code Class.forName} must not be used here: mixin config plugins run during config <em>prepare</em>,
     * and loading any Create class resolves its supertypes with it — e.g. {@code BasinBlockEntity} pulls in
     * {@code SmartBlockEntity} and then vanilla {@code BlockEntity}. That yanks a vanilla class onto the
     * classloader before other mods' configs have been prepared, and any mod holding a mixin on it (Lithium
     * targets {@code BlockEntity}) aborts the launch with {@code MixinTargetAlreadyLoadedException}.
     */
    @Override
    public void onLoad(String mixinPackage) {
        createPresent = LoadingModList.get().getModFileById("create") != null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return createPresent;
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
