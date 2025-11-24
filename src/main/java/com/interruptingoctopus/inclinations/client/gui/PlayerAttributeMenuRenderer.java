package com.interruptingoctopus.inclinations.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import com.interruptingoctopus.inclinations.Inclinations;

public class PlayerAttributeMenuRenderer {
    private static final ResourceLocation SURVIVAL_INVENTORY_TEXTURE = 
            ResourceLocation.fromNamespaceAndPath(Inclinations.MOD_ID, "textures/gui/container/player_attribute_menu");

    public static void render(GuiGraphics guiGraphics, int inventoryGuiLeft, int inventoryGuiTop) {
        guiGraphics.blit(SURVIVAL_INVENTORY_TEXTURE, inventoryGuiLeft, inventoryGuiTop, 0, 0, 176, 166, 256, 256);
    }
}