package dev.muon.medievalorigins.condition.item;

import dev.muon.medievalorigins.condition.ModItemConditionTypes;
import dev.muon.medievalorigins.util.ItemDataUtil;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.type.ItemConditionType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class IsValkyrieWeaponConditionType extends ItemConditionType {
    @Override
    public boolean test(Level world, ItemStack stack) {
        String itemName = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
        return (stack.getItem() instanceof SwordItem ||
                stack.getItem() instanceof TridentItem ||
                ItemDataUtil.getEnchantment(world, Enchantments.SHARPNESS).canEnchant(stack) ||
                ItemDataUtil.getEnchantment(world, Enchantments.PIERCING).canEnchant(stack))
                && (itemName.contains("glaive") || itemName.contains("spear")
                || itemName.contains("lance") || itemName.contains("halberd"));
    }

    @Override
    public @NotNull ConditionConfiguration<?> getConfig() {
        return ModItemConditionTypes.IS_VALKYRIE_WEAPON;
    }
}