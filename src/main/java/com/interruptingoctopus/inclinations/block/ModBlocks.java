package com.interruptingoctopus.inclinations.block;


import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Inclinations.MOD_ID);

    public static final DeferredBlock<Block> PLATINUM_BLOCK = registerBlock( "platinum_block",
            (properties) -> new Block(properties
                    .strength(4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL)
            )
    );
    public static final DeferredBlock<Block> PLATINUM_ORE = registerBlock( "platinum_ore",
            (properties) -> new Block(properties
                    .strength(4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
            )
    );
    public static final DeferredBlock<Block> RAW_PLATINUM_BLOCK = registerBlock( "raw_platinum_block",
            (properties) -> new Block(properties
                    .strength(4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL)
            )
    );

    public static final DeferredBlock<Block> SILVER_BLOCK = registerBlock( "silver_block",
            (properties) -> new Block(properties
                    .strength(4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL)
            )
    );
    public static final DeferredBlock<Block> SILVER_ORE = registerBlock( "silver_ore",
            (properties) -> new Block(properties
                    .strength(4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
            )
    );
    public static final DeferredBlock<Block> RAW_SILVER_BLOCK = registerBlock( "raw_silver_block",
            (properties) -> new Block(properties
                    .strength(4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL)
            )
    );

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function) {
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.registerItem(name, (properties) -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}