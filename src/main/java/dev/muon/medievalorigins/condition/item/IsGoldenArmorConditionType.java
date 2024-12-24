package dev.muon.medievalorigins.condition.item;

import dev.muon.medievalorigins.condition.ModItemConditionTypes;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.type.ItemConditionType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class IsGoldenArmorConditionType extends ItemConditionType {
    public boolean test(Level world, ItemStack stack) {
        String itemName = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
        boolean isGoldArmor = stack.getItem() instanceof ArmorItem &&
                (itemName.contains("gold") || itemName.contains("gilded"));

        boolean hasGoldTrim = false;
        ArmorTrim trim = stack.getComponents().get(DataComponents.TRIM);
        if (trim != null) {
            hasGoldTrim = trim.material().is(TrimMaterials.GOLD);
        }

        return isGoldArmor || hasGoldTrim;
    }

    @Override
    public @NotNull ConditionConfiguration<?> getConfig() {
        return ModItemConditionTypes.IS_GOLDEN_ARMOR;
    }
}