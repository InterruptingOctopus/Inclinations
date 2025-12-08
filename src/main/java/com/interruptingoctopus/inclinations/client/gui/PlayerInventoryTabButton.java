package com.interruptingoctopus.inclinations.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * A custom button class for representing a tab that opens the player's inventory screen.
 * This button is designed to be placed on other GUI screens, allowing quick navigation
 * to the inventory.
 */
@OnlyIn(Dist.CLIENT)
public class PlayerInventoryTabButton extends Button {
    /**
     * The ItemStack used as the icon for this tab button.
     */
    private final ItemStack iconItem;

    /**
     * Constructs a new PlayerInventoryTabButton.
     *
     * @param x        The x-coordinate of the button.
     * @param y        The y-coordinate of the button.
     * @param iconItem The ItemStack to display as the button's icon.
     * @param onPress  The action to perform when the button is pressed.
     */
    public PlayerInventoryTabButton(int x, int y, ItemStack iconItem, OnPress onPress) {
        super(x, y, 28, 32, Component.empty(), onPress, DEFAULT_NARRATION);
        this.iconItem = iconItem;
    }

    /**
     * Renders the button widget.
     * If the tab is not selected, it renders the unselected tab background and icon.
     * Selected tabs are rendered separately by {@link #renderActiveTab(GuiGraphics)}.
     *
     * @param guiGraphics The GuiGraphics instance for rendering.
     * @param mouseX The x-coordinate of the mouse.
     * @param mouseY The y-coordinate of the mouse.
     * @param partialTick The partial tick time.
     */
    @Override
    public void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // Only render if the tab is NOT selected. The active tab will be rendered separately.
        if (!isSelected()) {
            renderTabBackgroundAndIcon(guiGraphics);
        }
    }

    /**
     * Renders the active state of this tab button.
     * This method is intended to be called after other GUI elements have been rendered
     * to ensure the active tab appears on top.
     *
     * @param guiGraphics The GuiGraphics instance for rendering.
     */
    public void renderActiveTab(GuiGraphics guiGraphics) {
        // This method is called specifically to render the active tab on top.
        renderTabBackgroundAndIcon(guiGraphics);
    }

    /**
     * Renders the background and icon of the tab button.
     * The background sprite changes based on whether the tab is selected or not.
     *
     * @param guiGraphics The GuiGraphics instance for rendering.
     */
    private void renderTabBackgroundAndIcon(GuiGraphics guiGraphics) {
        ResourceLocation tabSprite = isSelected()
                ? ResourceLocation.fromNamespaceAndPath("minecraft", "container/creative_inventory/tab_top_selected_1")
                : ResourceLocation.fromNamespaceAndPath("minecraft", "container/creative_inventory/tab_top_unselected_1");

        // Using RenderPipelines.GUI_TEXTURED
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, tabSprite, this.getX(), this.getY(), this.width, this.height);
        guiGraphics.renderItem(this.iconItem, this.getX() + 6, this.getY() + 8);
    }

    /**
     * Checks if this tab button is currently selected.
     * A tab is considered selected if the current screen is an {@link InventoryScreen}.
     *
     * @return True if the tab is selected, false otherwise.
     */
    public boolean isSelected() {
        return Minecraft.getInstance().screen instanceof InventoryScreen;
    }
}
