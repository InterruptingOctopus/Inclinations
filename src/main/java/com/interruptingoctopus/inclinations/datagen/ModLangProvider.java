package com.interruptingoctopus.inclinations.datagen;

import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.block.ModBlocks;
import com.interruptingoctopus.inclinations.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLangProvider extends LanguageProvider {

    public ModLangProvider(PackOutput output) {
        super(output, Inclinations.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Items
        add(ModItems.PLATINUM_INGOT.get(), "Platinum Ingot");
        add(ModItems.PLATINUM_NUGGET.get(), "Platinum Nugget");
        add(ModItems.RAW_PLATINUM.get(), "Raw Platinum");
        add(ModItems.SILVER_INGOT.get(), "Silver Ingot");
        add(ModItems.SILVER_NUGGET.get(), "Silver Nugget");
        add(ModItems.RAW_SILVER.get(), "Raw Silver");

        // Blocks
        add(ModBlocks.PLATINUM_BLOCK.get(), "Block of Platinum");
        add(ModBlocks.PLATINUM_ORE.get(), "Platinum Ore");
        add(ModBlocks.RAW_PLATINUM_BLOCK.get(), "Block of Raw Platinum");
        add(ModBlocks.SILVER_BLOCK.get(), "Block of Silver");
        add(ModBlocks.SILVER_ORE.get(), "Silver Ore");
        add(ModBlocks.RAW_SILVER_BLOCK.get(), "Block of Raw Silver");

        // Creative Tabs
        add("creativetab.inclinations_ingredients_tab", "Inclinations: Ingredients");
        add("creativetab.inclinations_building_blocks_tab", "Inclinations: Building Blocks");
    }
}