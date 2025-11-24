package com.interruptingoctopus.inclinations.block;

import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.item.ModItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Inclinations.MOD_ID);

    // A map to hold all our registered blocks by name
    public static final Map<String, DeferredBlock<Block>> REGISTERED_BLOCKS = new HashMap<>();

    // --- Block Properties Helpers ---
    private static BlockBehaviour.Properties createMetalBlockProperties() {
        return BlockBehaviour.Properties.of()
                .strength(4f)
                .requiresCorrectToolForDrops()
                .sound(SoundType.METAL);
    }

    private static BlockBehaviour.Properties createOreProperties() {
        return BlockBehaviour.Properties.of()
                .strength(4f)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE);
    }

    private static BlockBehaviour.Properties createAltarProperties() {
        return BlockBehaviour.Properties.of()
                .strength(2.0f)
                .noOcclusion() // For custom models that aren't full cubes
                .sound(SoundType.STONE);
    }

    // Helper to register a single block and add it to the map for automatic item registration
    private static void registerBlock(String name, Supplier<Block> blockSupplier) {
        DeferredBlock<Block> block = BLOCKS.register(name, blockSupplier);
        REGISTERED_BLOCKS.put(name, block);
    }

    // Helper to register a block without adding it to the automatic item registration map
    private static <T extends Block> DeferredBlock<T> register(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    // --- Custom Blocks ---
    // Register the altar block without adding it to the map for automatic item registration
    public static final DeferredBlock<Block> ALTAR = register("altar", () -> new AltarBlock(createAltarProperties()));


    // Static initializer block to run the registration for all metals and gems
    static {
        ModItems.METALS.forEach(ModBlocks::registerMetalBlocks); // Reuse METALS list from ModItems
    }

    // Helper to register all standard blocks for a metal
    private static void registerMetalBlocks(String name) {
        registerBlock(name + "_block", () -> new Block(createMetalBlockProperties()));
        registerBlock(name + "_ore", () -> new Block(createOreProperties()));
        registerBlock("raw_" + name + "_block", () -> new Block(createMetalBlockProperties()));
    }

    // Method to retrieve a block by its registered name
    public static DeferredBlock<Block> get(String name) {
        return REGISTERED_BLOCKS.get(name);
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
