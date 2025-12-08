package com.interruptingoctopus.inclinations.datagen;

import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.block.ModBlocks;
import com.interruptingoctopus.inclinations.util.ModMetals;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

/**
 * Data provider for generating block tags for the mod.
 */
public class ModBlockTagProvider extends BlockTagsProvider {
    /**
     * Constructor for ModBlockTagProvider.
     *
     * @param output         The pack output.
     * @param lookupProvider The lookup provider for registries.
     */
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) { // Removed existingFileHelper
        super(output, lookupProvider, Inclinations.MOD_ID); // Removed existingFileHelper
    }

    /**
     * Adds all block tags for the mod.
     * @param pProvider The lookup provider for registries.
     */
    @Override
    protected void addTags(@Nonnull HolderLookup.Provider pProvider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.ALTAR.get());

        ModMetals.METALS.keySet().forEach(metalName -> {
            Block metalBlock = ModBlocks.get(metalName + "_block").get();
            Block metalOre = ModBlocks.get(metalName + "_ore").get();
            Block rawMetalBlock = ModBlocks.get("raw_" + metalName + "_block").get();

            this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                    .add(metalBlock)
                    .add(metalOre)
                    .add(rawMetalBlock);

            if (metalName.equals("silver")) {
                this.tag(BlockTags.NEEDS_IRON_TOOL)
                        .add(metalBlock)
                        .add(metalOre)
                        .add(rawMetalBlock);
            } else if (metalName.equals("platinum")) {
                this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                        .add(metalBlock)
                        .add(metalOre)
                        .add(rawMetalBlock);
            }
        });
    }
}
