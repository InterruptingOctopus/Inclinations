package com.interruptingoctopus.inclinations.datagen;

import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.block.ModBlocks;
import com.interruptingoctopus.inclinations.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.function.Supplier;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(PackOutput output) {
        super(output, Inclinations.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        //Blocks
        ModBlocks.REGISTERED_BLOCKS.forEach((name, blockHolder) -> {
            String friendlyName = capitalizeWords(name.replace("_", " "));
            // Call .get() on the DeferredHolder to get the actual Block instance
            addBlock((Supplier<? extends Block>) blockHolder.get(), friendlyName);
        });

        //Items
        ModItems.REGISTERED_CUSTOM_ITEMS.forEach((name, itemHolder) -> {
            String friendlyName = capitalizeWords(name.replace("_", " "));
            addItem((Supplier<? extends Item>) itemHolder.get(), friendlyName); //Add Translation for the item
        });

        //Creative Tabs
        add("itemGroup." + Inclinations.MOD_ID + ".blocks_tab", "inclinations");
        add("itemGroup." + Inclinations.MOD_ID + ".items_tab", "inclinations");
    }

    // Helper method to capitalize words (optional, but makes names look nicer)
    private String capitalizeWords(String input) {
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;
        for (char c : input.toCharArray()) {
            if (Character.isWhitespace(c)) {
                capitalizeNext = true;
                result.append(c);
            } else if (capitalizeNext) {
                result.append(Character.toUpperCase(c));
                capitalizeNext = false;
            } else {
                result.append(Character.toLowerCase(c));
            }
        }
        return result.toString();
    }
}
