package com.interruptingoctopus.inclinations.datagen;

import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.block.ModBlocks;
import com.interruptingoctopus.inclinations.item.ModItems;
import com.interruptingoctopus.inclinations.util.ModMetals;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

/**
 * Data provider for generating language translations for the mod.
 */
public class ModLangProvider extends LanguageProvider {

    /**
     * Constructor for ModLangProvider.
     *
     * @param output The pack output.
     */
    public ModLangProvider(PackOutput output) {
        super(output, Inclinations.MOD_ID, "en_us");
    }

    /**
     * Adds all translations for the mod's items, blocks, and creative tabs.
     */
    @Override
    protected void addTranslations() {
        // Add translation for the screwdriver
        add(ModItems.SCREW_DRIVER.get(), "Screwdriver");

        // Add translation for the Altar container title
        add("container.inclinations.altar", "Altar");

        // Dynamically add translations for items
        ModMetals.METALS.keySet().forEach(metalName -> { // Use ModMetals.METALS
            add(ModItems.get(metalName + "_ingot").get(), capitalize(metalName) + " Ingot");
            add(ModItems.get(metalName + "_nugget").get(), capitalize(metalName) + " Nugget");
            add(ModItems.get("raw_" + metalName).get(), "Raw " + capitalize(metalName));
        });

        // Dynamically add translations for blocks
        ModBlocks.REGISTERED_BLOCKS.forEach((name, deferredBlock) -> {
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

    /**
     * Helper method to capitalize the first letter of each word in a string.
     * @param text The input string.
     * @return The capitalized string.
     */
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
