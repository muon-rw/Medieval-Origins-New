package dev.muon.medievalorigins.condition.item;

import dev.muon.medievalorigins.condition.ModItemConditionTypes;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.type.ItemConditionType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class IsSilverArmorConditionType extends ItemConditionType {
    @Override
    public boolean test(Level world, ItemStack stack) {
        String itemName = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
        boolean isGoldArmor = stack.getItem() instanceof ArmorItem &&
                (itemName.contains("silver") || itemName.contains("iron"));

        boolean hasSilverTrim = false;
        // TODO: All the Trims compat
        /*
        ArmorTrim trim = stack.getComponents().get(DataComponents.TRIM);
        if (trim != null) {
            hasSilverTrim = trim.material().is(TrimMaterials.SILVER);
        }
        */
        return isGoldArmor || hasSilverTrim;
    }

    @Override
    public @NotNull ConditionConfiguration<?> getConfig() {
        return ModItemConditionTypes.IS_SILVER_ARMOR;
    }
}