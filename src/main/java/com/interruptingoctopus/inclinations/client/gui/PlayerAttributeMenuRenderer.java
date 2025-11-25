package com.interruptingoctopus.inclinations.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import com.interruptingoctopus.inclinations.Inclinations;

public class PlayerAttributeMenuRenderer {
    private static final ResourceLocation PLAYER_ATTRIBUTE_MENU_TEXTURE = 
            ResourceLocation.fromNamespaceAndPath(Inclinations.MOD_ID, "textures/gui/container/player_attribute_menu.png");
    private static final ResourceLocation HEALTH_ICON = ResourceLocation.withDefaultNamespace("hud/heart_full");
    private static final ResourceLocation HUNGER_ICON = ResourceLocation.withDefaultNamespace("hud/food_full");
    private static final ResourceLocation ARMOR_ICON = ResourceLocation.withDefaultNamespace("hud/armor_full");

    public static void render(GuiGraphics guiGraphics, int inventoryGuiLeft, int inventoryGuiTop) {
        guiGraphics.fill(inventoryGuiLeft, inventoryGuiTop, inventoryGuiLeft + 176, inventoryGuiTop + 166, 0xFF8B8B8B);
        
        guiGraphics.blit(PLAYER_ATTRIBUTE_MENU_TEXTURE, inventoryGuiLeft, inventoryGuiTop, 0, 0, 176, 166);

        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        int startX = inventoryGuiLeft + 20;
        int startY = inventoryGuiTop + 20;
        int spacing = 18;

        int health = (int) Math.ceil(player.getHealth());
        int hunger = player.getFoodData().getFoodLevel();
        int armor = player.getArmorValue();

        renderAttribute(guiGraphics, startX, startY, HEALTH_ICON, health);
        renderAttribute(guiGraphics, startX, startY + spacing, HUNGER_ICON, hunger);
        renderAttribute(guiGraphics, startX, startY + spacing * 2, ARMOR_ICON, armor);
    }

    private static void renderAttribute(GuiGraphics guiGraphics, int x, int y, ResourceLocation icon, int value) {
        guiGraphics.blitSprite(icon, x, y, 16, 16);
        guiGraphics.drawString(Minecraft.getInstance().font, String.valueOf(value), x + 20, y + 5, 0xFFFFFF);
    }
}