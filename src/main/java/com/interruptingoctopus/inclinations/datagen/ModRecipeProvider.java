package com.interruptingoctopus.inclinations.datagen;

import com.interruptingoctopus.inclinations.block.ModBlocks;
import com.interruptingoctopus.inclinations.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items; // Import Items
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    // Specific values for Platinum
    private static final float PLATINUM_ORE_XP = 1.0f; // Higher XP for platinum
    private static final int PLATINUM_SMELTING_TIME = 200;
    private static final int PLATINUM_BLASTING_TIME = 100;

    // Specific values for Silver
    private static final float SILVER_ORE_XP = 0.8f; // Slightly higher than default, less than platinum
    private static final int SILVER_SMELTING_TIME = 200;
    private static final int SILVER_BLASTING_TIME = 100;


    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput pRecipeOutput) {
        // --- Screwdriver Recipe ---
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SCREW_DRIVER.get())
                .pattern(" N ")
                .pattern(" N ")
                .pattern(" C ")
                .define('N', Items.IRON_NUGGET)
                .define('C', Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .save(pRecipeOutput);

        // --- Altar Recipe ---
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.ALTAR.get())
                .pattern("R W")
                .pattern("QLQ")
                .pattern(" Q ")
                .define('R', Items.RED_CARPET)
                .define('W', Items.WHITE_CARPET)
                .define('Q', Items.QUARTZ_BLOCK)
                .define('L', Items.LECTERN)
                .unlockedBy("has_lectern", has(Items.LECTERN))
                .save(pRecipeOutput);


        // --- Platinum Recipes ---
        nineBlockStorageRecipes(pRecipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.get("platinum_block").get(), ModItems.get("platinum_ingot").get());
        nineBlockStorageRecipes(pRecipeOutput, RecipeCategory.MISC, ModItems.get("platinum_ingot").get(), ModItems.get("platinum_nugget").get());
        nineBlockStorageRecipes(pRecipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.get("raw_platinum_block").get(), ModItems.get("raw_platinum").get());
        oreProcessingRecipes(pRecipeOutput, ModBlocks.get("platinum_ore").get(), ModItems.get("raw_platinum").get(), ModItems.get("platinum_ingot").get(),
                PLATINUM_ORE_XP, PLATINUM_SMELTING_TIME, PLATINUM_BLASTING_TIME);

        // --- Silver Recipes ---
        nineBlockStorageRecipes(pRecipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.get("silver_block").get(), ModItems.get("silver_ingot").get());
        nineBlockStorageRecipes(pRecipeOutput, RecipeCategory.MISC, ModItems.get("silver_ingot").get(), ModItems.get("silver_nugget").get());
        nineBlockStorageRecipes(pRecipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.get("raw_silver_block").get(), ModItems.get("raw_silver").get());
        oreProcessingRecipes(pRecipeOutput, ModBlocks.get("silver_ore").get(), ModItems.get("raw_silver").get(), ModItems.get("silver_ingot").get(),
                SILVER_ORE_XP, SILVER_SMELTING_TIME, SILVER_BLASTING_TIME);
    }

    /**
     * Generates a pair of smelting and blasting recipes for a given input.
     */
    private void cookingRecipes(@NotNull RecipeOutput pRecipeOutput, @NotNull ItemLike pInput, @NotNull ItemLike pResult, float pExperience, int pSmeltingTime, int pBlastingTime) {
        String resultName = getItemName(pResult);
        String inputName = getItemName(pInput);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(pInput), RecipeCategory.MISC, pResult, pExperience, pSmeltingTime)
                .unlockedBy(getHasName(pInput), has(pInput))
                .save(pRecipeOutput, resultName + "_from_smelting_" + inputName);

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(pInput), RecipeCategory.MISC, pResult, pExperience, pBlastingTime)
                .unlockedBy(getHasName(pInput), has(pInput))
                .save(pRecipeOutput, resultName + "_from_blasting_" + inputName);
    }

    /**
     * Generates all four standard ore processing recipes (smelting and blasting for the ore and its raw form).
     */
    private void oreProcessingRecipes(@NotNull RecipeOutput pRecipeOutput, @NotNull ItemLike pOre, @NotNull ItemLike pRaw, @NotNull ItemLike pResult, float pExperience, int pSmeltingTime, int pBlastingTime) {
        cookingRecipes(pRecipeOutput, pOre, pResult, pExperience, pSmeltingTime, pBlastingTime);
        cookingRecipes(pRecipeOutput, pRaw, pResult, pExperience, pSmeltingTime, pBlastingTime);
    }

    /**
     * Generates recipes for crafting a 3x3 storage block and converting it back to 9 materials.
     */
    private void nineBlockStorageRecipes(@NotNull RecipeOutput pRecipeOutput, @NotNull RecipeCategory pShapedCategory, @NotNull ItemLike pCompact, @NotNull ItemLike pMaterial) {
        String materialName = getItemName(pMaterial);
        String compactName = getItemName(pCompact);

        ShapedRecipeBuilder.shaped(pShapedCategory, pCompact)
                .pattern("MMM")
                .pattern("MMM")
                .pattern("MMM")
                .define('M', pMaterial)
                .unlockedBy(getHasName(pMaterial), has(pMaterial))
                .save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, pMaterial, 9)
                .requires(pCompact)
                .unlockedBy(getHasName(pCompact), has(pCompact))
                .save(pRecipeOutput, compactName + "_from_" + materialName);
    }
}
