package dev.muon.medievalorigins.condition.entity;

import dev.muon.medievalorigins.condition.ModEntityConditionTypes;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.context.EntityConditionContext;
import io.github.apace100.apoli.condition.type.EntityConditionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class LookingDownConditionType extends EntityConditionType {
    public static final TypedDataObjectFactory<LookingDownConditionType> DATA_FACTORY = TypedDataObjectFactory.simple(
            new SerializableData(),
            data -> new LookingDownConditionType(),
            (type, data) -> data.instance()
    );

    @Override
    public boolean test(EntityConditionContext context) {
        Entity entity = context.entity();
        if (entity instanceof Player player) {
            float pitch = player.getXRot();
            return pitch >= 70.0f && pitch <= 90.0f;
        }
        return false;
    }

    @Override
    public @NotNull ConditionConfiguration<?> getConfig() {
        return ModEntityConditionTypes.LOOKING_DOWN;
    }
}
