package com.interruptingoctopus.inclinations;

import com.interruptingoctopus.inclinations.block.ModBlocks;
import com.interruptingoctopus.inclinations.block.entity.ModBlockEntities;
import com.interruptingoctopus.inclinations.events.ModEvents;
import com.interruptingoctopus.inclinations.item.ModCreativeModeTabs;
import com.interruptingoctopus.inclinations.item.ModItems;
import com.interruptingoctopus.inclinations.recipe.ModRecipes;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

/**
 * The main mod class for Inclinations.
 * This class is the entry point for the mod and handles the registration of all mod content.
 */
@Mod(Inclinations.MOD_ID)
public class Inclinations {
    /**
     * The unique identifier for the mod.
     */
    public static final String MOD_ID = "inclinations";
    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Constructor for the Inclinations mod.
     * This is where all the registration for the mod happens.
     *
     * @param modEventBus  The event bus for the mod.
     * @param modContainer The container for the mod.
     */
    public Inclinations(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("Hello from Inclinations!"); // Using the logger
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        NeoForge.EVENT_BUS.register(this);

        // Register all deferred registers
        ModBlocks.register(modEventBus);
        ModItems.ITEMS.register(modEventBus); // Corrected registration
        ModItems.registerBlockItems(); // Call this to register BlockItems after blocks are registered
        ModBlockEntities.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register the data generator

        // Register our mod's ModConfigSpec
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    /**
     * Common setup logic, called during mod loading.
     * @param event The common setup event.
     */
    private void commonSetup(final FMLCommonSetupEvent event) {
        // Put your Common Setup Logic here
    }

    /**
     * Adds items to creative mode tabs.
     *
     * @param event The BuildCreativeModeTabContentsEvent.
     */
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        // Removed empty if statements
    }

    /**
     * Called when the server is starting.
     * @param event The server starting event.
     */
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Put your Server Starting Logic here
    }

    /**
     * Client-side event subscriber for Inclinations.
     * This class handles client-specific registrations and setups.
     */
    @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT) // Corrected EventBusSubscriber usage
    public static class ClientModEvents {
        /**
         * Client setup logic, called during mod loading on the client.
         *
         * @param event The client setup event.
         */
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ModEvents.registerClientEvents(event);
            // Client-side initialization code goes here
            // Example: ItemBlockRenderTypes.setRenderLayer(ModBlocks.SOME_BLOCK.get(), RenderType.cutout());
        }
    }
}
