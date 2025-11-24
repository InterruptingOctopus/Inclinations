package com.interruptingoctopus.inclinations.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.effect.MobEffects;

public class ModCustomOverlay {
    // Define our own ResourceLocation constants for the hunger sprites
    private static final ResourceLocation FOOD_EMPTY_HUNGER_SPRITE = ResourceLocation.withDefaultNamespace("hud/food_empty_hunger");
    private static final ResourceLocation FOOD_HALF_HUNGER_SPRITE = ResourceLocation.withDefaultNamespace("hud/food_half_hunger");
    private static final ResourceLocation FOOD_FULL_HUNGER_SPRITE = ResourceLocation.withDefaultNamespace("hud/food_full_hunger");
    private static final ResourceLocation FOOD_EMPTY_SPRITE = ResourceLocation.withDefaultNamespace("hud/food_empty");
    private static final ResourceLocation FOOD_HALF_SPRITE = ResourceLocation.withDefaultNamespace("hud/food_half");
    private static final ResourceLocation FOOD_FULL_SPRITE = ResourceLocation.withDefaultNamespace("hud/food_full");

    public void render(GuiGraphics guiGraphics, int screenWidth, int screenHeight) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;

        if (player == null || minecraft.options.hideGui || minecraft.gameMode == null || !minecraft.gameMode.canHurtPlayer()) {
            return;
        }

        FoodData foodData = player.getFoodData();
        int foodLevel = foodData.getFoodLevel();
        float saturationLevel = foodData.getSaturationLevel();

        // Position the hunger bar above the health bar, on the left side.
        int left = screenWidth / 2 - 91; // Same horizontal position as vanilla health bar
        int top = screenHeight - 50; // 11 pixels above the default health bar position (screenHeight - 39)

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        for (int i = 0; i < 10; ++i) {
            int iconX = left + i * 8;

            ResourceLocation emptySprite;
            ResourceLocation halfSprite;
            ResourceLocation fullSprite;

            if (player.hasEffect(MobEffects.HUNGER)) {
                emptySprite = FOOD_EMPTY_HUNGER_SPRITE;
                halfSprite = FOOD_HALF_HUNGER_SPRITE;
                fullSprite = FOOD_FULL_HUNGER_SPRITE;
            } else {
                emptySprite = FOOD_EMPTY_SPRITE;
                halfSprite = FOOD_HALF_SPRITE;
                fullSprite = FOOD_FULL_SPRITE;
            }

            guiGraphics.blitSprite(emptySprite, iconX, top, 9, 9);

            if (saturationLevel > (float)i * 2.0F + 1.0F) {
                guiGraphics.blitSprite(FOOD_FULL_SPRITE, iconX, top, 9, 9);
            } else if (saturationLevel > (float)i * 2.0F) {
                guiGraphics.blitSprite(FOOD_HALF_SPRITE, iconX, top, 9, 9);
            }

            if (foodLevel > i * 2 + 1) {
                guiGraphics.blitSprite(fullSprite, iconX, top, 9, 9);
            } else if (foodLevel == i * 2 + 1) {
                guiGraphics.blitSprite(halfSprite, iconX, top, 9, 9);
            }
        }
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}