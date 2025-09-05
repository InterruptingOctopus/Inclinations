package com.interruptingoctopus.inclinations;

import com.interruptingoctopus.inclinations.Recipe.ModRecipes;
import com.interruptingoctopus.inclinations.block.ModBlocks;
import com.interruptingoctopus.inclinations.item.ModCreativeModeTabs;
import com.interruptingoctopus.inclinations.item.ModItems;


import com.mojang.logging.LogUtils;


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

        ModCreativeModeTabs.CREATIVE_MODE_TAB.register(modEventBus); // Register your creative tabs

        ModBlocks.BLOCKS.register(modEventBus);// Register the DeferredRegister.Blocks to the mod event bus
        ModItems.ITEMS.register(modEventBus); // Register the DeferredRegister.Items to the mod event bus

//      ModDataComponents.register(modEventBus);
//      ModSounds.register(modEventBus);
//
//      ModEffects.register(modEventBus);
//      ModPotions.register(modEventBus);
//
//      ModEnchantmentEffects.register(modEventBus);
//      ModEntities.register(modEventBus);
//
//      ModVillagers.register(modEventBus);
//      ModParticles.register(modEventBus);
//
//      ModLootModifiers.register(modEventBus);
//      ModBlockEntities.register(modEventBus);
//
//      ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);

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
            //event.accept(ModItems.);
            //Platinum
            event.accept(ModItems.PLATINUM_INGOT);
            event.accept(ModItems.PLATINUM_NUGGET);
            event.accept(ModItems.RAW_PLATINUM);
            //Silver
            event.accept(ModItems.SILVER_INGOT);
            event.accept(ModItems.SILVER_NUGGET);
            event.accept(ModItems.RAW_SILVER);
            }

        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.PLATINUM_BLOCK);
            event.accept(ModBlocks.PLATINUM_ORE);
            event.accept(ModBlocks.RAW_PLATINUM_BLOCK);

            event.accept(ModBlocks.SILVER_BLOCK);
            event.accept(ModBlocks.SILVER_ORE);
            event.accept(ModBlocks.RAW_SILVER_BLOCK);
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
