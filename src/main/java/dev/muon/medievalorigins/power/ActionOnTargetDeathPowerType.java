package dev.muon.medievalorigins.power;

import dev.muon.medievalorigins.MedievalOrigins;
import io.github.apace100.apoli.action.BiEntityAction;
import io.github.apace100.apoli.condition.BiEntityCondition;
import io.github.apace100.apoli.condition.DamageCondition;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.CooldownPowerType;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ActionOnTargetDeathPowerType extends CooldownPowerType {
    private final Optional<BiEntityAction> biEntityAction;
    private final Optional<BiEntityCondition> biEntityCondition;
    private final Optional<DamageCondition> damageCondition;

    public ActionOnTargetDeathPowerType(Optional<BiEntityAction> biEntityAction,
                                        Optional<BiEntityCondition> biEntityCondition,
                                        Optional<DamageCondition> damageCondition,
                                        HudRender hudRender,
                                        int cooldown,
                                        Optional<EntityCondition> condition) {
        super(cooldown, hudRender, condition);
        this.biEntityAction = biEntityAction;
        this.biEntityCondition = biEntityCondition;
        this.damageCondition = damageCondition;
    }

    public boolean doesApply(Entity target, DamageSource source, float amount) {
        return canUse() &&
                damageCondition.map(condition -> condition.test(source, amount)).orElse(true) &&
                biEntityCondition.map(condition -> condition.test(getHolder(), target)).orElse(true);
    }

    public void executeActions(Entity target, DamageSource source, float amount) {
        if (doesApply(target, source, amount)) {
            use();
            biEntityAction.ifPresent(action -> action.execute(getHolder(), target));
        }
    }

    @Override
    public @NotNull PowerConfiguration<?> getConfig() {
        return ModPowerTypes.ACTION_ON_TARGET_DEATH;
    }

    public static final PowerConfiguration<ActionOnTargetDeathPowerType> FACTORY = PowerConfiguration.conditionedOf(
            MedievalOrigins.loc("action_on_target_death"),
            new SerializableData()
                    .add("bientity_action", BiEntityAction.DATA_TYPE.optional(), Optional.empty())
                    .add("bientity_condition", BiEntityCondition.DATA_TYPE.optional(), Optional.empty())
                    .add("damage_condition", DamageCondition.DATA_TYPE.optional(), Optional.empty())
                    .add("cooldown", SerializableDataTypes.INT, 1)
                    .add("hud_render", HudRender.DATA_TYPE, HudRender.DONT_RENDER),
            (data, condition) -> new ActionOnTargetDeathPowerType(
                    data.get("bientity_action"),
                    data.get("bientity_condition"),
                    data.get("damage_condition"),
                    data.get("hud_render"),
                    data.get("cooldown"),
                    condition
            ),
            (type, data) -> data.instance()
                    .set("bientity_action", type.biEntityAction)
                    .set("bientity_condition", type.biEntityCondition)
                    .set("damage_condition", type.damageCondition)
                    .set("cooldown", type.getCooldown())
                    .set("hud_render", type.getRenderSettings())
    );
}
