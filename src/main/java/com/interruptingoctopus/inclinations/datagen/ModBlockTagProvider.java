package com.interruptingoctopus.inclinations.datagen;

import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.block.ModBlocks;
import com.interruptingoctopus.inclinations.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block; // Re-added import for Block
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Inclinations.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(@Nonnull HolderLookup.Provider pProvider) {
        // All our custom blocks should be mineable with a pickaxe
        // Dynamically add all registered blocks to the MINEABLE_WITH_PICKAXE tag
        ModBlocks.REGISTERED_BLOCKS.forEach((name, deferredBlock) -> tag(BlockTags.MINEABLE_WITH_PICKAXE).add(deferredBlock.get()));

        // Define tool requirements for our metal blocks and ores dynamically
        ModItems.METALS.forEach(metalName -> {
            Block metalBlock = ModBlocks.get(metalName + "_block").get();
            Block metalOre = ModBlocks.get(metalName + "_ore").get();
            Block rawMetalBlock = ModBlocks.get("raw_" + metalName + "_block").get();

            if (metalName.equals("silver")) {
                // Silver blocks and ores require an iron pickaxe
                tag(BlockTags.NEEDS_IRON_TOOL)
                        .add(metalBlock)
                        .add(metalOre)
                        .add(rawMetalBlock);
            } else if (metalName.equals("platinum")) {
                // Platinum blocks and ores require a diamond pickaxe
                tag(BlockTags.NEEDS_DIAMOND_TOOL)
                        .add(metalBlock)
                        .add(metalOre)
                        .add(rawMetalBlock);
            }
        });
    }
}