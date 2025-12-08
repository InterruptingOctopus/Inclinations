package com.interruptingoctopus.inclinations.events;

import com.interruptingoctopus.inclinations.client.gui.PlayerAttributeMenuScreen;
import com.interruptingoctopus.inclinations.client.gui.PlayerAttributeMenuTabButton;
import com.interruptingoctopus.inclinations.client.gui.PlayerInventoryTabButton;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

/**
 * Handles various client-side events for the Inclinations mod,
 * such as GUI rendering and screen initialization.
 */
public class ModEvents {

    /**
     * Logger for ModEvents.
     */
    private static final Logger LOGGER = LogUtils.getLogger();
    // The ModCustomOverlay is now handled entirely by its own @EventBusSubscriber for the RegisterGuiLayersEvent.
    // An instance is no longer needed here.

    /**
     * Registers client-side event listeners.
     *
     * @param event The FMLClientSetupEvent.
     */
    @SuppressWarnings("unused")
    public static void registerClientEvents(final FMLClientSetupEvent event) {
        LOGGER.info("Registering client events for Inclinations mod.");
        // Register RenderGuiLayerEvent listeners for HUD modifications
        NeoForge.EVENT_BUS.addListener(ModEvents::onRenderGuiPre);
        // The onRenderGuiPost listener is no longer needed as the overlay is registered via RegisterGuiLayersEvent.
        // Register ScreenEvent.Render.Pre listener for inventory screen modifications
        NeoForge.EVENT_BUS.addListener(ModEvents::onScreenRenderPre);
        // Register ScreenEvent.Render.Post listener for rendering active tabs on top
        NeoForge.EVENT_BUS.addListener(ModEvents::onScreenRenderPost);
        // Register ScreenEvent.Init.Post listener for adding custom buttons to screens
        NeoForge.EVENT_BUS.addListener(ModEvents::onScreenInitPost);
    }

    /**
     * Event handler for pre-rendering GUI layers.
     * Used to cancel vanilla armor and food level rendering.
     * @param event The RenderGuiLayerEvent.Pre event.
     */
    private static void onRenderGuiPre(RenderGuiLayerEvent.Pre event) {
        // Cancel vanilla armor and food level rendering.
        // The custom hunger overlay is rendered automatically via RegisterGuiLayersEvent.
        if (event.getName().equals(VanillaGuiLayers.ARMOR_LEVEL) ||
                event.getName().equals(VanillaGuiLayers.FOOD_LEVEL)) {
            event.setCanceled(true);
        }
    }

    // The onRenderGuiPost method has been removed as it is no longer necessary.
    // The custom hunger overlay is now rendered automatically by the system
    // because it was registered in ModCustomOverlay using the RegisterGuiLayersEvent.

    /**
     * Event handler for pre-rendering screens.
     * @param event The ScreenEvent.Render.Pre event.
     */
    @SuppressWarnings("unused")
    private static void onScreenRenderPre(ScreenEvent.Render.Pre event) {
        // Currently empty, but can be used for future pre-render screen modifications.
    }

    /**
     * Event handler for post-rendering screens.
     * Used to render active tab buttons on top of other GUI elements.
     * @param event The ScreenEvent.Render.Post event.
     */
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

    /**
     * Event handler for post-initialization of screens.
     * Used to add custom tab buttons to inventory and attribute screens.
     * @param event The ScreenEvent.Init.Post event.
     */
    private static void onScreenInitPost(ScreenEvent.Init.Post event) {
        int guiLeft = 0;
        int guiTop = 0;
        boolean shouldAddButtons = false;

        if (event.getScreen() instanceof InventoryScreen inventoryScreen) {
            guiLeft = inventoryScreen.getGuiLeft();
            guiTop = inventoryScreen.getGuiTop();
            shouldAddButtons = true;
        } else if (event.getScreen() instanceof PlayerAttributeMenuScreen attributeScreen) {
            guiLeft = attributeScreen.getGuiLeft();
            guiTop = attributeScreen.getGuiTop();
            shouldAddButtons = true;
        }

        if (shouldAddButtons) {
            PlayerInventoryTabButton inventoryTabButton = createPlayerInventoryTabButton(guiLeft, guiTop);
            event.addListener(inventoryTabButton);

            PlayerAttributeMenuTabButton attributeTabButton = createPlayerAttributeMenuTabButton(guiLeft, guiTop);
            event.addListener(attributeTabButton);
        }
    }

    /**
     * Creates a new PlayerInventoryTabButton instance.
     * @param guiLeft The x-coordinate of the GUI's left edge.
     * @param guiTop The y-coordinate of the GUI's top edge.
     * @return A new PlayerInventoryTabButton.
     */
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

    /**
     * Creates a new PlayerAttributeMenuTabButton instance.
     * @param guiLeft The x-coordinate of the GUI's left edge.
     * @param guiTop The y-coordinate of the GUI's top edge.
     * @return A new PlayerAttributeMenuTabButton.
     */
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
