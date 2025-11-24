package com.interruptingoctopus.inclinations.datagen;

import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Inclinations.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // Altar
        simpleBlock(ModBlocks.ALTAR.get(), models().getExistingFile(modLoc("block/altar")));

        // Dynamically generate block states and models for all registered blocks
        ModBlocks.REGISTERED_BLOCKS.forEach((name, deferredBlock) -> {
            // Skip blocks that have custom block state/model generation
            // The altar is now handled above, so we don't need to skip it here.
            // if (deferredBlock.equals(ModBlocks.ALTAR)) {
            //     return; // Skip this block as it's handled above
            // }
            simpleBlock(deferredBlock.get());
        });
    }
}