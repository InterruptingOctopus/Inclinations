package com.interruptingoctopus.inclinations.client.gui;

import com.interruptingoctopus.inclinations.Inclinations;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class PlayerAttributeMenuScreen extends Screen {
    private static final ResourceLocation PLAYER_ATTRIBUTE_MENU_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Inclinations.MOD_ID, "textures/gui/container/player_attribute_menu.png");
    private static final ResourceLocation HEALTH_ICON = ResourceLocation.withDefaultNamespace("hud/heart/full");
    private static final ResourceLocation HUNGER_ICON = ResourceLocation.withDefaultNamespace("hud/food_full");
    private static final ResourceLocation ARMOR_ICON = ResourceLocation.withDefaultNamespace("hud/armor_full");

    private int guiLeft;
    private int guiTop;

    public PlayerAttributeMenuScreen() {
        super(Component.literal("Player Attributes"));
    }

    @Override
    protected void init() {
        this.guiLeft = (this.width - 176) / 2;
        this.guiTop = (this.height - 166) / 2;
        super.init();
    }

    @Override
    public void render(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.blit(PLAYER_ATTRIBUTE_MENU_TEXTURE, this.guiLeft, this.guiTop, 0, 0, 176, 166);

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

    private void renderAttribute(@Nonnull GuiGraphics guiGraphics, int x, int y, @Nonnull ResourceLocation icon, int value) {
        guiGraphics.blitSprite(icon, x, y, 9, 9);
        guiGraphics.drawString(this.font, String.valueOf(value), x + 12, y + 1, 0xFFFFFF);
    }

    @Override
    public void onClose() {
        if (this.minecraft != null && this.minecraft.player != null) {
            this.minecraft.setScreen(new InventoryScreen(this.minecraft.player));
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public int getGuiLeft() {
        return guiLeft;
    }

    public int getGuiTop() {
        return guiTop;
    }
}
