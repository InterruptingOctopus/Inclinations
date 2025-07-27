package com.interruptingoctopus.inclinations.item;

import com.interruptingoctopus.inclinations.Inclinations;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister
            .createItems(Inclinations.MOD_ID);

    //Platinum
    public static final DeferredItem<Item> PLATINUM_INGOT = ITEMS.registerItem( "platinum_ingot",
            Item::new, new Item.Properties());

    public static final DeferredItem<Item> PLATINUM_NUGGET = ITEMS.registerItem( "platinum_nugget",
            Item::new, new Item.Properties());

    public static final DeferredItem<Item> RAW_PLATINUM = ITEMS.registerItem( "raw_platinum",
            Item::new, new Item.Properties());

    //Silver
    public static final DeferredItem<Item> SILVER_INGOT = ITEMS.registerItem( "silver_ingot",
            Item::new, new Item.Properties());

    public static final DeferredItem<Item> SILVER_NUGGET = ITEMS.registerItem( "silver_nugget",
            Item::new, new Item.Properties());

    public static final DeferredItem<Item> RAW_SILVER = ITEMS.registerItem( "raw_silver",
            Item::new, new Item.Properties());




    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

