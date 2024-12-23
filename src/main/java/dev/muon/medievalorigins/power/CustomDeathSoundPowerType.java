package dev.muon.medievalorigins.power;

import dev.muon.medievalorigins.MedievalOrigins;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class CustomDeathSoundPowerType extends PowerType {
    private final SoundEvent sound;
    private final boolean muted;
    private final float volume;
    private final float pitch;

    public CustomDeathSoundPowerType(SoundEvent sound,
                                     boolean muted,
                                     float volume,
                                     float pitch,
                                     Optional<EntityCondition> condition) {
        super(condition);
        this.sound = sound;
        this.muted = muted;
        this.volume = volume;
        this.pitch = pitch;
    }

    public void playDeathSound(Entity entity) {
        if (muted || sound == null) return;

        RandomSource random = entity instanceof LivingEntity living
                ? living.getRandom()
                : entity.level().random;

        float randomPitch = (random.nextFloat() - random.nextFloat()) * 0.2F + pitch;

        // One of these works. I'll figure out which at some point
        if (entity instanceof LivingEntity living) {
            living.playSound(sound, volume, randomPitch);
        }

        entity.level().playSound(
                null,
                entity.getX(), entity.getY(), entity.getZ(),
                sound,
                entity.getSoundSource(),
                volume,
                randomPitch
        );

        if (entity.level() instanceof ServerLevel serverLevel) {
            serverLevel.playSound(
                    null,
                    entity.getX(), entity.getY(), entity.getZ(),
                    sound,
                    entity.getSoundSource(),
                    volume,
                    randomPitch
            );
        }
    }

    public boolean isMuted() {
        return muted;
    }

    @Override
    public @NotNull PowerConfiguration<?> getConfig() {
        return ModPowerTypes.CUSTOM_DEATH_SOUND;
    }

    public static final PowerConfiguration<CustomDeathSoundPowerType> FACTORY = PowerConfiguration.conditionedOf(
            MedievalOrigins.loc("custom_death_sound"),
            new SerializableData()
                    .add("sound", SerializableDataTypes.SOUND_EVENT)
                    .add("muted", SerializableDataTypes.BOOLEAN, false)
                    .add("volume", SerializableDataTypes.FLOAT, 1.0f)
                    .add("pitch", SerializableDataTypes.FLOAT, 1.0f),
            (data, condition) -> new CustomDeathSoundPowerType(
                    data.get("sound"),
                    data.getBoolean("muted"),
                    data.getFloat("volume"),
                    data.getFloat("pitch"),
                    condition
            ),
            (type, data) -> data.instance()
                    .set("sound", type.sound)
                    .set("muted", type.muted)
                    .set("volume", type.volume)
                    .set("pitch", type.pitch)
    );
}