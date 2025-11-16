package com.interruptingoctopus.inclinations.datagen;

import com.interruptingoctopus.inclinations.Inclinations;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.DataProvider; // This import is needed for DataProvider.Factory
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Inclinations.MOD_ID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // Client-side providers
        generator.addProvider(true, new ModModelProvider(packOutput));
        generator.addProvider(true, new ModLangProvider(packOutput));

        // Server-side providers
        // Explicitly cast the lambda to DataProvider.Factory to resolve the ambiguity
        generator.addProvider(true, (DataProvider.Factory<ModRecipeProvider.Runner>) output -> new ModRecipeProvider.Runner(output, lookupProvider));
    }
}
