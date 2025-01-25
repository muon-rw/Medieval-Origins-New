package dev.muon.medievalorigins.condition.item;

import dev.muon.medievalorigins.condition.ModItemConditionTypes;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.context.ItemConditionContext;
import io.github.apace100.apoli.condition.type.ItemConditionType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class IsSilverWeaponConditionType extends ItemConditionType {
    @Override
    public boolean test(ItemConditionContext context) {
        ItemStack stack = context.stack();
        String itemName = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
        return stack.getItem() instanceof SwordItem &&
                (itemName.contains("silver") || itemName.contains("iron"));
    }

    @Override
    public @NotNull ConditionConfiguration<?> getConfig() {
        return ModItemConditionTypes.IS_SILVER_WEAPON;
    }
}