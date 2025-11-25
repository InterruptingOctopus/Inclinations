package com.interruptingoctopus.inclinations.events;

import com.interruptingoctopus.inclinations.client.gui.ModCustomOverlay;
import com.interruptingoctopus.inclinations.client.gui.PlayerAttributeMenuScreen;
import com.interruptingoctopus.inclinations.client.gui.PlayerAttributeMenuTabButton;
import com.interruptingoctopus.inclinations.client.gui.PlayerInventoryTabButton;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

public class ModEvents {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final ModCustomOverlay CUSTOM_HUNGER_OVERLAY = new ModCustomOverlay();

    // Fields to hold references to our custom tab buttons
    private static PlayerInventoryTabButton inventoryTabButton;
    private static PlayerAttributeMenuTabButton attributeTabButton;

    @SuppressWarnings("unused")
    public static void registerClientEvents(final FMLClientSetupEvent event) {
        LOGGER.info("Registering client events for Inclinations mod.");
        // Register RenderGuiLayerEvent listeners for HUD modifications
        NeoForge.EVENT_BUS.addListener(ModEvents::onRenderGuiPre);
        NeoForge.EVENT_BUS.addListener(ModEvents::onRenderGuiPost);
        // Register ScreenEvent.Render.Pre listener for inventory screen modifications
        NeoForge.EVENT_BUS.addListener(ModEvents::onScreenRenderPre);
        // Register ScreenEvent.Render.Post listener for rendering active tabs on top
        NeoForge.EVENT_BUS.addListener(ModEvents::onScreenRenderPost);
        // Register ScreenEvent.Init.Post listener for adding custom buttons to screens
        NeoForge.EVENT_BUS.addListener(ModEvents::onScreenInitPost);
    }

    private static void onRenderGuiPre(RenderGuiLayerEvent.Pre event) {
        // Cancel vanilla armor and food level rendering
        if (event.getName().equals(VanillaGuiLayers.ARMOR_LEVEL) ||
                event.getName().equals(VanillaGuiLayers.FOOD_LEVEL)) {
            event.setCanceled(true);
        }
    }

    private static void onRenderGuiPost(RenderGuiLayerEvent.Post event) {
        // Only render our custom hunger overlay after the EFFECTS layer to ensure it's on top of hearts.
        if (event.getName().equals(VanillaGuiLayers.EFFECTS)) {
            Minecraft minecraft = Minecraft.getInstance();
            Player player = minecraft.player;
            GuiGraphics graphics = event.getGuiGraphics();

            if (player == null || minecraft.options.hideGui || minecraft.gameMode == null || !minecraft.gameMode.canHurtPlayer()) {
                return;
            }
            // Render our custom hunger overlay
            CUSTOM_HUNGER_OVERLAY.render(graphics, graphics.guiWidth(), graphics.guiHeight());
        }
    }

    @SuppressWarnings("unused")
    private static void onScreenRenderPre(ScreenEvent.Render.Pre event) {
    }

    private static void onScreenRenderPost(ScreenEvent.Render.Post event) {
        // After everything else is rendered, find the active tab and render it on top.
        for (Renderable renderable : event.getScreen().renderables) {
            if (renderable instanceof PlayerInventoryTabButton tabButton && tabButton.isSelected()) {
                tabButton.renderActiveTab(event.getGuiGraphics());
            } else if (renderable instanceof PlayerAttributeMenuTabButton tabButton && tabButton.isSelected()) {
                tabButton.renderActiveTab(event.getGuiGraphics());
            }
        }
    }

    private static void onScreenInitPost(ScreenEvent.Init.Post event) {
        int guiLeft = 0;
        int guiTop = 0;
        boolean shouldAddButtons = false;

        if (event.getScreen() instanceof InventoryScreen inventoryScreen) {
            LOGGER.info("ScreenEvent.Init.Post fired for InventoryScreen.");
            guiLeft = inventoryScreen.getGuiLeft();
            guiTop = inventoryScreen.getGuiTop();
            shouldAddButtons = true;
        } else if (event.getScreen() instanceof PlayerAttributeMenuScreen attributeScreen) {
            LOGGER.info("ScreenEvent.Init.Post fired for PlayerAttributeMenuScreen.");
            guiLeft = attributeScreen.getGuiLeft();
            guiTop = attributeScreen.getGuiTop();
            shouldAddButtons = true;
        }

        if (shouldAddButtons) {
            inventoryTabButton = createPlayerInventoryTabButton(guiLeft, guiTop);
            event.addListener(inventoryTabButton);

            attributeTabButton = createPlayerAttributeMenuTabButton(guiLeft, guiTop);
            event.addListener(attributeTabButton);
        }
    }

    private static PlayerInventoryTabButton createPlayerInventoryTabButton(int guiLeft, int guiTop) {
        int tabX = guiLeft + 28;
        int tabY = guiTop - 30;

        return new PlayerInventoryTabButton(
                tabX, tabY,
                new ItemStack(Items.CRAFTING_TABLE),
                button -> {
                    Minecraft minecraft = Minecraft.getInstance();
                    if (!(minecraft.screen instanceof InventoryScreen) && minecraft.player != null) {
                        minecraft.setScreen(new InventoryScreen(minecraft.player));
                        LOGGER.info("Player Inventory Tab clicked! Opening Inventory Screen.");
                    }
                }
        );
    }

    private static PlayerAttributeMenuTabButton createPlayerAttributeMenuTabButton(int guiLeft, int guiTop) {
        int tabX = guiLeft + 56;
        int tabY = guiTop - 30;

        return new PlayerAttributeMenuTabButton(
                tabX, tabY,
                new ItemStack(Items.IRON_SWORD),
                button -> {
                    Minecraft minecraft = Minecraft.getInstance();
                    if (!(minecraft.screen instanceof PlayerAttributeMenuScreen) && minecraft.player != null) {
                        minecraft.setScreen(new PlayerAttributeMenuScreen());
                        LOGGER.info("Player Attribute Menu Tab clicked! Opening Player Attribute Menu Screen.");
                    }
                }
        );
    }
}
