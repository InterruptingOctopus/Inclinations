package com.interruptingoctopus.inclinations.datagen;

import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.block.ModBlocks;
import com.interruptingoctopus.inclinations.item.ModItems;
import com.interruptingoctopus.inclinations.util.ModMetals;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Data provider for generating recipes for the mod.
 */
public class ModRecipeProvider extends RecipeProvider { // Outer class extends RecipeProvider
    public ModRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
        super(provider, recipeOutput);
    }

    /**
     * Inner static class that extends RecipeProvider.Runner to build recipes.
     */
    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
            super(packOutput, provider);
        }

        @Override
        protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.@NotNull Provider provider, @NotNull RecipeOutput recipeOutput) {
            return new ModRecipeProvider(provider, recipeOutput);
        }

        @Override
        public @NotNull String getName() {
            return "Inclinations Recipes";
        }
    }

    @Override
    protected void buildRecipes() {
        // --- Screwdriver Recipe ---
        shaped(RecipeCategory.TOOLS, ModItems.SCREW_DRIVER.get())
                .pattern(" N ")
                .pattern(" N ")
                .pattern(" C ")
                .define('N', Items.IRON_NUGGET)
                .define('C', Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(output);

        // --- Altar Recipe ---
        shaped(RecipeCategory.DECORATIONS, ModBlocks.ALTAR.get())
                .pattern("R W")
                .pattern("QLQ")
                .pattern(" Q ")
                .define('R', Items.RED_CARPET)
                .define('W', Items.WHITE_CARPET)
                .define('Q', Items.QUARTZ_BLOCK)
                .define('L', Items.LECTERN)
                .unlockedBy("has_lectern", has(Items.LECTERN)).save(output);

        // --- Dynamic Metal Recipes ---
        ModMetals.METALS.forEach((name, metalProperties) -> {
            // Block to Ingot/Nugget recipes
            shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.get(name + "_block").get())
                    .pattern("MMM")
                    .pattern("MMM")
                    .pattern("MMM")
                    .define('M', ModItems.get(name + "_ingot").get())
                    .unlockedBy("has_" + name + "_ingot", has(ModItems.get(name + "_ingot").get())).save(output);

            shapeless(RecipeCategory.MISC, ModItems.get(name + "_ingot").get(), 9)
                    .requires(ModBlocks.get(name + "_block").get())
                    .unlockedBy("has_" + name + "_block", has(ModBlocks.get(name + "_block").get())).save(output, ModBlocks.get(name + "_block").getId().getPath() + "_from_" + ModItems.get(name + "_ingot").getId().getPath());

            shaped(RecipeCategory.MISC, ModItems.get(name + "_ingot").get())
                    .pattern("NNN")
                    .pattern("NNN")
                    .pattern("NNN")
                    .define('N', ModItems.get(name + "_nugget").get())
                    .unlockedBy("has_" + name + "_nugget", has(ModItems.get(name + "_nugget").get())).save(output, ModItems.get(name + "_ingot").getId().getPath() + "_from_" + ModItems.get(name + "_nugget").getId().getPath());

            shapeless(RecipeCategory.MISC, ModItems.get(name + "_nugget").get(), 9)
                    .requires(ModItems.get(name + "_ingot").get())
                    .unlockedBy("has_" + name + "_ingot", has(ModItems.get(name + "_ingot").get())).save(output, ModItems.get(name + "_nugget").getId().getPath() + "_from_" + ModItems.get(name + "_ingot").getId().getPath());

            shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.get("raw_" + name + "_block").get())
                    .pattern("RRR")
                    .pattern("RRR")
                    .pattern("RRR")
                    .define('R', ModItems.get("raw_" + name).get())
                    .unlockedBy("has_raw_" + name, has(ModItems.get("raw_" + name).get())).save(output);

            shapeless(RecipeCategory.MISC, ModItems.get("raw_" + name).get(), 9)
                    .requires(ModBlocks.get("raw_" + name + "_block").get())
                    .unlockedBy("has_raw_" + name + "_block", has(ModBlocks.get("raw_" + name + "_block").get())).save(output, ModBlocks.get("raw_" + name + "_block").getId().getPath() + "_from_" + ModItems.get("raw_" + name).getId().getPath());


            // Ore processing recipes
            float oreXp = 0.7f; // Default XP
            int smeltingTime = 200;
            int blastingTime = 100;

            if (name.equals("platinum")) {
                oreXp = 1.0f;
            } else if (name.equals("silver")) {
                oreXp = 0.8f;
            }

            List<ItemLike> oreSmeltables = List.of(ModBlocks.get(name + "_ore").get(), ModItems.get("raw_" + name).get());
            oreSmelting(output, oreSmeltables, ModItems.get(name + "_ingot").get(), oreXp, smeltingTime, name);
            oreBlasting(output, oreSmeltables, ModItems.get(name + "_ingot").get(), oreXp, blastingTime, name);
        });
    }

    protected void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, ItemLike pResult,
                               float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, ItemLike pResult,
                               float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory, List<ItemLike> pIngredients, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for (ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), RecipeCategory.MISC, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, Inclinations.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
