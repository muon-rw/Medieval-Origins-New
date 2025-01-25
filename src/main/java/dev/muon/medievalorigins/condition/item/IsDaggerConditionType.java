package dev.muon.medievalorigins.condition.item;

import dev.muon.medievalorigins.condition.ModItemConditionTypes;
import dev.muon.medievalorigins.util.ItemDataUtil;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.context.ItemConditionContext;
import io.github.apace100.apoli.condition.type.ItemConditionType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class IsDaggerConditionType extends ItemConditionType {
    @Override
    public boolean test(ItemConditionContext context) {
        ItemStack stack = context.stack();
        Level world = context.world();
        String itemName = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
        return (stack.getItem() instanceof SwordItem ||
                ItemDataUtil.getEnchantment(world, Enchantments.SHARPNESS).canEnchant(stack))
                && (itemName.contains("dagger") || itemName.contains("knife")
                || itemName.contains("sai") || itemName.contains("athame"));
    }

    @Override
    public @NotNull ConditionConfiguration<?> getConfig() {
        return ModItemConditionTypes.IS_DAGGER;
    }
}