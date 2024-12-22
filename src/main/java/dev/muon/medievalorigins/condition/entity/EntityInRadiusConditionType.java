package dev.muon.medievalorigins.condition.entity;

import dev.muon.medievalorigins.condition.ModEntityConditionTypes;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.condition.type.EntityConditionType;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.apoli.util.Comparison;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class EntityInRadiusConditionType extends EntityConditionType {
    private final EntityCondition entityCondition;
    private final double radius;
    private final int compareTo;
    private final Comparison comparison;

    public EntityInRadiusConditionType(EntityCondition entityCondition, double radius, int compareTo, Comparison comparison) {  // Changed parameter type
        this.entityCondition = entityCondition;
        this.radius = radius;
        this.compareTo = compareTo;
        this.comparison = comparison;
    }

    public static final TypedDataObjectFactory<EntityInRadiusConditionType> DATA_FACTORY = TypedDataObjectFactory.simple(
            new SerializableData()
                    .add("condition", EntityCondition.DATA_TYPE, null)
                    .add("radius", SerializableDataTypes.DOUBLE)
                    .add("compare_to", SerializableDataTypes.INT, 1)
                    .add("comparison", ApoliDataTypes.COMPARISON, Comparison.GREATER_THAN_OR_EQUAL),
            data -> new EntityInRadiusConditionType(
                    data.get("condition"),
                    data.getDouble("radius"),
                    data.getInt("compare_to"),
                    data.get("comparison")
            ),
            (type, data) -> data.instance()
                    .set("condition", type.entityCondition)
                    .set("radius", type.radius)
                    .set("compare_to", type.compareTo)
                    .set("comparison", type.comparison)
    );

    @Override
    public boolean test(Entity entity) {
        int stopAt = -1;
        switch (comparison) {
            case EQUAL, LESS_THAN_OR_EQUAL, GREATER_THAN -> stopAt = compareTo + 1;
            case LESS_THAN, GREATER_THAN_OR_EQUAL -> stopAt = compareTo;
        }

        int count = 0;
        for (Entity target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(radius))) {
            if (target != null && (entityCondition == null || entityCondition.test(target))) {
                count++;
                if (count == stopAt) {
                    break;
                }
            }
        }
        return comparison.compare(count, compareTo);
    }

    @Override
    public @NotNull ConditionConfiguration<?> getConfig() {
        return ModEntityConditionTypes.ENTITY_IN_RADIUS;
    }
}