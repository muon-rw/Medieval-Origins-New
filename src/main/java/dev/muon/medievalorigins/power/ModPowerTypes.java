package dev.muon.medievalorigins.power;

import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.core.Registry;

public class ModPowerTypes {
    public static final PowerConfiguration<PixieWingsPowerType> PIXIE_WINGS = register(PixieWingsPowerType.FACTORY);
    public static final PowerConfiguration<IcarusWingsPowerType> ICARUS_WINGS = register(IcarusWingsPowerType.FACTORY);
    public static final PowerConfiguration<OwnerAttributeTransferPowerType> OWNER_ATTRIBUTE_TRANSFER = register(OwnerAttributeTransferPowerType.FACTORY);
    public static final PowerConfiguration<MobsIgnorePowerType> MOBS_IGNORE = register(MobsIgnorePowerType.FACTORY);
    public static final PowerConfiguration<ActionOnTargetDeathPowerType> ACTION_ON_TARGET_DEATH = register(ActionOnTargetDeathPowerType.FACTORY);
    public static final PowerConfiguration<CustomDeathSoundPowerType> CUSTOM_DEATH_SOUND = register(CustomDeathSoundPowerType.FACTORY);
    public static final PowerConfiguration<ActionOnJumpPowerType> ACTION_ON_JUMP = register(ActionOnJumpPowerType.FACTORY);
    public static final PowerConfiguration<AttributePowerType> ATTRIBUTE = register(AttributePowerType.FACTORY);

    public static void register() {
    }


    @SuppressWarnings("unchecked")
    public static <T extends PowerType> PowerConfiguration<T> register(PowerConfiguration<T> configuration) {

        PowerConfiguration<PowerType> casted = (PowerConfiguration<PowerType>) configuration;
        Registry.register(ApoliRegistries.POWER_TYPE, casted.id(), casted);

        return configuration;
    }
}