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
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A tool for modifying block states in creative mode.
 * Allows players to cycle through a block's properties and their values.
 */
public class ScrewDriver extends Item {
    /**
     * A set of block state property names that the screwdriver should not be able to change.
     */
    private static final Set<String> FORBIDDEN_PROPERTIES = Set.of("powered", "waterlogged");
    /**
     * The key used to store the currently selected property in the item's custom data.
     */
    private static final String SELECTED_PROPERTY_KEY = "selected_property";

    /**
     * Constructor for the ScrewDriver item.
     *
     * @param pProperties The properties of the item.
     */
    public ScrewDriver(Properties pProperties) {
        super(pProperties);
    }

    /**
     * Called when the item is used on a block.
     * If the player is sneaking, it cycles through the available properties of the block.
     * If the player is not sneaking, it cycles through the values of the selected property.
     * This only works in creative mode.
     * @param pContext The context of the use action.
     * @return The result of the interaction.
     */
    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
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

    /**
     * Handles cycling through the available properties of a block state.
     * @param pPlayer The player using the item.
     * @param pState The block state being interacted with.
     * @param pStack The item stack of the screwdriver.
     */
    private void handlePropertyCycle(Player pPlayer, BlockState pState, ItemStack pStack) {
        Collection<Property<?>> properties = getAllowedProperties(pState);
        if (properties.isEmpty()) {
            // Updated sendSystemMessage call with boolean argument
            pPlayer.setCustomName(Component.literal("Block has no allowed properties."));
            return;
        }

        CustomData customData = pStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag tag = customData.copyTag(); // Get a mutable copy of the tag
        String currentPropName = tag.contains(SELECTED_PROPERTY_KEY) ? String.valueOf(tag.getString(SELECTED_PROPERTY_KEY)) : "";
        Property<?> nextProp = getNextProperty(properties, currentPropName);

        if (nextProp != null) {
            tag.putString(SELECTED_PROPERTY_KEY, nextProp.getName());
            pStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag)); // Set the updated CustomData
            // Updated sendSystemMessage call with boolean argument
            pPlayer.setCustomName(Component.literal("Selected property: " + nextProp.getName()));
        }
    }

    /**
     * Handles cycling through the values of the selected property of a block state.
     * @param pPlayer The player using the item.
     * @param pState The block state being interacted with.
     * @param pPos The position of the block.
     * @param pLevel The level the block is in.
     * @param pStack The item stack of the screwdriver.
     */
    private void handleValueCycle(Player pPlayer, BlockState pState, BlockPos pPos, Level pLevel, ItemStack pStack) {
        Collection<Property<?>> properties = getAllowedProperties(pState);
        if (properties.isEmpty()) {
            // Updated sendSystemMessage call with boolean argument
            pPlayer.setCustomName(Component.literal("Block has no allowed properties."));
            return;
        }

        CustomData customData = pStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag tag = customData.copyTag(); // Get a mutable copy of the tag
        String currentPropName = tag.contains(SELECTED_PROPERTY_KEY) ? String.valueOf(tag.getString(SELECTED_PROPERTY_KEY)) : "";

        Optional<Property<?>> selectedPropOpt = properties.stream()
                .filter(p -> p.getName().equals(currentPropName))
                .findFirst();

        Property<?> propertyToCycle = selectedPropOpt.orElse(properties.iterator().next());
        if (selectedPropOpt.isEmpty()) {
            // If no property was selected, or the selected one is no longer allowed, select the first one
            tag.putString(SELECTED_PROPERTY_KEY, propertyToCycle.getName());
            pStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
            pPlayer.setCustomName(Component.literal("Selected property: " + propertyToCycle.getName()));
        }

        BlockState newState = cycleProperty(pState, propertyToCycle);
        pLevel.setBlock(pPos, newState, 2);
        // Updated sendSystemMessage call with boolean argument
        pPlayer.setCustomName(Component.literal("Set " + propertyToCycle.getName() + " to " + newState.getValue(propertyToCycle)));
    }

    /**
     * Cycles to the next value of a given property in a block state.
     * @param pState The block state to modify.
     * @param pProperty The property to cycle.
     * @return The new block state with the updated property value.
     */
    private <T extends Comparable<T>> BlockState cycleProperty(BlockState pState, Property<T> pProperty) {
        return pState.setValue(pProperty, getNextValue(pProperty.getPossibleValues(), pState.getValue(pProperty)));
    }

    /**
     * Gets the next value in a collection, cycling back to the start if the end is reached.
     * @param pValues The collection of values.
     * @param pCurrentValue The current value.
     * @return The next value in the collection.
     */
    private <T> T getNextValue(Collection<T> pValues, T pCurrentValue) {
        return Iterables.get(pValues, (Iterables.indexOf(pValues, v -> Objects.equals(v, pCurrentValue)) + 1) % pValues.size());
    }

    /**
     * Gets the next property in a collection, cycling back to the start if the end is reached.
     * @param pProperties The collection of properties.
     * @param pCurrentPropName The name of the current property.
     * @return The next property in the collection.
     */
    private Property<?> getNextProperty(Collection<Property<?>> pProperties, String pCurrentPropName) {
        if (pCurrentPropName.isEmpty()) {
            return pProperties.iterator().next();
        }
        return Iterables.get(pProperties, (Iterables.indexOf(pProperties, p -> p != null && Objects.equals(p.getName(), pCurrentPropName)) + 1) % pProperties.size());
    }

    /**
     * Gets a collection of properties from the block state, excluding any that are in the FORBIDDEN_PROPERTIES set.
     * @param pState The block state to get the properties from.
     * @return A collection of allowed properties.
     */
    private Collection<Property<?>> getAllowedProperties(BlockState pState) {
        return pState.getProperties().stream()
                .filter(p -> !FORBIDDEN_PROPERTIES.contains(p.getName()))
                .collect(Collectors.toList());
    }
}
