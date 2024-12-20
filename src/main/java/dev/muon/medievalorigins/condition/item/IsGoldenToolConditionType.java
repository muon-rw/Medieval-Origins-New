package dev.muon.medievalorigins.condition.item;

import dev.muon.medievalorigins.condition.ModItemConditionTypes;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.type.ItemConditionType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class IsGoldenToolConditionType extends ItemConditionType {
    @Override
    public boolean test(Level world, ItemStack stack) {
        String itemName = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
        return stack.getItem() instanceof DiggerItem &&
                (itemName.contains("gold") || itemName.contains("gilded"));
    }

    @Override
    public @NotNull ConditionConfiguration<?> getConfig() {
        return ModItemConditionTypes.IS_GOLDEN_TOOL;
    }
}