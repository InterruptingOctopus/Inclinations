package com.interruptingoctopus.inclinations.item;

import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Inclinations.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> INCLINATIONS_INGREDIENTS_TAB = CREATIVE_MODE_TABS.register("inclinations_ingredients_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.PLATINUM_INGOT.get()))
                    .title(Component.translatable("creativetab.inclinations_ingredients_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        // Add Items
                        output.accept(ModItems.PLATINUM_INGOT.get());
                        output.accept(ModItems.PLATINUM_NUGGET.get());
                        output.accept(ModItems.RAW_PLATINUM.get());
                        output.accept(ModItems.SILVER_INGOT.get());
                        output.accept(ModItems.SILVER_NUGGET.get());
                        output.accept(ModItems.RAW_SILVER.get());
                    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> INCLINATIONS_BUILDING_BLOCKS_TAB = CREATIVE_MODE_TABS.register("inclinations_building_blocks_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.PLATINUM_BLOCK.get()))
                    .title(Component.translatable("creativetab.inclinations_building_blocks_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        // Add Blocks
                        output.accept(ModBlocks.PLATINUM_BLOCK.get());
                        output.accept(ModBlocks.PLATINUM_ORE.get());
                        output.accept(ModBlocks.RAW_PLATINUM_BLOCK.get());
                        output.accept(ModBlocks.SILVER_BLOCK.get());
                        output.accept(ModBlocks.SILVER_ORE.get());
                        output.accept(ModBlocks.RAW_SILVER_BLOCK.get());
                    }).build());
}