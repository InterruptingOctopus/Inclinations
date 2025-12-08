package com.interruptingoctopus.inclinations.block;

import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.util.ModMetals;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Utility class for managing and registering all custom blocks in the Inclinations mod.
 */
public class ModBlocks {
    /**
     * Deferred register for blocks.
     */
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Inclinations.MOD_ID);

    /**
     * A map to hold all registered blocks by name for easy retrieval.
     */
    public static final Map<String, DeferredBlock<Block>> REGISTERED_BLOCKS = new HashMap<>();

    /**
     * The Altar block.
     */
    public static final DeferredBlock<Block> ALTAR = registerBlock("altar",
            () -> new AltarBlock(BlockBehaviour.Properties.of().strength(4.0F).sound(SoundType.WOOD)));

    static {
        // Register blocks for each metal defined in ModMetals
        ModMetals.METALS.forEach((name, metal) -> registerMetalBlocks(name, metal.properties()));
    }


    /**
     * Helper method to register a single block and add it to the REGISTERED_BLOCKS map.
     *
     * @param name          The name of the block.
     * @param blockSupplier The block supplier.
     * @param <T>           The type of the block, must extend Block.
     * @return The registered block.
     */
    @SuppressWarnings("unchecked")
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> blockSupplier) {
        DeferredBlock<T> block = BLOCKS.register(name, blockSupplier);
        REGISTERED_BLOCKS.put(name, (DeferredBlock<Block>) block);
        return block;
    }

    /**
     * Helper to register all standard blocks for a metal (block, ore, raw block).
     *
     * @param name       The name of the metal.
     * @param properties The properties of the metal.
     */
    private static void registerMetalBlocks(String name, ModMetals.MetalProperties properties) {
        // Construct BlockBehaviour.Properties inside the lambda using raw values
        registerBlock(name + "_block", () -> new Block(BlockBehaviour.Properties.of()
                .strength(properties.blockStrength(), properties.blockExplosionResistance())
                .sound(properties.blockSoundType())
                .requiresCorrectToolForDrops()));

        registerBlock(name + "_ore", () -> new Block(BlockBehaviour.Properties.of()
                .strength(properties.oreStrength(), properties.oreExplosionResistance())
                .sound(properties.oreSoundType())
                .requiresCorrectToolForDrops()));

        registerBlock("raw_" + name + "_block", () -> new Block(BlockBehaviour.Properties.of()
                .strength(properties.blockStrength(), properties.blockExplosionResistance())
                .sound(properties.blockSoundType())
                .requiresCorrectToolForDrops()));
    }

    /**
     * Retrieves a registered block by its name.
     * @param name The name of the block.
     * @return The registered block, or null if not found.
     */
    public static DeferredBlock<Block> get(String name) {

        return REGISTERED_BLOCKS.get(name);
    }

    /**
     * Registers the deferred register for blocks with the event bus.
     * This method should be called during mod construction.
     * @param eventBus The event bus.
     */
    public static void register(IEventBus eventBus) {

        BLOCKS.register(eventBus);
    }
}
