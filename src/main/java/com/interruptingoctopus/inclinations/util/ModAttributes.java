package com.interruptingoctopus.inclinations.util;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(
            BuiltInRegistries.ATTRIBUTE, "inclinations"
    );

    public static final Holder<Attribute> VIGOR = ATTRIBUTES.register("vigor", () -> new RangedAttribute(
                    // The translation key to use.
                    "attributes.inclinations.vigor",
                    //The default value.
                    10,
                    // Min and max values.
                    -99,
                    99
                    )
            );

    public static final Holder<Attribute> MIND = ATTRIBUTES.register("mind", () -> new RangedAttribute(
                    // The translation key to use.
                    "attributes.inclinations.mind",
                    //The default value.
                    10,
                    // Min and max values.
                    -99,
                    99
                    )
            );

    public static final Holder<Attribute> ENDURANCE = ATTRIBUTES.register("endurance", () -> new RangedAttribute(
                    // The translation key to use.
                    "attributes.inclinations.X",
                    //The default value.
                    10,
                    // Min and max values.
                    -99,
                    99
                    )
            );

    public static final Holder<Attribute> STRENGTH = ATTRIBUTES.register("strength", () -> new RangedAttribute(
                    // The translation key to use.
                    "attributes.inclinations.strength",
                    //The default value.
                    10,
                    // Min and max values.
                    -99,
                    99
                    )
            );

    public static final Holder<Attribute> DEXTERITY = ATTRIBUTES.register("dexterity", () -> new RangedAttribute(
                    // The translation key to use.
                    "attributes.inclinations.dexterity",
                    //The default value.
                    10,
                    // Min and max values.
                    -99,
                    99
                    )
            );

    public static final Holder<Attribute> INTELLIGENCE = ATTRIBUTES.register("intelligence", () -> new RangedAttribute(
                    // The translation key to use.
                    "attributes.inclinations.intelligence",
                    //The default value.
                    10,
                    // Min and max values.
                    -99,
                    99
                    )
            );

    public static final Holder<Attribute> WISDOM = ATTRIBUTES.register("wisdom", () -> new RangedAttribute(
                    // The translation key to use.
                    "attributes.inclinations.wisdom",
                    //The default value.
                    10,
                    // Min and max values.
                    -99,
                    99
                    )
            );

    public static final Holder<Attribute> EGO = ATTRIBUTES.register("ego", () -> new RangedAttribute(
                            // The translation key to use.
                            "attributes.inclinations.ego",
                            //The default value.
                            10,
                            // Min and max values.
                            -99,
                            99
                    )
            );

}
