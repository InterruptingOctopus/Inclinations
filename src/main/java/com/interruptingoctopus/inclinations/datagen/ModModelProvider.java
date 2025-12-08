package com.interruptingoctopus.inclinations.datagen;

import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.block.ModBlocks;
import com.interruptingoctopus.inclinations.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

/**
 * Combines block state and item model generation for the mod.
 */
public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, Inclinations.MOD_ID);
    }

    @Override
    protected void registerModels(@Nonnull BlockModelGenerators blockModels, @Nonnull ItemModelGenerators itemModels) {
        // Handle specific blocks first, like ALTAR
        // Changed to createTrivialCube to resolve 'models' and 'modLoc' errors for now.
        // If ALTAR needs a custom model, a more advanced method would be required.
        blockModels.createTrivialCube(ModBlocks.ALTAR.get());

        // Iterate over all registered blocks from the DeferredRegister
        ModBlocks.BLOCKS.getEntries().forEach(blockHolder -> {
            Block block = blockHolder.value();
            // Skip ALTAR as it's handled specifically above
            if (block.equals(ModBlocks.ALTAR.get())) {
                return;
            }
            // For now, all other blocks are simple cubes.
            // These methods automatically generate the item model for the corresponding BlockItem.
            blockModels.createTrivialCube(block);
            // If you have other block types (stairs, slabs, etc.), you'd add specific calls here:
            // blockModels.createStairs((CustomStairsBlock)block);
            // blockModels.createSlab((CustomSlabBlock)block);
            // ...
        });

        // Iterate over all registered items from the DeferredRegister
        ModItems.ITEMS.getEntries().forEach(itemHolder -> {
            Item item = itemHolder.value();

            if (item.equals(ModItems.SCREW_DRIVER.get())) {
                itemModels.generateFlatItem(item, ModelTemplates.FLAT_HANDHELD_ITEM);
            }
            // For BlockItems, their models are implicitly handled by the blockModels.createTrivialBlock/createTrivialCube calls.
            // We only need to explicitly generate models for non-BlockItems here.
            else if (!(item instanceof BlockItem)) {
                itemModels.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
            }
        });
    }

    @Nonnull
    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream(); // Removed .map(Holder::value)
    }

    @Nonnull
    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return ModItems.ITEMS.getEntries().stream(); // Removed .map(Holder::value)
    }
}
