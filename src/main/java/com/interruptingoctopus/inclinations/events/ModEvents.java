package com.interruptingoctopus.inclinations.events;

import com.interruptingoctopus.inclinations.Inclinations;
import com.interruptingoctopus.inclinations.client.gui.CraftingMenuTabButton;
import com.interruptingoctopus.inclinations.client.gui.ModCustomOverlay;
import com.interruptingoctopus.inclinations.client.gui.PlayerAttributeMenuRenderer;
import com.interruptingoctopus.inclinations.client.gui.PlayerAttributeMenuTabButton;
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

    // Define our own ResourceLocation constant for the armor sprite
    private static final ResourceLocation ARMOR_FULL_SPRITE = ResourceLocation.withDefaultNamespace("hud/armor_full");
    // Define a ResourceLocation for our new custom inventory tab
    public static final ResourceLocation ARMOR_ATTRIBUTE_TAB_ID = ResourceLocation.fromNamespaceAndPath(Inclinations.MOD_ID, "armor_attribute_tab");

    // Crafting menu tab
    public static final ResourceLocation CRAFTING_MENU_TAB_ID = ResourceLocation.fromNamespaceAndPath(Inclinations.MOD_ID, "crafting_menu_tab");

    // Survival inventory tab
    public static final ResourceLocation SURVIVAL_INVENTORY_TAB_ID = ResourceLocation.fromNamespaceAndPath(Inclinations.MOD_ID, "survival_inventory_tab");

    // Static field to track the currently active custom tab
    public static ResourceLocation currentActiveTab = null;

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

    private static void onScreenRenderPost(ScreenEvent.Render.Post event) {
        if (event.getScreen() instanceof InventoryScreen inventoryScreen) {
            GuiGraphics guiGraphics = event.getGuiGraphics();
            
            if (SURVIVAL_INVENTORY_TAB_ID.equals(currentActiveTab)) {
                PlayerAttributeMenuRenderer.render(guiGraphics,
                        inventoryScreen.getGuiLeft(), inventoryScreen.getGuiTop());
            }
        }
    }

    private static void onScreenInitPost(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof InventoryScreen inventoryScreen) {
            LOGGER.info("ScreenEvent.Init.Post fired for InventoryScreen.");

            // Set the crafting tab as the default active tab when the inventory screen is opened
            currentActiveTab = CRAFTING_MENU_TAB_ID;

            // Create and add our custom tab buttons
            event.addListener(createCraftingTabButton(inventoryScreen));
            event.addListener(createArmorTabButton(inventoryScreen));
            event.addListener(createSurvivalInventoryTabButton(inventoryScreen));
        }
    }

    private static PlayerAttributeMenuTabButton createArmorTabButton(InventoryScreen inventoryScreen) {
        int tabX = inventoryScreen.getGuiLeft() + 56;
        int tabY = inventoryScreen.getGuiTop() - 30;

        return new PlayerAttributeMenuTabButton(
                tabX, tabY,
                ARMOR_ATTRIBUTE_TAB_ID,
                ARMOR_FULL_SPRITE,
                button -> {
                    currentActiveTab = ARMOR_ATTRIBUTE_TAB_ID;
                    LOGGER.info("Armor Attribute Tab clicked! Setting active tab to: {}", ARMOR_ATTRIBUTE_TAB_ID);
                }
        );
    }

    private static CraftingMenuTabButton createCraftingTabButton(InventoryScreen inventoryScreen) {
        int tabX = inventoryScreen.getGuiLeft() + 28;
        int tabY = inventoryScreen.getGuiTop() - 30;

        return new CraftingMenuTabButton(
                tabX, tabY,
                CRAFTING_MENU_TAB_ID,
                new ItemStack(Items.CRAFTING_TABLE),
                button -> {
                    currentActiveTab = CRAFTING_MENU_TAB_ID;
                    LOGGER.info("Crafting Menu Tab clicked! Setting active tab to: {}", CRAFTING_MENU_TAB_ID);
                }
        );
    }

    private static PlayerAttributeMenuTabButton createSurvivalInventoryTabButton(InventoryScreen inventoryScreen) {
        int tabX = inventoryScreen.getGuiLeft() + 84;
        int tabY = inventoryScreen.getGuiTop() - 30;

        return new PlayerAttributeMenuTabButton(
                tabX, tabY,
                SURVIVAL_INVENTORY_TAB_ID,
                ResourceLocation.withDefaultNamespace("hud/armor_full"),
                button -> {
                    currentActiveTab = SURVIVAL_INVENTORY_TAB_ID;
                    LOGGER.info("Survival Inventory Tab clicked! Setting active tab to: {}", SURVIVAL_INVENTORY_TAB_ID);
                }
        );
    }
}