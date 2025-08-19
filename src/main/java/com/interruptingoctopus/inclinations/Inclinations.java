package com.interruptingoctopus.inclinations;

import com.interruptingoctopus.inclinations.block.ModBlocks;
import com.interruptingoctopus.inclinations.item.ModCreativeModeTabs;
import com.interruptingoctopus.inclinations.item.ModItems;


import com.mojang.logging.LogUtils;
import net.minecraft.world.level.ItemLike;

import net.minecraft.world.item.CreativeModeTabs;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import static com.interruptingoctopus.inclinations.block.ModBlocks.BLOCKS;
import static com.interruptingoctopus.inclinations.item.ModItems.ITEMS;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Inclinations.MOD_ID)
public class Inclinations {
    public static final String MOD_ID = "inclinations";
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Inclinations(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for mod loading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);


        ModBlocks.BLOCKS.register(modEventBus);// Register the DeferredRegister.Blocks to the mod event bus
        ModItems.ITEMS.register(modEventBus); // Register the DeferredRegister.Items to the mod event bus
        ModCreativeModeTabs.CREATIVE_MODE_TAB.register(modEventBus); // Register your creative tabs

        // Call the method to register blocks and their items defined in ModBlocks and ModItems
        ModBlocks.registerBlocks(); // Register blocks
        ModItems.registerItems();// Register your custom items using the map
        ModItems.registerBlockItems(); // Register block items

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            ITEMS.getEntries().forEach(i -> event.accept((ItemLike) i));
        }
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            BLOCKS.getEntries().forEach(i -> event.accept((ItemLike) i));
        }

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }

    }

}
