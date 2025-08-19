package com.interruptingoctopus.inclinations.datagen;

import com.interruptingoctopus.inclinations.Inclinations;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

// This class will be automatically registered to the mod event bus
@Mod.EventBusSubscriber(modid = Inclinations.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators  {
    @SubscribeEvent
    public static void gatherDataClient(GatherDataEvent.Client event) {
        PackOutput packOutput = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        event.getGenerator().addProvider(true, new ModLanguageProvider(packOutput));

    }
    // @SubscribeEvent
    // public static void gatherDataServer(GatherDataEvent.Server event) {
    //     PackOutput packOutput = event.getGenerator().getPackOutput();
    //     event.getGenerator().addProvider(true, new ModRecipeProvider(packOutput));
    // }
}
