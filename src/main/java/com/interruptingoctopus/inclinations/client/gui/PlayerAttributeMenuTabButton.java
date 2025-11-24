package com.interruptingoctopus.inclinations.client.gui;

import com.interruptingoctopus.inclinations.events.ModEvents;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlayerAttributeMenuTabButton extends Button {
    private final ResourceLocation tabId;
    private final ResourceLocation iconSprite;

    public PlayerAttributeMenuTabButton(int x, int y, ResourceLocation tabId, ResourceLocation iconSprite, OnPress onPress) {
        super(x, y, 28, 32, Component.empty(), onPress, DEFAULT_NARRATION);
        this.tabId = tabId;
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
        return this.tabId.equals(ModEvents.currentActiveTab);
    }
}
