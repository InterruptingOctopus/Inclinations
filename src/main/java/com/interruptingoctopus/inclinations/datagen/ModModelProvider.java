package com.interruptingoctopus.inclinations.datagen;

import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.block.ModBlocks;
import com.interruptingoctopus.inclinations.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import java.util.stream.Stream;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, Inclinations.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        //itemModels.generateFlatItem(ModItems._.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.PLATINUM_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.PLATINUM_NUGGET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_PLATINUM.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.SILVER_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SILVER_NUGGET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_SILVER.get(), ModelTemplates.FLAT_ITEM);

        //itemModels.generateFlatItem(ModItems.HANDHELD_ITEM.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        //itemModels.generateTrimmableItem(ModItems.TRIMMABLE_HELMET.get(), ModelArmorMaterials.MATERIAL, "material", false);
        //itemModels.generateTrimmableItem(ModItems.TRIMMABLE_CHESTPLATE.get(), ModelArmorMaterials.MATERIAL, "material", false);
        //itemModels.generateTrimmableItem(ModItems.TRIMMABLE_LEGGINGS.get(), ModelArmorMaterials.MATERIAL, "material", false);
        //itemModels.generateTrimmableItem(ModItems.TRIMMABLE_BOOTS.get(), ModelArmorMaterials.MATERIAL, "material", false);

        /* BLOCKS */
        //blockModels.createTrivialCube(ModBlocks._.get());
        blockModels.createTrivialCube(ModBlocks.PLATINUM_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.PLATINUM_ORE.get());
        blockModels.createTrivialCube(ModBlocks.RAW_PLATINUM_BLOCK.get());

        blockModels.createTrivialCube(ModBlocks.SILVER_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.SILVER_ORE.get());
        blockModels.createTrivialCube(ModBlocks.RAW_SILVER_BLOCK.get());

    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return super.getKnownBlocks();
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems(){
        return super.getKnownItems();
    }
}
