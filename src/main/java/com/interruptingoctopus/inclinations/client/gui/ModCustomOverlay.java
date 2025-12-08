package com.interruptingoctopus.inclinations.client.gui;

import com.interruptingoctopus.inclinations.Inclinations;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;


/**
 * Renders a custom hunger overlay on the HUD, replacing the vanilla hunger bar.
 * This overlay is positioned above the health bar and dynamically changes sprites
 * based on the player's food level, saturation, and hunger effect status.
 */
@EventBusSubscriber(modid = Inclinations.MOD_ID, value = Dist.CLIENT)
public class ModCustomOverlay {

    // Declare ResourceLocation objects as static final fields using the static factory method
    private static final ResourceLocation HUNGER_EMPTY_SPRITE = ResourceLocation.fromNamespaceAndPath("minecraft", "hud/hunger_empty");
    private static final ResourceLocation HUNGER_HALF_SPRITE = ResourceLocation.fromNamespaceAndPath("minecraft", "hud/hunger_half");
    private static final ResourceLocation HUNGER_FULL_SPRITE = ResourceLocation.fromNamespaceAndPath("minecraft", "hud/hunger_full");

    private static final ResourceLocation HUNGER_EMPTY_POISON_SPRITE = ResourceLocation.fromNamespaceAndPath("minecraft", "hud/hunger_empty_poison");
    private static final ResourceLocation HUNGER_HALF_POISON_SPRITE = ResourceLocation.fromNamespaceAndPath("minecraft", "hud/hunger_half_poison");
    private static final ResourceLocation HUNGER_FULL_POISON_SPRITE = ResourceLocation.fromNamespaceAndPath("minecraft", "hud/hunger_full_poison");

    private static final ResourceLocation HUNGER_EMPTY_HUNGER_SPRITE = ResourceLocation.fromNamespaceAndPath("minecraft", "hud/hunger_empty_hunger");
    private static final ResourceLocation HUNGER_HALF_HUNGER_SPRITE = ResourceLocation.fromNamespaceAndPath("minecraft", "hud/hunger_half_hunger");
    private static final ResourceLocation HUNGER_FULL_HUNGER_SPRITE = ResourceLocation.fromNamespaceAndPath("minecraft", "hud/hunger_full_hunger");

    /**
     * Registers the custom hunger overlay to replace the vanilla hunger bar.
     *
     * @param event The event for registering GUI layers.
     */
    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        // Correctly pass a ResourceLocation for the layerId using the mod's ID and static factory method
        event.registerAbove(VanillaGuiLayers.FOOD_LEVEL, ResourceLocation.fromNamespaceAndPath(Inclinations.MOD_ID, "inclinations_hunger_overlay"), ModCustomOverlay::renderHungerOverlay);
    }

    /**
     * Renders the custom hunger overlay.
     * This method is called by the GUI rendering system.
     *
     * @param guiGraphics  The GuiGraphics instance for rendering.
     * @param deltaTracker The DeltaTracker instance for timing information.
     */
    public static void renderHungerOverlay(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player == null) {
            return;
        }

        FoodData foodData = player.getFoodData();
        int foodLevel = foodData.getFoodLevel();
        int guiWidth = guiGraphics.guiWidth();
        int guiHeight = guiGraphics.guiHeight();

        // Calculate position for the hunger bar.
        // Vanilla health bar is at guiHeight - 39.
        // Hunger bar should be above health, so guiHeight - 39 - 10 (height of hunger bar) - some padding.
        // Let's aim for 10 pixels above the health bar.
        int leftX = guiWidth / 2 + 91; // Right side of the screen, aligned with vanilla health/hunger
        int topY = guiHeight - 39 - 10; // 10 pixels above the vanilla health bar position (guiHeight - 39)

        boolean isPoisoned = player.hasEffect(MobEffects.POISON);
        boolean isHungry = player.hasEffect(MobEffects.HUNGER);

        for (int i = 0; i < 10; ++i) {
            int x = leftX - i * 8 - 9; // Position for each icon, from right to left

            ResourceLocation emptySprite;
            ResourceLocation halfSprite;
            ResourceLocation fullSprite;

            if (isPoisoned) {
                emptySprite = HUNGER_EMPTY_POISON_SPRITE;
                halfSprite = HUNGER_HALF_POISON_SPRITE;
                fullSprite = HUNGER_FULL_POISON_SPRITE;
            } else if (isHungry) {
                emptySprite = HUNGER_EMPTY_HUNGER_SPRITE;
                halfSprite = HUNGER_HALF_HUNGER_SPRITE;
                fullSprite = HUNGER_FULL_HUNGER_SPRITE;
            } else {
                emptySprite = HUNGER_EMPTY_SPRITE;
                halfSprite = HUNGER_HALF_SPRITE;
                fullSprite = HUNGER_FULL_SPRITE;
            }

            // Draw background icon using the 5-argument blitSprite overload with RenderPipelines.GUI
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, emptySprite, x, topY, 9, 9);

            // Draw foreground icon based on food level using the 5-argument blitSprite overload with RenderPipelines.GUI
            if (i * 2 + 1 < foodLevel) { // Full icon
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, fullSprite, x, topY, 9, 9);
            } else if (i * 2 + 1 == foodLevel) { // Half icon
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, halfSprite, x, topY, 9, 9);
            }
        }
    }
}