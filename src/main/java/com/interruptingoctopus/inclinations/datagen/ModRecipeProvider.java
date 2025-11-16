package com.interruptingoctopus.inclinations.datagen;

import com.interruptingoctopus.inclinations.block.ModBlocks;
import com.interruptingoctopus.inclinations.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.DataProvider; // Ensure this is imported
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider {

    // Explicitly make Runner implement DataProvider as the compiler demands
    public static class Runner extends RecipeProvider implements DataProvider {
        private final List<ItemLike> PLATINUM_SMELTABLES = List.of(ModItems.RAW_PLATINUM.get(), ModBlocks.PLATINUM_ORE.get());
        private final List<ItemLike> SILVER_SMELTABLES = List.of(ModItems.RAW_SILVER.get(), ModBlocks.SILVER_ORE.get());

        public Runner(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider) {
            super(pOutput, pLookupProvider);
        }

        @Override
        protected void buildRecipes(RecipeOutput pRecipeOutput) {
            // --- Smelting and Blasting ---
            oreSmelting(pRecipeOutput, PLATINUM_SMELTABLES, RecipeCategory.MISC, ModItems.PLATINUM_INGOT.get(), 0.7f, 200, "platinum");
            oreBlasting(pRecipeOutput, PLATINUM_SMELTABLES, RecipeCategory.MISC, ModItems.PLATINUM_INGOT.get(), 0.7f, 100, "platinum");

            oreSmelting(pRecipeOutput, SILVER_SMELTABLES, RecipeCategory.MISC, ModItems.SILVER_INGOT.get(), 0.7f, 200, "silver");
            oreBlasting(pRecipeOutput, SILVER_SMELTABLES, RecipeCategory.MISC, ModItems.SILVER_INGOT.get(), 0.7f, 100, "silver");

            // --- Compression / Decompression Recipes (3x3) ---
            nineBlockStorageRecipes(pRecipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.PLATINUM_BLOCK.get(), RecipeCategory.MISC, ModItems.PLATINUM_INGOT.get());
            nineBlockStorageRecipes(pRecipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.RAW_PLATINUM_BLOCK.get(), RecipeCategory.MISC, ModItems.RAW_PLATINUM.get());
            nineBlockStorageRecipes(pRecipeOutput, RecipeCategory.MISC, ModItems.PLATINUM_INGOT.get(), RecipeCategory.MISC, ModItems.PLATINUM_NUGGET.get());

            nineBlockStorageRecipes(pRecipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SILVER_BLOCK.get(), RecipeCategory.MISC, ModItems.SILVER_INGOT.get());
            nineBlockStorageRecipes(pRecipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.RAW_SILVER_BLOCK.get(), RecipeCategory.MISC, ModItems.RAW_SILVER.get());
            nineBlockStorageRecipes(pRecipeOutput, RecipeCategory.MISC, ModItems.SILVER_INGOT.get(), RecipeCategory.MISC, ModItems.SILVER_NUGGET.get());
        }

        @Override
        public String getName() {
            return "Inclinations Recipes"; // Provide a name for your data provider
        }

        private void nineBlockStorageRecipes(RecipeOutput recipeOutput, RecipeCategory categoryCompressed, ItemLike compressed, RecipeCategory categoryUncompressed, ItemLike uncompressed) {
            ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), categoryUncompressed, uncompressed, 9)
                    .requires(compressed)
                    .unlockedBy(getHasName(compressed), has(compressed))
                    .save(recipeOutput);

            ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), categoryCompressed, compressed)
                    .pattern("###")
                    .pattern("###")
                    .pattern("###")
                    .define('#', uncompressed)
                    .unlockedBy(getHasName(uncompressed), has(uncompressed))
                    .save(recipeOutput);
        }

        private void oreSmelting(RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
            SimpleCookingRecipeBuilder.smelting(Ingredient.of(pIngredients.getFirst()), pCategory, pResult, pExperience, pCookingTime)
                    .group(pGroup)
                    .unlockedBy(getHasName(pIngredients.getFirst()), has(pIngredients.getFirst()))
                    .save(pRecipeOutput);
        }

        private void oreBlasting(RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
            SimpleCookingRecipeBuilder.blasting(Ingredient.of(pIngredients.getFirst()), pCategory, pResult, pExperience, pCookingTime)
                    .group(pGroup)
                    .unlockedBy(getHasName(pIngredients.getFirst()), has(pIngredients.getFirst()))
                    .save(pRecipeOutput);
        }
    }
}
