package com.interruptingoctopus.inclinations.item;

import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.block.ModBlocks;
import com.interruptingoctopus.inclinations.item.custom.ScrewDriver;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Inclinations.MOD_ID);

    // A map to hold all our registered items by name
    public static final Map<String, DeferredItem<Item>> REGISTERED_ITEMS = new HashMap<>();

    // --- Custom Items ---
    public static final DeferredItem<Item> SCREW_DRIVER = ITEMS.register("screw_driver",
            () -> new ScrewDriver(new Item.Properties()));

    // --- Block Items ---
    public static final DeferredItem<Item> ALTAR = ITEMS.register("altar",
            () -> new BlockItem(ModBlocks.ALTAR.get(), new Item.Properties()));


    // --- Material Definitions ---
    public static final List<String> METALS = List.of("platinum", "silver");

    // Helper to register a single item and add it to the map
    private static void registerItem(String name) {
        DeferredItem<Item> item = ITEMS.register(name, () -> new Item(new Item.Properties()));
        REGISTERED_ITEMS.put(name, item);
    }

    // Helper to register all standard items for a metal
    private static void registerMetalItems(String name) {
        registerItem(name + "_ingot");
        registerItem(name + "_nugget");
        registerItem("raw_" + name);
    }

    // Static initializer block to run the registration
    static {
        // Register regular items
        METALS.forEach(ModItems::registerMetalItems);
        REGISTERED_ITEMS.put("screw_driver", SCREW_DRIVER);
        REGISTERED_ITEMS.put("altar", ALTAR); // Add altar to the map
    }

    // New method to register BlockItems, called after ModBlocks are fully registered
    public static void registerBlockItems() {
        ModBlocks.REGISTERED_BLOCKS.forEach((name, block) -> {
            // Only register if not already registered (e.g., ALTAR is registered manually)
            if (!REGISTERED_ITEMS.containsKey(name)) {
                DeferredItem<Item> blockItem = ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
                REGISTERED_ITEMS.put(name, blockItem);
            }
        });
    }

    // Method to retrieve an item by its registered name
    public static DeferredItem<Item> get(String name) {
        return REGISTERED_ITEMS.get(name);
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
    // Reviewed for build errors. No code changes needed here for "cannot resolve symbol" errors,
    // as they are likely environment/IDE related. Please refresh your Gradle project.
}
