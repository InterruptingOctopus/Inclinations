package com.interruptingoctopus.inclinations.item;

import com.interruptingoctopus.inclinations.Inclinations;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Locale;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Inclinations.MOD_ID);

    // --- Item Definitions ---
    // The enum handles the registration, deriving the lowercase name from the enum constant's name.
    private enum ItemDef {
        PLATINUM_INGOT,
        PLATINUM_NUGGET,
        RAW_PLATINUM,
        SILVER_INGOT,
        SILVER_NUGGET,
        RAW_SILVER;

        private final DeferredItem<Item> holder;

        ItemDef() {
            this.holder = ITEMS.registerItem(this.name().toLowerCase(Locale.ROOT), Item::new, new Item.Properties());
        }

        public DeferredItem<Item> getHolder() {
            return holder;
        }
    }

    // --- Static Fields for easy access from other classes ---
    // These fields just point to the holders created in the enum, maintaining compatibility.
    public static final DeferredItem<Item> PLATINUM_INGOT = ItemDef.PLATINUM_INGOT.getHolder();
    public static final DeferredItem<Item> PLATINUM_NUGGET = ItemDef.PLATINUM_NUGGET.getHolder();
    public static final DeferredItem<Item> RAW_PLATINUM = ItemDef.RAW_PLATINUM.getHolder();
    public static final DeferredItem<Item> SILVER_INGOT = ItemDef.SILVER_INGOT.getHolder();
    public static final DeferredItem<Item> SILVER_NUGGET = ItemDef.SILVER_NUGGET.getHolder();
    public static final DeferredItem<Item> RAW_SILVER = ItemDef.RAW_SILVER.getHolder();
}