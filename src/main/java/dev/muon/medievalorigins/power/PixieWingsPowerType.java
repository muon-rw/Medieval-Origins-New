package dev.muon.medievalorigins.power;

import dev.muon.medievalorigins.MedievalOrigins;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PixieWingsPowerType extends PowerType {

    public PixieWingsPowerType(Optional<EntityCondition> condition) {
        super(condition);
    }

    @Override
    public @NotNull PowerConfiguration<?> getConfig() {
        return ModPowerTypes.PIXIE_WINGS;
    }

    public static final PowerConfiguration<PixieWingsPowerType> FACTORY = PowerConfiguration.conditionedSimple(
            MedievalOrigins.loc("pixie_wings"),
            PixieWingsPowerType::new
    );
}