package com.interruptingoctopus.inclinations.block;

import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Locale;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Inclinations.MOD_ID);

    // --- Block Properties ---
    private static final BlockBehaviour.Properties METAL_BLOCK_PROPERTIES = BlockBehaviour.Properties.of()
            .strength(4f).requiresCorrectToolForDrops().sound(SoundType.METAL);
    private static final BlockBehaviour.Properties ORE_PROPERTIES = BlockBehaviour.Properties.of()
            .strength(4f).requiresCorrectToolForDrops().sound(SoundType.STONE);

    // --- Block Definitions ---
    private enum BlockDef {
        PLATINUM_BLOCK(() -> new Block(METAL_BLOCK_PROPERTIES)),
        PLATINUM_ORE(() -> new Block(ORE_PROPERTIES)),
        RAW_PLATINUM_BLOCK(() -> new Block(METAL_BLOCK_PROPERTIES)),
        SILVER_BLOCK(() -> new Block(METAL_BLOCK_PROPERTIES)),
        SILVER_ORE(() -> new Block(ORE_PROPERTIES)),
        RAW_SILVER_BLOCK(() -> new Block(METAL_BLOCK_PROPERTIES));

        private final DeferredBlock<Block> holder;

        BlockDef(Supplier<Block> blockSupplier) {
            String name = this.name().toLowerCase(Locale.ROOT);
            this.holder = BLOCKS.register(name, blockSupplier);
            registerBlockItem(name, this.holder);
        }

        private static void registerBlockItem(String name, DeferredBlock<Block> blockHolder) {
            ModItems.ITEMS.registerItem(name, (properties) -> new BlockItem(blockHolder.get(), properties.useBlockDescriptionPrefix()));
        }

        public DeferredBlock<Block> getHolder() {
            return holder;
        }
    }

    // --- Static Fields for easy access ---
    public static final DeferredBlock<Block> PLATINUM_BLOCK = BlockDef.PLATINUM_BLOCK.getHolder();
    public static final DeferredBlock<Block> PLATINUM_ORE = BlockDef.PLATINUM_ORE.getHolder();
    public static final DeferredBlock<Block> RAW_PLATINUM_BLOCK = BlockDef.RAW_PLATINUM_BLOCK.getHolder();
    public static final DeferredBlock<Block> SILVER_BLOCK = BlockDef.SILVER_BLOCK.getHolder();
    public static final DeferredBlock<Block> SILVER_ORE = BlockDef.SILVER_ORE.getHolder();
    public static final DeferredBlock<Block> RAW_SILVER_BLOCK = BlockDef.RAW_SILVER_BLOCK.getHolder();
}