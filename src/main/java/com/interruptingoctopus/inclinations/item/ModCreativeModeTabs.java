package com.interruptingoctopus.inclinations.item;

import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Inclinations.MOD_ID);
    public static final Supplier<CreativeModeTab> INCLINATIONS_ITEMS_TAB = CREATIVE_MODE_TAB.register("inclinations_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.PLATINUM_INGOT.get()))
                    .title(Component.translatable("creativetab.inclinations.inclinations_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        //output.accept(ModItems.);
                        output.accept(ModItems.PLATINUM_INGOT);
                        output.accept(ModItems.PLATINUM_NUGGET);
                        output.accept(ModItems.RAW_PLATINUM);

                        output.accept(ModItems.SILVER_INGOT);
                        output.accept(ModItems.SILVER_NUGGET);
                        output.accept(ModItems.RAW_SILVER);
                    }).build());

    public static final Supplier<CreativeModeTab> INCLINATIONS_BLOCK_TAB = CREATIVE_MODE_TAB.register("inclinations_blocks_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.PLATINUM_BLOCK))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(Inclinations.MOD_ID, "inclinations_items_tab"))
                    .title(Component.translatable("creativetab.inclinations.inclinations_blocks"))
                    .displayItems((itemDisplayParameters, output) -> {
                        //output.accept(ModBlocks.);
                        output.accept(ModBlocks.PLATINUM_BLOCK);
                        output.accept(ModBlocks.PLATINUM_ORE);
                        output.accept(ModBlocks.RAW_PLATINUM_BLOCK);

                        output.accept(ModBlocks.SILVER_BLOCK);
                        output.accept(ModBlocks.SILVER_ORE);
                        output.accept(ModBlocks.RAW_SILVER_BLOCK);
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}