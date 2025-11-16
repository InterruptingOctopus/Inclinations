package com.interruptingoctopus.inclinations;

import com.interruptingoctopus.inclinations.Recipe.ModRecipes;
import com.interruptingoctopus.inclinations.block.ModBlocks;
import com.interruptingoctopus.inclinations.item.ModCreativeModeTabs;
import com.interruptingoctopus.inclinations.item.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Inclinations.MOD_ID)
public class Inclinations {
    public static final String MOD_ID = "inclinations";

    public Inclinations(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for mod loading
        modEventBus.addListener(this::commonSetup);
        // Register the modern non-static clientSetup method for client-side setup
        modEventBus.addListener(this::clientSetup);

        // Register event busses for your mod content

        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModCreativeModeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in (Game Bus)
        NeoForge.EVENT_BUS.register(this);

        // Register our mod's ModConfigSpec
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Put your Common Setup Logic here
    }

    // Modern, non-static method to handle client setup
    private void clientSetup(final FMLClientSetupEvent event) {
        // Client-side initialization code goes here
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Put your Server Starting Logic here
    }
}