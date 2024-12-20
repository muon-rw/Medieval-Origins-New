package dev.muon.medievalorigins.condition.entity;

import dev.muon.medievalorigins.condition.ModEntityConditionTypes;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.type.EntityConditionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class CreativeModeConditionType extends EntityConditionType {
    public static final TypedDataObjectFactory<CreativeModeConditionType> DATA_FACTORY = TypedDataObjectFactory.simple(
            new SerializableData(),
            data -> new CreativeModeConditionType(),
            (type, data) -> data.instance()
    );

    @Override
    public boolean test(Entity entity) {
        return entity instanceof Player player && player.getAbilities().instabuild;
    }

    @Override
    public @NotNull ConditionConfiguration<?> getConfig() {
        return ModEntityConditionTypes.CREATIVE_MODE;
    }
}