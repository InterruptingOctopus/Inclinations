package com.interruptingoctopus.inclinations.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * The Block Entity for the Altar block.
 * This class handles the logic and data storage for the Altar.
 */
public class AltarBlockEntity extends BlockEntity {
    /**
     * Constructor for the AltarBlockEntity.
     *
     * @param pPos        The position of the block entity.
     * @param pBlockState The block state of the block entity.
     */
    public AltarBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ALTAR_BE.get(), pPos, pBlockState);
    }
}
