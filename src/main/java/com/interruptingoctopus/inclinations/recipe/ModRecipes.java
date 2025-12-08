package com.interruptingoctopus.inclinations.recipe;

import com.interruptingoctopus.inclinations.Inclinations;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    /**
     * Deferred register for recipe serializers.
     */
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, Inclinations.MOD_ID);
    /**
     * Deferred register for recipe types.
     */
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, Inclinations.MOD_ID);

    /**
     * Registers the deferred registers for recipes with the event bus.
     *
     * @param eventBus The event bus.
     */
    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
