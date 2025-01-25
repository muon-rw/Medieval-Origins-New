package dev.muon.medievalorigins.condition.entity;

import dev.muon.medievalorigins.condition.ModEntityConditionTypes;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.context.EntityConditionContext;
import io.github.apace100.apoli.condition.type.EntityConditionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.jetbrains.annotations.NotNull;

public class IsArrowConditionType extends EntityConditionType {
    public static final TypedDataObjectFactory<IsArrowConditionType> DATA_FACTORY = TypedDataObjectFactory.simple(
            new SerializableData(),
            data -> new IsArrowConditionType(),
            (type, data) -> data.instance()
    );

    @Override
    public boolean test(EntityConditionContext context) {
        Entity entity = context.entity();
        return entity instanceof AbstractArrow;
    }

    @Override
    public @NotNull ConditionConfiguration<?> getConfig() {
        return ModEntityConditionTypes.IS_ARROW;
    }
}