package com.interruptingoctopus.inclinations.block.entity;

import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Utility class for managing and registering all custom block entity types in the Inclinations mod.
 */
public class ModBlockEntities {
    /**
     * Deferred register for block entity types.
     */
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Inclinations.MOD_ID);

    /**
     * The Block Entity Type for the Altar.
     */
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AltarBlockEntity>> ALTAR_BE =
            BLOCK_ENTITIES.register("altar_be", () -> new BlockEntityType<>(
                    AltarBlockEntity::new, ModBlocks.ALTAR.get()));


    /**
     * Registers the deferred register for block entity types with the event bus.
     * This method should be called during mod construction.
     *
     * @param eventBus The event bus.
     */
    public static void register(IEventBus eventBus) {

        BLOCK_ENTITIES.register(eventBus);
    }
}
