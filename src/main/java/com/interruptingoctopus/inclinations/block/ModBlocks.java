package com.interruptingoctopus.inclinations.block;


import com.interruptingoctopus.inclinations.Inclinations;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;

public class ModBlocks {
    // Define your DeferredRegister for blocks in your main mod class or a dedicated registry class
    public static final DeferredRegister.Blocks BLOCKS =  DeferredRegister.createBlocks(Inclinations.MOD_ID);

    private static final Map<String, BlockBehaviour.Properties> BLOCK_PROPERTIES = new HashMap<>();

    static {
        // Initialize the map with block names and properties
        BLOCK_PROPERTIES.put("platinum_block",
                BlockBehaviour.Properties.of()
                        .strength(5.0f, 6.0f)
                        .sound(SoundType.METAL)
                        .requiresCorrectToolForDrops());
        BLOCK_PROPERTIES.put("raw_platinum_block",
                BlockBehaviour.Properties.of()
                        .strength(5.0f, 6.0f)
                        .sound(SoundType.METAL)
                        .requiresCorrectToolForDrops());
        BLOCK_PROPERTIES.put("silver_block",
                BlockBehaviour.Properties.of()
                        .strength(5.0f, 6.0f)
                        .sound(SoundType.METAL)
                        .requiresCorrectToolForDrops());
        BLOCK_PROPERTIES.put("raw_silver_block",
                BlockBehaviour.Properties.of()
                        .strength(5.0f, 6.0f)
                        .sound(SoundType.METAL)
                        .requiresCorrectToolForDrops());

        BLOCK_PROPERTIES.put("platinum_ore",
                BlockBehaviour.Properties.of()
                        .strength(5.0f, 6.0f)
                        .sound(SoundType.STONE)
                        .requiresCorrectToolForDrops());
        BLOCK_PROPERTIES.put("silver_ore",
                BlockBehaviour.Properties.of()
                        .strength(5.0f, 6.0f)
                        .sound(SoundType.STONE)
                        .requiresCorrectToolForDrops());
    }

    //Map to hold the registered DeferredBlock Objects
    public static final Map<String, DeferredHolder<Block, Block>> REGISTERED_BLOCKS = new HashMap<>();

    // Map specifically for ore blocks
    public static final Map<String, DeferredHolder<Block, Block>> ORE_BLOCKS = new HashMap<>();

    // Method to register all blocks using a forEach loop
    public static void registerBlocks() {
        BLOCK_PROPERTIES.forEach((name, properties) -> {
            DeferredHolder<Block, Block> registeredBlock = BLOCKS.register(name, () -> new Block(properties));
            REGISTERED_BLOCKS.put(name, registeredBlock); // Store the registered block

            // Determine if it's an ore and add to ORE_BLOCKS map
            // You can refine this check (e.g., using a tag, a specific base class for ores, etc.)
            if (name.endsWith("_ore")) {
                ORE_BLOCKS.put(name, registeredBlock);
            }
        });
    }


    // In your main mod class constructor:
//    public MyMod(IEventBus modEventBus) {
//        BLOCKS.register(modEventBus);
//        // ... other registrations
//        registerBlocks(); // Call the registration method
//    }


//    public static final DeferredBlock<Block> PLATINUM_BLOCK = registerBlock( "platinum_block",
//            (properties) -> new Block(properties
//                    .strength(4f)
//                    .requiresCorrectToolForDrops()
//                    .sound(SoundType.METAL)
//            )
//    );
//    public static final DeferredBlock<Block> PLATINUM_ORE = registerBlock( "platinum_ore",
//            (properties) -> new Block(properties
//                    .strength(4f)
//                    .requiresCorrectToolForDrops()
//                    .sound(SoundType.STONE)
//            )
//    );
//    public static final DeferredBlock<Block> RAW_PLATINUM_BLOCK = registerBlock( "raw_platinum_block",
//            (properties) -> new Block(properties
//                    .strength(4f)
//                    .requiresCorrectToolForDrops()
//                    .sound(SoundType.METAL)
//            )
//    );
//
//    public static final DeferredBlock<Block> SILVER_BLOCK = registerBlock( "silver_block",
//            (properties) -> new Block(properties
//                    .strength(4f)
//                    .requiresCorrectToolForDrops()
//                    .sound(SoundType.METAL)
//            )
//    );
//    public static final DeferredBlock<Block> SILVER_ORE = registerBlock( "silver_ore",
//            (properties) -> new Block(properties
//                    .strength(4f)
//                    .requiresCorrectToolForDrops()
//                    .sound(SoundType.STONE)
//            )
//    );
//    public static final DeferredBlock<Block> RAW_SILVER_BLOCK = registerBlock( "raw_silver_block",
//            (properties) -> new Block(properties
//                    .strength(4f)
//                    .requiresCorrectToolForDrops()
//                    .sound(SoundType.METAL)
//            )
//    );
//
//    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function) {
//        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
//        registerBlockItem(name, toReturn);
//        return toReturn;
//    }
//
//    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
//        ModItems.ITEMS.registerItem(name, (properties) -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
//    }
//
//    public static void register(IEventBus eventBus) {
//        BLOCKS.register(eventBus);
//    }
}