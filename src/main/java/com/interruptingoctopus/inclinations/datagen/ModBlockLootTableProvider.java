package com.interruptingoctopus.inclinations.datagen;

import com.interruptingoctopus.inclinations.block.ModBlocks;
import com.interruptingoctopus.inclinations.item.ModItems;
import com.interruptingoctopus.inclinations.util.ModMetals;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * Data provider for generating loot tables for mod blocks.
 */
public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    /**
     * Generates the loot tables for the mod blocks.
     */
    @Override
    protected void generate() {
        ModBlocks.REGISTERED_BLOCKS.forEach((name, deferredBlock) -> {
            Block block = deferredBlock.get();
            if (name.endsWith("_ore")) {
                String baseName = name.substring(0, name.indexOf("_ore"));
                if (ModMetals.METALS.containsKey(baseName)) {
                    this.add(block, createOreDrop(block, ModItems.get("raw_" + baseName).get()));
                } else {
                    dropSelf(block);
                }
            } else {
                dropSelf(block);
            }
        });
    }

    /**
     * Returns an iterable of all blocks known to this loot table provider.
     *
     * @return An iterable of blocks.
     */
    @Nonnull
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.REGISTERED_BLOCKS.values().stream().map(DeferredBlock::get).toList();
    }
}
