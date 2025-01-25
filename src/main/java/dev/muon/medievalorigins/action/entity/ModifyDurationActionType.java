package dev.muon.medievalorigins.action.entity;

import dev.muon.medievalorigins.action.ModEntityActionTypes;
import dev.muon.medievalorigins.entity.SummonedMob;
import io.github.apace100.apoli.action.ActionConfiguration;
import io.github.apace100.apoli.action.context.EntityActionContext;
import io.github.apace100.apoli.action.type.EntityActionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class ModifyDurationActionType extends EntityActionType {
    public static final TypedDataObjectFactory<ModifyDurationActionType> DATA_FACTORY = TypedDataObjectFactory.simple(
            new SerializableData()
                    .add("multiplier", SerializableDataTypes.FLOAT, 1.0f)
                    .add("make_permanent", SerializableDataTypes.BOOLEAN, false),
            data -> new ModifyDurationActionType(
                    data.get("multiplier"),
                    data.get("make_permanent")
            ),
            (type, data) -> data.instance()
                    .set("multiplier", type.multiplier)
                    .set("make_permanent", type.makePermanent)
    );

    private final float multiplier;
    private final boolean makePermanent;

    public ModifyDurationActionType(float multiplier, boolean makePermanent) {
        this.multiplier = multiplier;
        this.makePermanent = makePermanent;
    }

    @Override
    public void accept(EntityActionContext context) {
        Entity entity = context.entity();
        if (entity instanceof SummonedMob summon) {
            if (makePermanent) {
                summon.setIsLimitedLife(false);
            } else {
                int currentTicks = summon.getLifeTicks();
                summon.setLifeTicks((int)(currentTicks * multiplier));
            }
        }
    }

    @Override
    public @NotNull ActionConfiguration<?> getConfig() {
        return ModEntityActionTypes.MODIFY_DURATION;
    }
}