package com.interruptingoctopus.inclinations.block.entity;

import com.interruptingoctopus.inclinations.screen.AltarMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class AltarBlockEntity extends BlockEntity implements MenuProvider, IItemHandler {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    public AltarBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ALTAR.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(@Nonnull CompoundTag pTag, @Nonnull HolderLookup.Provider pRegistries) {
        pTag.put("inventory", itemHandler.serializeNBT(pRegistries));
        super.saveAdditional(pTag, pRegistries);
    }

    @Override
    public void loadAdditional(@Nonnull CompoundTag pTag, @Nonnull HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        itemHandler.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
    }

    @Nonnull
    @Override
    public Component getDisplayName() {
        return Component.translatable("container.inclinations.altar");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @Nonnull Inventory pPlayerInventory, @Nonnull Player pPlayer) {
        return new AltarMenu(pContainerId, pPlayerInventory, this);
    }

    @SuppressWarnings("unused")
    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, AltarBlockEntity pBlockEntity) {
        if (pLevel.isClientSide()) {
            return;
        }

        ItemStack enchantedItem = pBlockEntity.itemHandler.getStackInSlot(0);
        ItemStack book = pBlockEntity.itemHandler.getStackInSlot(1);
        ItemStack output = pBlockEntity.itemHandler.getStackInSlot(2);

        if (output.isEmpty() && !enchantedItem.isEmpty() && book.is(Items.BOOK) && enchantedItem.has(DataComponents.ENCHANTMENTS)) {
            ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
            enchantedBook.set(DataComponents.STORED_ENCHANTMENTS, enchantedItem.get(DataComponents.ENCHANTMENTS));

            pBlockEntity.itemHandler.setStackInSlot(2, enchantedBook);
            pBlockEntity.itemHandler.extractItem(0, 1, false);
            pBlockEntity.itemHandler.extractItem(1, 1, false);
        }
    }

    // IItemHandler methods
    @Override
    public int getSlots() {
        return itemHandler.getSlots();
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return itemHandler.getStackInSlot(slot);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        return itemHandler.insertItem(slot, stack, simulate);
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return itemHandler.extractItem(slot, amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        return itemHandler.getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return itemHandler.isItemValid(slot, stack);
    }
}