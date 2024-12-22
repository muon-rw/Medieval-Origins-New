package dev.muon.medievalorigins.condition.entity;

import dev.muon.medievalorigins.condition.ModEntityConditionTypes;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.type.EntityConditionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class OptionalDimensionConditionType extends EntityConditionType {
    private final ResourceLocation dimension;

    public OptionalDimensionConditionType(ResourceLocation dimension) {
        this.dimension = dimension;
    }

    public static final TypedDataObjectFactory<OptionalDimensionConditionType> DATA_FACTORY = TypedDataObjectFactory.simple(
            new SerializableData()
                    .add("dimension", SerializableDataTypes.IDENTIFIER),
            data -> new OptionalDimensionConditionType(
                    data.get("dimension")
            ),
            (type, data) -> data.instance()
                    .set("dimension", type.dimension)
    );

    @Override
    public boolean test(Entity entity) {
        ResourceLocation currentDimension = entity.level().dimension().location();
        return currentDimension.equals(dimension);
    }

    @Override
    public @NotNull ConditionConfiguration<?> getConfig() {
        return ModEntityConditionTypes.OPTIONAL_DIMENSION;
    }
}