package com.interruptingoctopus.inclinations.events;

import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.client.gui.ModCustomOverlay;
import com.interruptingoctopus.inclinations.client.gui.PlayerAttributeMenuScreen;
import com.interruptingoctopus.inclinations.client.gui.PlayerAttributeMenuTabButton;
import com.interruptingoctopus.inclinations.client.gui.PlayerInventoryTabButton;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ModEvents {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final ModCustomOverlay CUSTOM_HUNGER_OVERLAY = new ModCustomOverlay();

    // Define a ResourceLocation for the player attribute menu tab
    public static final ResourceLocation PLAYER_ATTRIBUTE_TAB_ID = ResourceLocation.fromNamespaceAndPath(Inclinations.MOD_ID, "player_attribute_tab");
    private static final ResourceLocation PLAYER_ATTRIBUTE_ICON = ResourceLocation.fromNamespaceAndPath(Inclinations.MOD_ID, "textures/gui/container/player_attribute_menu.png");

    // Player inventory tab
    public static final ResourceLocation PLAYER_INVENTORY_TAB_ID = ResourceLocation.fromNamespaceAndPath(Inclinations.MOD_ID, "player_inventory_tab");

    public static void registerClientEvents(final FMLClientSetupEvent event) {
        LOGGER.info("Registering client events for Inclinations mod.");
        // Register RenderGuiLayerEvent listeners for HUD modifications
        NeoForge.EVENT_BUS.addListener(ModEvents::onRenderGuiPre);
        NeoForge.EVENT_BUS.addListener(ModEvents::onRenderGuiPost);
        // Register ScreenEvent.Render.Pre listener for inventory screen modifications
        NeoForge.EVENT_BUS.addListener(ModEvents::onScreenRenderPre);
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

    @SuppressWarnings("unused")
    private static void onScreenRenderPost(ScreenEvent.Render.Post event) {
    }

    private static void onScreenInitPost(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof InventoryScreen inventoryScreen) {
            LOGGER.info("ScreenEvent.Init.Post fired for InventoryScreen.");

            event.addListener(createPlayerInventoryTabButton(inventoryScreen));
            event.addListener(createPlayerAttributeMenuTabButton(inventoryScreen));
        }
    }

    private static PlayerInventoryTabButton createPlayerInventoryTabButton(InventoryScreen inventoryScreen) {
        int tabX = inventoryScreen.getGuiLeft() + 28;
        int tabY = inventoryScreen.getGuiTop() - 30;

        return new PlayerInventoryTabButton(
                tabX, tabY,
                PLAYER_INVENTORY_TAB_ID,
                new ItemStack(Items.CRAFTING_TABLE),
                button -> {
                    Minecraft.getInstance().setScreen(new InventoryScreen(Minecraft.getInstance().player));
                    LOGGER.info("Player Inventory Tab clicked! Opening Inventory Screen.");
                }
        );
    }

    private static PlayerAttributeMenuTabButton createPlayerAttributeMenuTabButton(InventoryScreen inventoryScreen) {
        int tabX = inventoryScreen.getGuiLeft() + 56;
        int tabY = inventoryScreen.getGuiTop() - 30;

        return new PlayerAttributeMenuTabButton(
                tabX, tabY,
                PLAYER_ATTRIBUTE_TAB_ID,
                PLAYER_ATTRIBUTE_ICON,
                button -> {
                    Minecraft.getInstance().setScreen(new PlayerAttributeMenuScreen());
                    LOGGER.info("Player Attribute Menu Tab clicked! Opening Player Attribute Menu Screen.");
                }
        );
    }


}