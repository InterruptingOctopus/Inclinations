package com.interruptingoctopus.inclinations.datagen;

import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.block.ModBlocks;
import com.interruptingoctopus.inclinations.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Inclinations.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // The altar item model is handled manually in src/main/resources.

        // Dynamically generate item models for all remaining registered items.
        ModItems.REGISTERED_ITEMS.forEach((name, deferredItem) -> {
            // Skip automatic item model generation for items that have manual models.
            // "lightning_rod" is no longer being skipped as it's not registered and not needed.
            if (name.equals("altar")) { // Only skip "altar" now
                return; // Skip this item
            }

            if (deferredItem.get().equals(ModItems.SCREW_DRIVER.get())) {
                // Use handheld model for the screwdriver
                handheldItem(deferredItem.get());
            } else if (deferredItem.get() instanceof BlockItem) {
                // It's a block item, so create a model that points to the block model
                withExistingParent(name, modLoc("block/" + name));
            } else {
                // It's a regular item, so generate a basic 'generated' model
                basicItem(deferredItem.get());
            }
        });
    }
}
