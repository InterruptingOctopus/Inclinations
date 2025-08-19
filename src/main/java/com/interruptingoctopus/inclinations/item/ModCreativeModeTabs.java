package com.interruptingoctopus.inclinations.item;

import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ModCreativeModeTabs {
    // DeferredRegister for creative mode tabs
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, Inclinations.MOD_ID);

    // Define your custom creative tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> INCLINATIONS_BLOCKS_TAB = CREATIVE_MODE_TAB.register("inclinations_blocks_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + Inclinations.MOD_ID + ".inclinations_blocks")) // Set the title (requires a translation in en_us.json)
            .icon(() -> {
                DeferredHolder<Block, Block> iconItemHolder = ModBlocks.REGISTERED_BLOCKS.get("platinum_block");
                return iconItemHolder != null ? new ItemStack(iconItemHolder.get()) : ItemStack.EMPTY; // Fallback to an empty item stack if the item isn't found
            })
            .displayItems((params, output) -> {
                // Add items from your block map
                ModBlocks.REGISTERED_BLOCKS.values().forEach(blockHolder ->
                        output.accept(blockHolder.get())); // Add all block items
            })
            .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> INCLINATIONS_ITEMS_TAB = CREATIVE_MODE_TAB.register("inclinations_items_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + Inclinations.MOD_ID + ".inclinations_blocks")) // Set the title (requires a translation in en_us.json)
            .icon(() -> {
                DeferredHolder<Item, Item> iconItemHolder = ModItems.REGISTERED_CUSTOM_ITEMS.get("platinum_ingot");
                return iconItemHolder != null ? new ItemStack(iconItemHolder.get()) : ItemStack.EMPTY; // Fallback to an empty item stack if the item isn't found
            })
            .displayItems((params, output) -> {
                // Add items from your custom item map
                ModItems.REGISTERED_CUSTOM_ITEMS.values().forEach(itemHolder ->
                        {
                            if (!(itemHolder.get() instanceof BlockItem)) {
                                output.accept(itemHolder.get());
                            }
                        });
                        // Add all custom items

            })
            .build());


//    public static final Supplier<CreativeModeTab> INCLINATIONS_ITEMS_TAB = CREATIVE_MODE_TAB.register("inclinations_items_tab",
//            () -> CreativeModeTab.builder()
//                    .icon(() -> new ItemStack(ModItems.PLATINUM_INGOT.get()))
//                    .title(Component.translatable("creativetab.inclinations.inclinations_items"))
//                    .displayItems(
//                            (itemDisplayParameters, output) ->
//                                    ModItems.ITEMS.getEntries().forEach(deferredItem ->
//                                            {
//                                                Item item = deferredItem.get();
//                                                if (!(item instanceof BlockItem)) {
//                                                    output.accept(item);
//                                                }
//                                            }
//                                    )
//                    )
//                    .build()
//    );
//
//    public static final Supplier<CreativeModeTab> INCLINATIONS_BLOCKS_TAB = CREATIVE_MODE_TAB.register("inclinations_blocks_tab",
//            () -> CreativeModeTab.builder()
//                    .icon(() -> new ItemStack(ModBlocks.REGISTERED_BLOCKS.get("platinum_block")))
//                    .title(Component.translatable("creativetab.inclinations.inclinations_blocks"))
//                    .displayItems(
//                            (itemDisplayParameters, output) ->
//                                    ModBlocks.BLOCKS.getEntries().forEach(deferredBlock ->
//                                            output.accept(deferredBlock.get()
//                                            )
//                                    )
//                    )
//                    .build()
//    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);

    }
}
