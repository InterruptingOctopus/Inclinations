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
        // Add translation for the screwdriver
        add(ModItems.SCREW_DRIVER.get(), "Screwdriver");

        // Add translation for the Altar container title
        add("container.inclinations.altar", "Altar");

        // Dynamically add translations for items
        ModItems.METALS.forEach(metalName -> {
            add(ModItems.get(metalName + "_ingot").get(), capitalize(metalName) + " Ingot");
            add(ModItems.get(metalName + "_nugget").get(), capitalize(metalName) + " Nugget");
            add(ModItems.get("raw_" + metalName).get(), "Raw " + capitalize(metalName));
        });

        // Dynamically add translations for blocks
        ModBlocks.REGISTERED_BLOCKS.forEach((name, deferredBlock) -> {
            // Skip the custom lightning rod as it's handled above

            String displayName = capitalize(name.replace("_", " "));
            if (name.endsWith("_block")) {
                displayName = "Block of " + capitalize(name.substring(0, name.indexOf("_block")));
            } else if (name.endsWith("_ore")) {
                displayName = capitalize(name.substring(0, name.indexOf("_ore"))) + " Ore";
            } else if (name.startsWith("raw_") && name.endsWith("_block")) {
                displayName = "Block of Raw " + capitalize(name.substring(4, name.indexOf("_block")));
            }
            add(deferredBlock.get(), displayName);
        });

        // Creative Tabs
        add("creativetab.inclinations_ingredients_tab", "Inclinations: Ingredients");
        add("creativetab.inclinations_building_blocks_tab", "Inclinations: Building Blocks");
    }

    // Helper method to capitalize the first letter of each word
    private String capitalize(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;
        for (char c : text.toCharArray()) {
            if (Character.isWhitespace(c)) {
                capitalizeNext = true;
                result.append(c);
            } else if (capitalizeNext) {
                result.append(Character.toUpperCase(c));
                capitalizeNext = false;
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
