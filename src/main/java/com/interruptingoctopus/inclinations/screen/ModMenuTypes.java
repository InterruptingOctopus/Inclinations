package com.interruptingoctopus.inclinations.screen;

import com.interruptingoctopus.inclinations.Inclinations;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, Inclinations.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<AltarMenu>> ALTAR_MENU =
            MENUS.register("altar_menu", () -> new MenuType<>((IContainerFactory<AltarMenu>) AltarMenu::new, FeatureFlagSet.of()));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}