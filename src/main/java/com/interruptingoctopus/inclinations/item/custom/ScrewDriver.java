package com.interruptingoctopus.inclinations.item.custom;

import com.google.common.collect.Iterables;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.item.component.CustomData;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ScrewDriver extends Item {
    // Blacklist of properties the screwdriver should not be able to change.
    private static final Set<String> FORBIDDEN_PROPERTIES = Set.of("powered", "waterlogged");
    private static final String SELECTED_PROPERTY_KEY = "selected_property";

    public ScrewDriver(Properties pProperties) {
        super(pProperties);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        Level level = pContext.getLevel();

        if (player != null && !level.isClientSide() && player.isCreative()) {
            BlockPos pos = pContext.getClickedPos();
            BlockState state = level.getBlockState(pos);
            ItemStack stack = pContext.getItemInHand();

            if (player.isShiftKeyDown()) {
                handlePropertyCycle(player, state, stack);
            } else {
                handleValueCycle(player, state, pos, level, stack);
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    private void handlePropertyCycle(Player pPlayer, BlockState pState, ItemStack pStack) {
        Collection<Property<?>> properties = getAllowedProperties(pState);
        if (properties.isEmpty()) {
            pPlayer.sendSystemMessage(Component.literal("Block has no allowed properties."));
            return;
        }

        CustomData customData = pStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag tag = customData.copyTag();
        String currentPropName = tag.getString(SELECTED_PROPERTY_KEY);
        Property<?> nextProp = getNextProperty(properties, currentPropName);

        if (nextProp != null) {
            tag.putString(SELECTED_PROPERTY_KEY, nextProp.getName());
            pStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
            pPlayer.sendSystemMessage(Component.literal("Selected property: " + nextProp.getName()));
        }
    }

    private void handleValueCycle(Player pPlayer, BlockState pState, BlockPos pPos, Level pLevel, ItemStack pStack) {
        Collection<Property<?>> properties = getAllowedProperties(pState);
        if (properties.isEmpty()) {
            pPlayer.sendSystemMessage(Component.literal("Block has no allowed properties."));
            return;
        }

        CustomData customData = pStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag tag = customData.copyTag();
        String currentPropName = tag.getString(SELECTED_PROPERTY_KEY);

        Optional<Property<?>> selectedPropOpt = properties.stream()
                .filter(p -> p.getName().equals(currentPropName))
                .findFirst();

        Property<?> propertyToCycle = selectedPropOpt.orElse(properties.iterator().next());
        if (selectedPropOpt.isEmpty()) {
            // If no property was selected, or the selected one is no longer allowed, select the first one
            tag.putString(SELECTED_PROPERTY_KEY, propertyToCycle.getName());
            pStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
            pPlayer.sendSystemMessage(Component.literal("Selected property: " + propertyToCycle.getName()));
        }

        BlockState newState = cycleProperty(pState, propertyToCycle);
        pLevel.setBlock(pPos, newState, 2);
        pPlayer.sendSystemMessage(Component.literal("Set " + propertyToCycle.getName() + " to " + newState.getValue(propertyToCycle)));
    }

    private <T extends Comparable<T>> BlockState cycleProperty(BlockState pState, Property<T> pProperty) {
        return pState.setValue(pProperty, getNextValue(pProperty.getPossibleValues(), pState.getValue(pProperty)));
    }

    private <T> T getNextValue(Collection<T> pValues, T pCurrentValue) {
        return Iterables.get(pValues, (Iterables.indexOf(pValues, v -> Objects.equals(v, pCurrentValue)) + 1) % pValues.size());
    }

    private Property<?> getNextProperty(Collection<Property<?>> pProperties, String pCurrentPropName) {
        if (pCurrentPropName.isEmpty()) {
            return pProperties.iterator().next();
        }
        return Iterables.get(pProperties, (Iterables.indexOf(pProperties, p -> p != null && Objects.equals(p.getName(), pCurrentPropName)) + 1) % pProperties.size());
    }

    /**
     * Gets a collection of properties from the block state, excluding any that are in the FORBIDDEN_PROPERTIES set.
     */
    private Collection<Property<?>> getAllowedProperties(BlockState pState) {
        return pState.getProperties().stream()
                .filter(p -> !FORBIDDEN_PROPERTIES.contains(p.getName()))
                .collect(Collectors.toList());
    }
}
