package com.interruptingoctopus.inclinations.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.events.ModEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlayerAttributeMenuScreen extends Screen {
    private static final ResourceLocation PLAYER_ATTRIBUTE_MENU_TEXTURE = 
            ResourceLocation.fromNamespaceAndPath(Inclinations.MOD_ID, "textures/gui/container/player_attribute_menu.png");
    private static final ResourceLocation HEALTH_ICON = ResourceLocation.withDefaultNamespace("hud/heart_full");
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

        int tabY = this.guiTop - 30;
        
        this.addRenderableWidget(new PlayerInventoryTabButton(
                this.guiLeft + 28, tabY,
                ModEvents.PLAYER_INVENTORY_TAB_ID,
                new ItemStack(Items.CRAFTING_TABLE),
                button -> this.minecraft.setScreen(new InventoryScreen(this.minecraft.player))
        ));

        this.addRenderableWidget(new PlayerAttributeMenuTabButton(
                this.guiLeft + 56, tabY,
                ModEvents.PLAYER_ATTRIBUTE_TAB_ID,
                PLAYER_ATTRIBUTE_MENU_TEXTURE,
                button -> {}
        ));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        
        guiGraphics.blit(PLAYER_ATTRIBUTE_MENU_TEXTURE, this.guiLeft, this.guiTop, 0, 0, 176, 166);

        Player player = this.minecraft.player;
        if (player != null) {
            int startX = this.guiLeft + 20;
            int startY = this.guiTop + 20;
            int spacing = 18;

            int health = (int) Math.ceil(player.getHealth());
            int hunger = player.getFoodData().getFoodLevel();
            int armor = player.getArmorValue();

            renderAttribute(guiGraphics, startX, startY, HEALTH_ICON, health);
            renderAttribute(guiGraphics, startX, startY + spacing, HUNGER_ICON, hunger);
            renderAttribute(guiGraphics, startX, startY + spacing * 2, ARMOR_ICON, armor);
        }
    }

    private void renderAttribute(GuiGraphics guiGraphics, int x, int y, ResourceLocation icon, int value) {
        guiGraphics.blitSprite(icon, x, y, 16, 16);
        guiGraphics.drawString(this.font, String.valueOf(value), x + 20, y + 5, 0xFFFFFF);
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(new InventoryScreen(this.minecraft.player));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
