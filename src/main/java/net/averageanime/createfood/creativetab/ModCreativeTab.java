package net.averageanime.createfood.creativetab;

import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.foundation.utility.Components;
import com.tterrag.registrate.util.entry.RegistryEntry;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import it.unimi.dsi.fastutil.objects.ReferenceLinkedOpenHashSet;
import net.averageanime.createfood.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.averageanime.createfood.CreateFood;
import vectorwing.farmersdelight.common.block.PieBlock;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static net.averageanime.createfood.CreateFood.REGISTRATE;

public class ModCreativeTab {

    private static final DeferredRegister<CreativeModeTab> REGISTER;
    public static final RegistryObject<CreativeModeTab> CREATIVE_TAB;

    public ModCreativeTab() {}

    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }

    static {
        REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateFood.ID);
        CREATIVE_TAB = REGISTER.register("base", () -> {
            return CreativeModeTab.builder().title(Components.literal("Create: Food"))
                    .withTabsBefore(AllCreativeModeTabs.BASE_CREATIVE_TAB.getKey(),AllCreativeModeTabs.PALETTES_CREATIVE_TAB.getKey())
                    .icon(ModItems.BREAKFAST_PLATE::asStack)
                    .displayItems(new DisplayItemsGenerator())
                    .build();
        });
    }

    private static class DisplayItemsGenerator implements CreativeModeTab.DisplayItemsGenerator {

        @Override
        public void accept(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
            List<Item> items = new LinkedList();
            items.addAll(this.collectItems());
            items.addAll(this.collectBlocks());
            items.addAll(this.collectFluid());
            filterAndOutput(output, items);
        }

        private List<Item> collectBlocks() {
            List<Item> items = new ReferenceArrayList();
            Iterator var3 = REGISTRATE.getAll(Registries.BLOCK).iterator();

            while(var3.hasNext()) {
                RegistryEntry<Block> entry = (RegistryEntry)var3.next();
                Item item = entry.get().asItem();
                if (item != Items.AIR) {
                    items.add(item);
                }
            }

            items = new ReferenceArrayList(new ReferenceLinkedOpenHashSet(items));
            return items;
        }

        private List<Item> collectItems() {
            List<Item> items = new ReferenceArrayList();
            Iterator var3 = REGISTRATE.getAll(Registries.ITEM).iterator();

            while(var3.hasNext()) {
                RegistryEntry<Item> entry = (RegistryEntry)var3.next();
                Item item = entry.get();
                if (!(item instanceof BlockItem) && !(item instanceof BucketItem)) {
                    items.add(item);
                }
            }

            return items;
        }

        private List<Item> collectFluid() {
            List<Item> items = new ReferenceArrayList();
            Iterator var3 = REGISTRATE.getAll(Registries.FLUID).iterator();

            while(var3.hasNext()) {
                RegistryEntry<ForgeFlowingFluid> entry = (RegistryEntry)var3.next();
                ForgeFlowingFluid fluid = entry.get();
                if (fluid.getBucket()!=Items.AIR && !items.contains(fluid.getBucket())) {
                    items.add(fluid.getBucket());
                }
            }

            return items;
        }

        private static void filterAndOutput(CreativeModeTab.Output output, List<Item> items) {
            Iterator var4 = items.iterator();
            while(var4.hasNext()) {
                Item item = (Item)var4.next();
                if(item.toString().contains("incomplete")) continue;
                if(item.toString().contains("guide")) continue;
                if(item.toString().contains("blaze_burner")) continue;
                if(item.toString().contains("creative_tab_icon")) continue;
                output.accept(item);
            }
        }
    }
}
