package com.interruptingoctopus.inclinations;

import com.interruptingoctopus.inclinations.recipe.ModRecipes;
import com.interruptingoctopus.inclinations.block.ModBlocks;
import com.interruptingoctopus.inclinations.block.entity.ModBlockEntities;
import com.interruptingoctopus.inclinations.datagen.DataGenerators;
import com.interruptingoctopus.inclinations.events.ModEvents;
import com.interruptingoctopus.inclinations.item.ModCreativeModeTabs;
import com.interruptingoctopus.inclinations.item.ModItems;
import com.interruptingoctopus.inclinations.screen.AltarScreen;
import com.interruptingoctopus.inclinations.screen.ModMenuTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Inclinations.MOD_ID)
public class Inclinations {
    public static final String MOD_ID = "inclinations";

    public Inclinations(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for mod loading
        modEventBus.addListener(this::commonSetup);
        // Register the clientSetup method for client-side initialization
        modEventBus.addListener(this::clientSetup);
        // Register the data generation event listener
        modEventBus.addListener(DataGenerators::gatherData);


        // IMPORTANT: ModBlocks must be registered before ModItems due to BlockItem dependencies.
        ModBlocks.register(modEventBus); // Blocks and their BlockItems are registered here
        ModItems.register(modEventBus);  // Pure items are registered here
        ModItems.registerBlockItems(); // Call this to register BlockItems after blocks are registered
        ModRecipes.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModBlockEntities.register(modEventBus);

        // Register ourselves for server and other game events we are interested in (Game Bus)
        NeoForge.EVENT_BUS.register(this); // This registers @SubscribeEvent methods in this class

        // Register ModEvents to the mod event bus for client-side events
        modEventBus.addListener(ModEvents::registerClientEvents);
        // Removed: modEventBus.addListener(ModEvents::onRegisterGuiLayers); // Removed listener for GUI layer registration

        // Register our mod's ModConfigSpec
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        modEventBus.addListener(this::registerScreens);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Put your Common Setup Logic here
    }

    // Modern, non-static method to handle client setup
    private void clientSetup(final FMLClientSetupEvent event) {
        // Client-side initialization code goes here
    }

    public void registerScreens(final RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.ALTAR_MENU.get(), AltarScreen::new);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Put your Server Starting Logic here
    }
}