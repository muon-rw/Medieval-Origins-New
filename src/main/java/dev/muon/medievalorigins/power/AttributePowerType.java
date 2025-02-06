package dev.muon.medievalorigins.power;

import dev.muon.medievalorigins.MedievalOrigins;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.apoli.util.AttributedEntityAttributeModifier;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.util.MiscUtil;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class AttributePowerType extends PowerType implements AttributeModifying {
    private final List<AttributedEntityAttributeModifier> attributedModifiers;
    private final boolean updateHealth;

    public AttributePowerType(List<AttributedEntityAttributeModifier> attributedModifiers, boolean updateHealth, Optional<EntityCondition> condition) {
        super(condition);
        this.attributedModifiers = attributedModifiers;
        this.updateHealth = updateHealth;
    }

    @Override
    public @NotNull PowerConfiguration<?> getConfig() {
        return ModPowerTypes.ATTRIBUTE;
    }

    @Override
    public void onGained() {
        addPersistentModifiers(getHolder());
    }

    @Override
    public void onLost() {
        removeModifiers(getHolder());
    }

    @Override
    public void onRespawn() {
        addPersistentModifiers(getHolder());
    }

    @Override
    public List<AttributedEntityAttributeModifier> attributedModifiers() {
        return this.attributedModifiers;
    }

    @Override
    public boolean shouldUpdateHealth() {
        return this.updateHealth;
    }

    public static final PowerConfiguration<AttributePowerType> FACTORY = PowerConfiguration.conditionedOf(
            MedievalOrigins.loc("attribute"),
            new SerializableData()
                    .add("modifier", ApoliDataTypes.ATTRIBUTED_ATTRIBUTE_MODIFIER, null)
                    .addFunctionedDefault("modifiers", ApoliDataTypes.ATTRIBUTED_ATTRIBUTE_MODIFIERS,
                            data -> MiscUtil.singletonListOrNull(data.get("modifier")))
                    .add("update_health", SerializableDataTypes.BOOLEAN, true)
                    .validate(MiscUtil.validateAnyFieldsPresent("modifier", "modifiers")),
            (data, condition) -> new AttributePowerType(
                    data.get("modifiers"),
                    data.get("update_health"),
                    condition
            ),
            (type, data) -> data.instance()
                    .set("modifiers", type.attributedModifiers)
                    .set("update_health", type.updateHealth)
    );
}