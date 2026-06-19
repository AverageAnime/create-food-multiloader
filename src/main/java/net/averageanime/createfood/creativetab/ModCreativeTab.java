package net.averageanime.createfood.creativetab;

import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import it.unimi.dsi.fastutil.objects.ReferenceLinkedOpenHashSet;
import net.averageanime.createfood.CreateFood;
import net.averageanime.createfood.block.ModBlocks;
import net.averageanime.createfood.block.ModDisplayBlocks;
import net.averageanime.createfood.config.ConfigLogic;
import net.averageanime.createfood.fluid.FluidEntry;
import net.averageanime.createfood.fluid.ModFluids;
import net.averageanime.createfood.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ModCreativeTab {

    private static final DeferredRegister<CreativeModeTab> REGISTER;
    public static final RegistryObject<CreativeModeTab> CREATIVE_TAB;
    public static final RegistryObject<CreativeModeTab> FLUID_TAB;
    public static final RegistryObject<CreativeModeTab> DISPLAY_TAB;

    public ModCreativeTab() {}

    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }

    static {
        REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateFood.ID);
        CREATIVE_TAB = REGISTER.register("base", () ->
            CreativeModeTab.builder()
                    .title(Component.translatable("tab.createfood"))
                    .icon(() -> new ItemStack(ModItems.BREAKFAST_PLATE.get()))
                    .displayItems((params, output) -> {
                        List<Item> items = new LinkedList<>();
                        items.addAll(collectStorageItems());
                        items.addAll(collectItems());
                        items.addAll(collectBlocks());
                        items.sort(Comparator.comparing(item -> {
                            String desc = item.getDescriptionId();
                            int dot = desc.lastIndexOf('.');
                            return dot >= 0 ? desc.substring(dot + 1) : desc;
                        }));
                        filterAndOutput(output, items);
                    })
                    .build()
        );
        FLUID_TAB = REGISTER.register("fluid", () ->
            CreativeModeTab.builder()
                    .title(Component.translatable("tab.createfood.fluid"))
                    .withTabsBefore(CREATIVE_TAB.getKey())
                    .icon(() -> new ItemStack(ModFluids.CUSTARD.BUCKET().get()))
                    .displayItems((params, output) -> filterAndOutput(output, collectFluids()))
                    .build()
        );
        DISPLAY_TAB = REGISTER.register("display", () ->
            CreativeModeTab.builder()
                    .title(Component.translatable("tab.createfood.display"))
                    .withTabsBefore(FLUID_TAB.getKey())
                    .icon(() -> {
                        Block b = net.minecraftforge.registries.ForgeRegistries.BLOCKS
                                .getValue(new net.minecraft.resources.ResourceLocation(CreateFood.ID, "breakfast_plate_block"));
                        return new ItemStack(b != null && b.asItem() != Items.AIR
                                ? b.asItem() : ModDisplayBlocks.PLATE_BLOCK.get().asItem());
                    })
                    .displayItems((params, output) -> filterAndOutput(output, collectDisplayBlocks()))
                    .build()
        );
    }

    private static List<Item> collectStorageItems() {
        return List.of(ModBlocks.CLOTH_SACK_ITEM.get(), ModBlocks.RATION_BOX_ITEM.get());
    }

    private static List<Item> collectDisplayBlocks() {
        List<Item> items = new ReferenceArrayList<>();
        for (RegistryObject<Item> entry : ModDisplayBlocks.ITEMS.getEntries()) {
            String path = entry.getId().getPath();
            if (path.equals("plate_block") || path.equals("small_plate_block")
                    || path.equals("generic_display_plate_block")) continue;
            Item item = entry.get();
            if (item == Items.AIR) continue;
            if (!ConfigLogic.isDisplayBlockEnabled(new ItemStack(item))) continue;
            items.add(item);
        }
        return items;
    }

    private static List<Item> collectBlocks() {
        List<Item> items = new ReferenceArrayList<>();
        for (RegistryObject<Block> entry : ModBlocks.BLOCKS.getEntries()) {
            if (entry.getId().getPath().contains("candle")) continue;
            Item item = entry.get().asItem();
            if (item != Items.AIR) {
                items.add(item);
            }
        }
        return new ReferenceArrayList<>(new ReferenceLinkedOpenHashSet<>(items));
    }

    private static List<Item> collectItems() {
        List<Item> items = new ReferenceArrayList<>();
        for (RegistryObject<Item> entry : ModItems.ITEMS.getEntries()) {
            Item item = entry.get();
            if (!(item instanceof BlockItem) && !(item instanceof BucketItem)) {
                items.add(item);
            }
        }
        return items;
    }

    private static List<Item> collectFluids() {
        List<Item> items = new ReferenceArrayList<>();
        for (FluidEntry.Type entry : ModFluids.BY_ID.values()) {
            Item bucket = entry.BUCKET().get();
            if (bucket == Items.AIR || items.contains(bucket)) continue;
            if (!ConfigLogic.isFluidBucketEnabled(new ItemStack(bucket))) continue;
            items.add(bucket);
        }
        return items;
    }

    private static void filterAndOutput(CreativeModeTab.Output output, List<Item> items) {
        for (Item item : items) {
            if (item.toString().contains("incomplete")) continue;
            if (item.toString().contains("guide")) continue;
            if (item.toString().contains("blaze_burner")) continue;
            if (item.toString().contains("creative_tab_icon")) continue;
            if (!ConfigLogic.isItemEnabled(new ItemStack(item))) continue;
            output.accept(item);
        }
    }
}
