package com.interruptingoctopus.inclinations.item;

import com.interruptingoctopus.inclinations.Inclinations;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Inclinations.MOD_ID);

    //public static final DeferredItem<Item> NEW_ITEM = ITEMS.register("new_item",
    //            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> COPPER_NUGGET = ITEMS.register("copper_nugget",
                () -> new Item(new Item.Properties()));

    //platinum
    public static final DeferredItem<Item> PLATINUM_INGOT = ITEMS.register("platinum_ingot",
                () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PLATINUM_NUGGET = ITEMS.register("platinum_nugget",
               () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_PLATINUM = ITEMS.register("raw_platinum",
            () -> new Item(new Item.Properties()));

    //silver
    public static final DeferredItem<Item> SILVER_INGOT = ITEMS.register("silver_ingot",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SILVER_NUGGET = ITEMS.register("silver_nugget",
               () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_SILVER = ITEMS.register("raw_silver",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

