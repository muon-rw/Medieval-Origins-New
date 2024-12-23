package dev.muon.medievalorigins.power;

import dev.muon.medievalorigins.MedievalOrigins;
import io.github.apace100.apoli.condition.BiEntityCondition;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
public class MobsIgnorePowerType extends PowerType {
    private final EntityCondition mobCondition;
    private final BiEntityCondition biEntityCondition;

    public MobsIgnorePowerType(EntityCondition mobCondition, BiEntityCondition biEntityCondition, Optional<EntityCondition> condition) {
        super(condition);
        this.mobCondition = mobCondition;
        this.biEntityCondition = biEntityCondition;
    }

    public boolean shouldIgnore(Entity mob, Entity holder) {
        MedievalOrigins.LOGGER.info("Checking if {} should ignore {} with conditions: mob={}, bientity={}",
                mob.getName().getString(),
                holder.getName().getString(),
                mobCondition != null ? "present" : "null",
                biEntityCondition != null ? "present" : "null"
        );

        boolean mobTest = mobCondition == null || mobCondition.test(mob);
        boolean biEntityTest = biEntityCondition == null || biEntityCondition.test(holder, mob);

        MedievalOrigins.LOGGER.info("Test results: mobCondition={}, biEntityCondition={}",
                mobTest, biEntityTest);

        return mobTest && biEntityTest;
    }

    @Override
    public @NotNull PowerConfiguration<?> getConfig() {
        return ModPowerTypes.MOBS_IGNORE;
    }

    public static final PowerConfiguration<MobsIgnorePowerType> FACTORY = PowerConfiguration.conditionedOf(
            MedievalOrigins.loc("mobs_ignore"),
            new SerializableData()
                    .add("mob_condition", EntityCondition.DATA_TYPE, null)
                    .add("bientity_condition", BiEntityCondition.DATA_TYPE, null),
            (data, condition) -> new MobsIgnorePowerType(
                    data.get("mob_condition"),
                    data.get("bientity_condition"),
                    condition
            ),
            (type, data) -> data.instance()
                    .set("mob_condition", type.mobCondition)
                    .set("bientity_condition", type.biEntityCondition)
    );
}