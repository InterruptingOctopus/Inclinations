package com.interruptingoctopus.inclinations.screen;

import com.interruptingoctopus.inclinations.block.ModBlocks;
import com.interruptingoctopus.inclinations.block.entity.AltarBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class AltarMenu extends AbstractContainerMenu {
    public final AltarBlockEntity blockEntity;
    private final Level level;

    @SuppressWarnings("resource")
    public AltarMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public AltarMenu(int pContainerId, Inventory inv, BlockEntity entity) {
        super(ModMenuTypes.ALTAR_MENU.get(), pContainerId);
        checkContainerSize(inv, 3);
        this.blockEntity = (AltarBlockEntity) entity;
        this.level = inv.player.level();

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        if (this.blockEntity != null) {
            IItemHandler handler = this.blockEntity; // Direct cast as AltarBlockEntity now implements IItemHandler
            this.addSlot(new SlotItemHandler(handler, 0, 26, 47));
            this.addSlot(new SlotItemHandler(handler, 1, 76, 47));
            this.addSlot(new SlotItemHandler(handler, 2, 134, 47));
        }
    }

    @NotNull
    @Override
    public ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocks.ALTAR.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
