package dev.muon.medievalorigins.condition.block;

import dev.muon.medievalorigins.condition.ModBlockConditionTypes;
import dev.muon.medievalorigins.condition.ModItemConditionTypes;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.context.BlockConditionContext;
import io.github.apace100.apoli.condition.type.BlockConditionType;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class IsCropConditionType extends BlockConditionType {
    @Override
    public boolean test(BlockConditionContext context) {
        BlockState state = context.blockState();
        return state.getBlock() instanceof CropBlock;
    }

    @Override
    public @NotNull ConditionConfiguration<?> getConfig() {
        return ModBlockConditionTypes.IS_CROP;
    }
}