package com.interruptingoctopus.inclinations.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * The Altar block, a block that allows players to transfer enchantments from items to books.
 */
public class AltarBlock extends Block {
    /**
     * Constructor for the AltarBlock.
     *
     * @param pProperties The properties of the block.
     */
    public AltarBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
    }
}
