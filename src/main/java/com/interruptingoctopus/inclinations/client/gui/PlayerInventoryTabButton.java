package com.interruptingoctopus.inclinations.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlayerInventoryTabButton extends Button {
    private final ItemStack iconItem;

    public PlayerInventoryTabButton(int x, int y, ItemStack iconItem, OnPress onPress) {
        super(x, y, 28, 32, Component.empty(), onPress, DEFAULT_NARRATION);
        this.iconItem = iconItem;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        ResourceLocation tabSprite = isSelected()
                ? ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_top_selected_1")
                : ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_top_unselected_1");

        guiGraphics.blitSprite(tabSprite, this.getX(), this.getY(), this.width, this.height);
        guiGraphics.renderItem(this.iconItem, this.getX() + 6, this.getY() + 8);
    }

    public boolean isSelected() {
        return Minecraft.getInstance().screen instanceof InventoryScreen;
    }
}
