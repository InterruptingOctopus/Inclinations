package com.interruptingoctopus.inclinations.datagen;

import com.interruptingoctopus.inclinations.block.ModBlocks;
import com.interruptingoctopus.inclinations.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;

import javax.annotation.Nonnull; // Added for @Nonnull
import java.util.Collections;
// Removed unused import: import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    public ModBlockLootTableProvider(HolderLookup.Provider pRegistries) {
        super(Collections.emptySet(), // pKnownItems (we don't have custom items that need loot tables here)
              FeatureFlags.REGISTRY.allFlags(), // pFeatureFlags
              pRegistries); // pRegistries
    }

    @Override
    protected void generate() {
        ModBlocks.REGISTERED_BLOCKS.forEach((name, deferredBlock) -> {
            Block block = deferredBlock.get();
            if (name.endsWith("_ore")) {
                String baseName = name.substring(0, name.indexOf("_ore"));
                if (ModItems.METALS.contains(baseName)) {
                    // Metal ores drop their raw item form with fortune bonus
                    this.add(block, createOreDrop(block, ModItems.get("raw_" + baseName).get()));
                } else {
                    // Fallback for unknown ores, drop themselves
                    dropSelf(block);
                }
            } else {
                // All other blocks (metal blocks, raw metal blocks) drop themselves
                dropSelf(block);
            }
        });
    }

    @Nonnull // Added @Nonnull annotation
    @Override
    protected Iterable<Block> getKnownBlocks() {
        // This method is used by the superclass to validate that all known blocks have loot tables.
        return ModBlocks.REGISTERED_BLOCKS.values().stream().map(DeferredBlock::get).toList();
    }
}
