package com.interruptingoctopus.inclinations.client.gui;

import com.interruptingoctopus.inclinations.Inclinations;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * A custom GUI screen for displaying player attributes such as health, hunger, and armor.
 * This screen is accessible from the inventory screen via a custom tab button.
 */
@OnlyIn(Dist.CLIENT)
public class PlayerAttributeMenuScreen extends Screen {
    /**
     * The resource location for the background texture of the player attribute menu.
     */
    private static final ResourceLocation PLAYER_ATTRIBUTE_MENU_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Inclinations.MOD_ID, "textures/gui/container/player_attribute_menu.png");
    /**
     * The resource location for the health icon.
     */
    private static final ResourceLocation HEALTH_ICON = ResourceLocation.fromNamespaceAndPath("minecraft", "hud/heart/full");
    /**
     * The resource location for the hunger icon.
     */
    private static final ResourceLocation HUNGER_ICON = ResourceLocation.fromNamespaceAndPath("minecraft", "hud/food_full");
    /**
     * The resource location for the armor icon.
     */
    private static final ResourceLocation ARMOR_ICON = ResourceLocation.fromNamespaceAndPath("minecraft", "hud/armor_full");

    /**
     * The x-coordinate of the GUI's left edge.
     */
    private int guiLeft;
    /**
     * The y-coordinate of the GUI's top edge.
     */
    private int guiTop;

    /**
     * Constructs a new PlayerAttributeMenuScreen.
     */
    public PlayerAttributeMenuScreen() {
        super(Component.literal("Player Attributes"));
    }

    /**
     * Initializes the screen, setting up the GUI position.
     */
    @Override
    protected void init() {
        this.guiLeft = (this.width - 176) / 2;
        this.guiTop = (this.height - 166) / 2;
        super.init();
    }

    /**
     * Renders the screen, including the background texture and player attributes.
     *
     * @param guiGraphics The GuiGraphics instance for rendering.
     * @param mouseX      The x-coordinate of the mouse.
     * @param mouseY      The y-coordinate of the mouse.
     * @param partialTick The partial tick time.
     */
    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        // Using RenderPipelines.GUI_TEXTURED and texture dimensions (256, 256)
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, PLAYER_ATTRIBUTE_MENU_TEXTURE, this.guiLeft, this.guiTop, 0, 0, 176, 166, 256, 256);

        if (this.minecraft != null) {
            Player player = this.minecraft.player;
            if (player != null) {
                int startX = this.guiLeft + 20;
                int startY = this.guiTop + 20;
                int spacing = 12; // Reduced spacing for a tighter layout

                int health = (int) Math.ceil(player.getHealth());
                int hunger = player.getFoodData().getFoodLevel();
                int armor = player.getArmorValue();

                renderAttribute(guiGraphics, startX, startY, HEALTH_ICON, health);
                renderAttribute(guiGraphics, startX, startY + spacing, HUNGER_ICON, hunger);
                renderAttribute(guiGraphics, startX, startY + spacing * 2, ARMOR_ICON, armor);
            }
        }
    }

    /**
     * Renders a single player attribute with its icon and value.
     *
     * @param guiGraphics The GuiGraphics instance for rendering.
     * @param x           The x-coordinate for rendering the attribute.
     * @param y           The y-coordinate for rendering the attribute.
     * @param icon        The resource location of the attribute's icon.
     * @param value       The integer value of the attribute.
     */
    private void renderAttribute(GuiGraphics guiGraphics, int x, int y, ResourceLocation icon, int value) {
        // Using RenderPipelines.GUI_TEXTURED
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, icon, x, y, 9, 9);
        guiGraphics.drawString(this.font, String.valueOf(value), x + 12, y + 1, 0xFFFFFF);
    }

    /**
     * Called when the screen is closed.
     * Navigates back to the player's inventory screen.
     */
    @Override
    public void onClose() {
        if (this.minecraft != null && this.minecraft.player != null) {
            this.minecraft.setScreen(new InventoryScreen(this.minecraft.player));
        }
    }

    /**
     * Indicates whether this screen is a pause screen.
     * @return False, as this is not a pause screen.
     */
    @Override
    public boolean isPauseScreen() {
        return false;
    }

    /**
     * Gets the x-coordinate of the GUI's left edge.
     * @return The guiLeft value.
     */
    public int getGuiLeft() {
        return guiLeft;
    }

    /**
     * Gets the y-coordinate of the GUI's top edge.
     * @return The guiTop value.
     */
    public int getGuiTop() {
        return guiTop;
    }
}
