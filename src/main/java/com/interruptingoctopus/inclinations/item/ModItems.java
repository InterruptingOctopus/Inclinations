package com.interruptingoctopus.inclinations.item;

import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.block.ModBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Inclinations.MOD_ID);

    // Map to store custom item names and their properties
    private static final Map<String, Item.Properties> CUSTOM_ITEM_PROPERTIES = new HashMap<>();
    
    static {
        // Initialize the map with custom item names and properties
        CUSTOM_ITEM_PROPERTIES.put("platinum_ingot", new Item.Properties());
        CUSTOM_ITEM_PROPERTIES.put("platinum_nugget", new Item.Properties());
        CUSTOM_ITEM_PROPERTIES.put("raw_platinum", new Item.Properties());
        CUSTOM_ITEM_PROPERTIES.put("silver_ingot", new Item.Properties());
        CUSTOM_ITEM_PROPERTIES.put("silver_nugget", new Item.Properties());
        CUSTOM_ITEM_PROPERTIES.put("raw_silver", new Item.Properties());
    }

    // Map to hold the registered DeferredHolder objects for custom items
    public static final Map<String, DeferredHolder<Item, Item>> REGISTERED_CUSTOM_ITEMS = new HashMap<>();

    // Map to hold the registered DeferredHolder objects for BlockItems
    public static final Map<String, DeferredHolder<Item, Item>> REGISTERED_BLOCK_ITEMS = new HashMap<>();



    public static void registerItems() {
        CUSTOM_ITEM_PROPERTIES.forEach((name, properties) -> {
            DeferredHolder<Item, Item> registeredItem = ITEMS.register(name, () -> new Item(properties));
            REGISTERED_CUSTOM_ITEMS.put(name, registeredItem);
        });
    }

    public static void registerBlockItems() {
        ModBlocks.REGISTERED_BLOCKS.forEach((name, blockDeferredHolder) -> {
            DeferredHolder<Item, Item> registeredBlockItem = ITEMS.register(name, () -> new BlockItem(blockDeferredHolder.get(), new Item.Properties()));
            REGISTERED_BLOCK_ITEMS.put(name, registeredBlockItem);
        });
    }
//
//    //Platinum
//    public static final DeferredItem<Item> PLATINUM_INGOT = ITEMS.registerItem( "platinum_ingot",
//            Item::new, new Item.Properties());
//
//    public static final DeferredItem<Item> PLATINUM_NUGGET = ITEMS.registerItem( "platinum_nugget",
//            Item::new, new Item.Properties());
//
//    public static final DeferredItem<Item> RAW_PLATINUM = ITEMS.registerItem( "raw_platinum",
//            Item::new, new Item.Properties());
//
//    //Silver
//    public static final DeferredItem<Item> SILVER_INGOT = ITEMS.registerItem( "silver_ingot",
//            Item::new, new Item.Properties());
//
//    public static final DeferredItem<Item> SILVER_NUGGET = ITEMS.registerItem( "silver_nugget",
//            Item::new, new Item.Properties());
//
//    public static final DeferredItem<Item> RAW_SILVER = ITEMS.registerItem( "raw_silver",
//            Item::new, new Item.Properties());
//    public static void register(IEventBus eventBus) {
//        ITEMS.register(eventBus);
//    }
}

