package dev.muon.medievalorigins.condition.item;

import dev.muon.medievalorigins.condition.ModItemConditionTypes;
import dev.muon.medievalorigins.util.ItemDataUtil;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.type.ItemConditionType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class IsMeleeWeaponConditionType extends ItemConditionType {
    @Override
    public boolean test(Level world, ItemStack stack) {
        boolean acceptsSharpness = ItemDataUtil.getEnchantment(world, Enchantments.SHARPNESS)
                .canEnchant(stack);

        boolean hasAttackDamage = false;
        if (stack.getItem() instanceof DiggerItem) {
            var modifiers = ItemDataUtil.getAttributeModifiers(stack);
            final double[] totalDamage = {0.0};

            modifiers.forEach(EquipmentSlotGroup.MAINHAND, (attribute, modifier) -> {
                if (attribute.value() == Attributes.ATTACK_DAMAGE) {
                    totalDamage[0] += modifier.amount();
                }
            });

            hasAttackDamage = totalDamage[0] > 0;
        }

        return acceptsSharpness || hasAttackDamage;
    }

    @Override
    public @NotNull ConditionConfiguration<?> getConfig() {
        return ModItemConditionTypes.IS_MELEE_WEAPON;
    }
}