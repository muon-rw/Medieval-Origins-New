package dev.muon.medievalorigins.condition.item;

import dev.muon.medievalorigins.condition.ModItemConditionTypes;
import dev.muon.medievalorigins.enchantment.ModEnchantments;
import dev.muon.medievalorigins.util.ItemDataUtil;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.type.ItemConditionType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class IsHeavyArmorConditionType extends ItemConditionType {
    @Override
    public boolean test(Level world, ItemStack stack) {
        if (stack.getItem() instanceof ArmorItem armorItem) {
            int featherweightLevel = ItemDataUtil.getEnchantmentLevel(stack, ModEnchantments.FEATHERWEIGHT, world);
            return armorItem.getToughness() > 0 && featherweightLevel == 0;
        }
        return false;
    }

    @Override
    public @NotNull ConditionConfiguration<?> getConfig() {
        return ModItemConditionTypes.IS_HEAVY_ARMOR;
    }
}