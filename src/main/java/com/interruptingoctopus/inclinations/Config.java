package com.interruptingoctopus.inclinations;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * This class handles the mod's configuration.
 * It defines configuration options and their default values.
 */
public class Config {
    public static final ModConfigSpec SPEC;
    public static final Client CLIENT;

    static {
        ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

        CLIENT = new Client(BUILDER);

        SPEC = BUILDER.build();
    }

    /**
     * Client-side configuration options.
     */
    public static class Client {
        public ModConfigSpec.IntValue exampleValue;

        public Client(ModConfigSpec.Builder builder) {
            builder.push("client");
            exampleValue = builder
                    .comment("An example client-side configuration value.")
                    .defineInRange("exampleValue", 10, 0, 100);
            builder.pop();
        }
    }
}
