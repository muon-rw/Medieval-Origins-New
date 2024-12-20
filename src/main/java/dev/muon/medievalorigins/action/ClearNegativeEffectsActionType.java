package dev.muon.medievalorigins.action;

import io.github.apace100.apoli.action.ActionConfiguration;
import io.github.apace100.apoli.action.type.EntityActionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class ClearNegativeEffectsActionType extends EntityActionType {
    public static final TypedDataObjectFactory<ClearNegativeEffectsActionType> DATA_FACTORY;

    public ClearNegativeEffectsActionType() {
    }

    @Override
    protected void execute(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.getActiveEffects().stream()
                    .map(MobEffectInstance::getEffect)
                    .filter(effect -> !effect.value().isBeneficial())
                    .forEach(livingEntity::removeEffect);
        }
    }

    @Override
    public @NotNull ActionConfiguration<?> getConfig() {
        return ModEntityActions.CLEAR_NEGATIVE_EFFECTS;
    }

    static {
        DATA_FACTORY = TypedDataObjectFactory.simple(
                new SerializableData(),
                data -> new ClearNegativeEffectsActionType(),
                (actionType, serializableData) -> serializableData.instance()
        );
    }
}