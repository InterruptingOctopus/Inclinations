package com.interruptingoctopus.inclinations.item;

import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.block.ModBlocks;
import com.interruptingoctopus.inclinations.item.custom.ScrewDriver;
import com.interruptingoctopus.inclinations.util.ModMetals;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Utility class for managing and registering all custom items in the Inclinations mod.
 */
public class ModItems {
    /**
     * Deferred register for items.
     */
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Inclinations.MOD_ID);

    /**
     * A map to hold all registered items by name for easy retrieval.
     */
    public static final Map<String, DeferredItem<Item>> REGISTERED_ITEMS = new HashMap<>();

    /**
     * The Screw Driver item.
     */
    public static final DeferredItem<Item> SCREW_DRIVER = registerItem("screw_driver",
            () -> new ScrewDriver(new Item.Properties()));

    static {
        // Register items for each metal defined in ModMetals
        ModMetals.METALS.keySet().forEach(ModItems::registerMetalItems);
        // The altar block item will be registered by registerBlockItems() after blocks are initialized.
    }

    /**
     * Helper method to register a single item and add it to the REGISTERED_ITEMS map.
     *
     * @param name     The name of the item.
     * @param supplier The item supplier.
     * @param <T>      The type of the item, must extend Item.
     * @return The registered item.
     */
    @SuppressWarnings("unchecked") // Safe cast because T extends Item
    private static <T extends Item> DeferredItem<T> registerItem(String name, Supplier<T> supplier) {
        DeferredItem<T> item = ITEMS.register(name, supplier);
        REGISTERED_ITEMS.put(name, (DeferredItem<Item>) item);
        return item;
    }

    /**
     * Helper method to register all standard items for a given metal (ingot, nugget, raw form).
     *
     * @param name The name of the metal.
     */
    private static void registerMetalItems(String name) {
        registerItem(name + "_ingot", () -> new Item(new Item.Properties()));
        registerItem(name + "_nugget", () -> new Item(new Item.Properties()));
        registerItem("raw_" + name, () -> new Item(new Item.Properties()));
    }

    /**
     * Registers BlockItems for all blocks in ModBlocks that don't already have a custom item registered.
     * This should be called after ModBlocks are registered and before ModItems.register().
     */
    public static void registerBlockItems() {
        ModBlocks.REGISTERED_BLOCKS.forEach((name, block) -> {
            // Only register a BlockItem if a custom item for this block hasn't been registered yet.
            if (!REGISTERED_ITEMS.containsKey(name)) {
                registerItem(name, () -> new BlockItem(block.get(), new Item.Properties()));
            }
        });
    }

    /**
     * Retrieves a registered item by its name.
     * @param name The name of the item.
     * @return The registered item, or null if not found.
     */
    public static DeferredItem<Item> get(String name) {
        return REGISTERED_ITEMS.get(name);
    }
}
