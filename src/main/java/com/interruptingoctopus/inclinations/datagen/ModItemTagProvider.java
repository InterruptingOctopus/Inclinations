package com.interruptingoctopus.inclinations.datagen;

import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.data.tags.TagsProvider;

import javax.annotation.Nonnull;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput p_275343_, CompletableFuture<Provider> p_275729_, CompletableFuture<TagsProvider.TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, Inclinations.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(@Nonnull Provider pProvider) {
        // Add all metal ingots to the forge:ingots tag
        this.tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge", "ingots")))
                .add(ModItems.get("platinum_ingot").get())
                .add(ModItems.get("silver_ingot").get());

        // Add specific metal ingots to their own tags
        this.tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge", "ingots/platinum")))
                .add(ModItems.get("platinum_ingot").get());
        this.tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge", "ingots/silver")))
                .add(ModItems.get("silver_ingot").get());
    }
}
