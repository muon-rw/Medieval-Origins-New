package dev.muon.medievalorigins.condition.item;

import dev.muon.medievalorigins.condition.ModItemConditionTypes;
import dev.muon.medievalorigins.util.ItemDataUtil;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.context.ItemConditionContext;
import io.github.apace100.apoli.condition.type.ItemConditionType;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class IsCursedConditionType extends ItemConditionType {
    @Override
    public boolean test(ItemConditionContext context) {
        ItemStack stack = context.stack();
        ItemEnchantments enchantments = stack.getEnchantments();
        if (enchantments == ItemEnchantments.EMPTY) {
            return false;
        }

        return enchantments.keySet().stream()
                .filter(holder -> enchantments.getLevel(holder) > 0)
                .anyMatch(holder -> holder.is(EnchantmentTags.CURSE));
    }

    @Override
    public @NotNull ConditionConfiguration<?> getConfig() {
        return ModItemConditionTypes.IS_CURSED;
    }
}