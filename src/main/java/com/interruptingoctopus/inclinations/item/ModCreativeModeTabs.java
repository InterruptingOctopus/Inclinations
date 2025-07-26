package com.interruptingoctopus.inclinations.item;

import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Inclinations.MOD_ID);

    public static final Supplier<CreativeModeTab> INCLINATIONS_ITEMS_TAB = CREATIVE_MODE_TAB.register("inclinations_items_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.PLATINUM_INGOT.get()))
                    .title(Component.translatable("creativetab.inclinations.inclinations_items"))
                    .displayItems(
                            (itemDisplayParameters, output) -> ModItems.ITEMS.getEntries().forEach(deferredItem -> output.accept(deferredItem.get())
                            )
                            ).build()
    );

    public static final Supplier<CreativeModeTab> INCLINATIONS_BLOCKS_TAB = CREATIVE_MODE_TAB.register("inclinations_blocks_tab",
            () -> CreativeModeTab.builder()
                        .icon(() -> new ItemStack(ModBlocks.PLATINUM_BLOCK.get()))
                        .title(Component.translatable("creativetab.inclinations.inclinations_items"))
                        .displayItems(
                            (itemDisplayParameters, output) -> ModBlocks.BLOCKS.getEntries().forEach(deferredBlock -> output.accept(deferredBlock.get())
                            )
                            ).build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);

    }
}
