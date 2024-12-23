package dev.muon.medievalorigins.power;

import dev.muon.medievalorigins.MedievalOrigins;
import io.github.apace100.apoli.action.EntityAction;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ActionOnJumpPowerType extends PowerType {
    private final Optional<EntityAction> entityAction;

    public ActionOnJumpPowerType(Optional<EntityAction> entityAction, Optional<EntityCondition> condition) {
        super(condition);
        this.entityAction = entityAction;
    }

    public void executeAction(Entity entity) {
        entityAction.ifPresent(action -> action.execute(entity));
    }

    @Override
    public @NotNull PowerConfiguration<?> getConfig() {
        return ModPowerTypes.ACTION_ON_JUMP;
    }

    public static final PowerConfiguration<ActionOnJumpPowerType> FACTORY = PowerConfiguration.conditionedOf(
            MedievalOrigins.loc("action_on_jump"),
            new SerializableData()
                    .add("entity_action", EntityAction.DATA_TYPE.optional(), Optional.empty()),
            (data, condition) -> new ActionOnJumpPowerType(
                    data.get("entity_action"),
                    condition
            ),
            (type, data) -> data.instance()
                    .set("entity_action", type.entityAction)
    );
}