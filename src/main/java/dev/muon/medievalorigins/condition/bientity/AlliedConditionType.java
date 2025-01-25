package dev.muon.medievalorigins.condition.bientity;

import dev.muon.medievalorigins.condition.ModBientityConditionTypes;
import io.github.apace100.apoli.action.context.BiEntityActionContext;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.context.BiEntityConditionContext;
import io.github.apace100.apoli.condition.type.BiEntityConditionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.apoli.util.context.ConditionContext;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class AlliedConditionType extends BiEntityConditionType {
    public static final TypedDataObjectFactory<AlliedConditionType> DATA_FACTORY = TypedDataObjectFactory.simple(
            new SerializableData(),
            data -> new AlliedConditionType(),
            (type, data) -> data.instance()
    );

    @Override
    public boolean test(BiEntityConditionContext context) {
        Entity actor = context.actor();
        Entity target = context.target();
        return actor.isAlliedTo(target);
    }

    @Override
    public @NotNull ConditionConfiguration<?> getConfig() {
        return ModBientityConditionTypes.ALLIED;
    }
}