package com.interruptingoctopus.inclinations.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlayerAttributeMenuTabButton extends Button {
    private final ResourceLocation iconSprite;

    public PlayerAttributeMenuTabButton(int x, int y, ResourceLocation iconSprite, OnPress onPress) {
        super(x, y, 28, 32, Component.empty(), onPress, DEFAULT_NARRATION);
        this.iconSprite = iconSprite;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        ResourceLocation tabSprite = isSelected()
                ? ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_top_selected_1")
                : ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_top_unselected_1");

        guiGraphics.blitSprite(tabSprite, this.getX(), this.getY(), this.width, this.height);
        guiGraphics.blitSprite(this.iconSprite, this.getX() + 6, this.getY() + 8, 16, 16);
    }

    public boolean isSelected() {
        return Minecraft.getInstance().screen instanceof PlayerAttributeMenuScreen;
    }
}
