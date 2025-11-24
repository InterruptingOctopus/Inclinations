package com.interruptingoctopus.inclinations.item;

import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Inclinations.MOD_ID);

    // --- Creative Tab Definitions ---
    @SuppressWarnings("unused")
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> INCLINATIONS_INGREDIENTS_TAB = registerCreativeTab(
            "inclinations_ingredients_tab",
            () -> new ItemStack(ModItems.get("platinum_ingot").get()),
            (params, output) -> {
                // Add all non-block items from the central map
                ModItems.REGISTERED_ITEMS.values().forEach(item -> {
                    if (!(item.get() instanceof BlockItem)) {
                        output.accept(item.get());
                    }
                });
            }
    );

    @SuppressWarnings("unused")
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> INCLINATIONS_BUILDING_BLOCKS_TAB = registerCreativeTab(
            "inclinations_building_blocks_tab",
            () -> new ItemStack(ModBlocks.get("platinum_block").get()),
            (params, output) -> {
                // Add all block items from the central map
                ModItems.REGISTERED_ITEMS.values().forEach(item -> {
                    if (item.get() instanceof BlockItem) {
                        output.accept(item.get());
                    }
                });
            }
    );

    private static DeferredHolder<CreativeModeTab, CreativeModeTab> registerCreativeTab(String name, Supplier<ItemStack> icon, CreativeModeTab.DisplayItemsGenerator displayItems) {
        return CREATIVE_MODE_TABS.register(name, () -> CreativeModeTab.builder()
                .icon(icon)
                .title(Component.translatable("creativetab." + name))
                .displayItems(displayItems)
                .build());
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
