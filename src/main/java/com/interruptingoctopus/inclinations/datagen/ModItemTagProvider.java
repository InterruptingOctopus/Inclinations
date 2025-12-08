package com.interruptingoctopus.inclinations.datagen;

import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.item.ModItems;
import com.interruptingoctopus.inclinations.util.ModMetals;
import com.interruptingoctopus.inclinations.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

/**
 * Data provider for generating item tags for the mod.
 */
public class ModItemTagProvider extends ItemTagsProvider {

    /**
     * Constructor for ModItemTagProvider.
     *
     * @param pOutput     The pack output.
     * @param pRegistries The lookup provider for registries.
     */
    public ModItemTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries, Inclinations.MOD_ID);
    }

    /**
     * Adds all item tags for the mod.
     *
     * @param pProvider The lookup provider for registries.
     */
    @Override
    protected void addTags(@Nonnull HolderLookup.Provider pProvider) {
        // Dynamically add tags for all metals
        ModMetals.METALS.keySet().forEach(metalName -> {
            // Add all metal ingots to the forge:ingots tag
            this.tag(ModTags.Items.FORGE_INGOTS)
                    .add(ModItems.get(metalName + "_ingot").get());

            // Add specific metal ingots to their own tags
            this.tag(ModTags.Items.forgeIngotTag(metalName))
                    .add(ModItems.get(metalName + "_ingot").get());

            // Add raw metals to forge:raw_materials tag
            this.tag(ModTags.Items.FORGE_RAW_MATERIALS)
                    .add(ModItems.get("raw_" + metalName).get());

            // Add specific raw metals to their own tags
            this.tag(ModTags.Items.forgeRawMaterialTag(metalName))
                    .add(ModItems.get("raw_" + metalName).get());
        });
    }
}
