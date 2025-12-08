package com.interruptingoctopus.inclinations.util;

import com.interruptingoctopus.inclinations.Inclinations;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/**
 * Centralized class for defining custom TagKeys for blocks and items in the Inclinations mod.
 * This helps in organizing and easily accessing mod-specific tags.
 */
public class ModTags {

    public static final class Blocks {
        public static final TagKey<Block> NEEDS_DIAMOND_TOOL
                = createTag("needs_diamond_tool");
        public static final TagKey<Block> NEEDS_IRON_TOOL
                = createTag("needs_iron_tool");
        public static final TagKey<Block> NEEDS_NETHERITE_TOOL
                = createTag("needs_netherite_tool");

        private static TagKey<Block> createTag(String name) {
            // Using the standard factory method for clarity and consistency
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Inclinations.MOD_ID, name));
        }
    }

    public static final class Items {
        public static final TagKey<Item> INGOTS_SILVER
                = createTag("ingots/silver");
        public static final TagKey<Item> NUGGETS_SILVER
                = createTag("nuggets/silver");

        // Forge Tags
        public static final TagKey<Item> FORGE_INGOTS = forgeTag("ingots");
        public static final TagKey<Item> FORGE_RAW_MATERIALS = forgeTag("raw_materials");

        private static TagKey<Item> createTag(String name) {
            // Using the standard factory method for clarity and consistency
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Inclinations.MOD_ID, name));
        }

        /**
         * Creates a TagKey for an item within the "forge" namespace.
         *
         * @param name The path for the ResourceLocation.
         * @return A TagKey for the item.
         */
        private static TagKey<Item> forgeTag(String name) {
            // Using the standard factory method for clarity and consistency
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", name));
        }

        /**
         * Creates a TagKey for a metal ingot within the "forge" namespace, e.g., "forge:ingots/silver".
         *
         * @param metalName The name of the metal (e.g., "silver").
         * @return A TagKey for the specific metal ingot.
         */
        public static TagKey<Item> forgeIngotTag(String metalName) {
            return forgeTag("ingots/" + metalName);
        }

        /**
         * Creates a TagKey for a raw metal material within the "forge" namespace, e.g., "forge:raw_materials/silver".
         *
         * @param metalName The name of the metal (e.g., "silver").
         * @return A TagKey for the specific raw metal material.
         */
        public static TagKey<Item> forgeRawMaterialTag(String metalName) {
            return forgeTag("raw_materials/" + metalName);
        }
    }
}
