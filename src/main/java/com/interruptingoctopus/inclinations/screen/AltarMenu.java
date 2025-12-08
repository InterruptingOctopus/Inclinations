package com.interruptingoctopus.inclinations.screen;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * The container menu for the Altar block.
 * This class defines the slots and interaction logic for the Altar's GUI.
 */
public class AltarMenu extends AbstractContainerMenu {

    /**
     * Constructor for AltarMenu.
     *
     * @param pContainerId     The unique ID for this container.
     * @param pPlayerInventory The player's inventory.
     */
    public AltarMenu(int pContainerId, Inventory pPlayerInventory) {
        super(ModMenuTypes.ALTAR_MENU.get(), pContainerId);
        // Add slots for the altar and player inventory here
        // Example: this.addSlot(new Slot(blockEntity, 0, 80, 35));
        // Layout player inventory
        layoutPlayerInventory(pPlayerInventory);
    }

    /**
     * Constructor for AltarMenu, used when opening from a FriendlyByteBuf.
     *
     * @param pContainerId     The unique ID for this container.
     * @param pPlayerInventory The player's inventory.
     * @param pBuffer          The FriendlyByteBuf containing additional data (e.g., block entity position).
     */
    public AltarMenu(int pContainerId, Inventory pPlayerInventory, FriendlyByteBuf pBuffer) {
        this(pContainerId, pPlayerInventory);
        // Handle additional data from buffer if needed
    }

    /**
     * Determines if the player can interact with this container.
     * @param pPlayer The player interacting.
     * @return True if the player can interact, false otherwise.
     */
    @Override
    public boolean stillValid(net.minecraft.world.entity.player.@NotNull Player pPlayer) {
        // Implement logic to check if the player is still close enough to the altar
        return true;
    }

    /**
     * Handles shift-clicking items between inventory and altar.
     * @param pPlayer The player performing the shift-click.
     * @param pIndex The index of the slot that was clicked.
     * @return The ItemStack remaining after the transfer.
     */
    @Override
    public @NotNull ItemStack quickMoveStack(net.minecraft.world.entity.player.@NotNull Player pPlayer, int pIndex) {
        // Implement quick move logic
        return ItemStack.EMPTY;
    }

    /**
     * Lays out the player's inventory and hotbar slots.
     *
     * @param pPlayerInventory The player's inventory.
     */
    private void layoutPlayerInventory(Inventory pPlayerInventory) {
        // Player inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(pPlayerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // Player hotbar
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(pPlayerInventory, col, 8 + col * 18, 142));
        }
    }
}
