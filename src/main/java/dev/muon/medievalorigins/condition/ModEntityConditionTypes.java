package dev.muon.medievalorigins.condition;

import dev.muon.medievalorigins.MedievalOrigins;
import dev.muon.medievalorigins.condition.entity.*;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.type.EntityConditionType;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.util.IdentifierAlias;
import net.minecraft.core.Registry;

public class ModEntityConditionTypes {
    public static final IdentifierAlias ALIASES = new IdentifierAlias();
    public static final SerializableDataType<ConditionConfiguration<EntityConditionType>> DATA_TYPE =
            SerializableDataType.registry(ApoliRegistries.ENTITY_CONDITION_TYPE, MedievalOrigins.MOD_ID, ALIASES,
                    (configurations, id) -> "Entity condition type \"" + id + "\" is undefined!");

    public static final ConditionConfiguration<IsArrowConditionType> IS_ARROW = register(ConditionConfiguration.of(MedievalOrigins.loc("is_arrow"), IsArrowConditionType.DATA_FACTORY));
    public static final ConditionConfiguration<EntityInRadiusConditionType> ENTITY_IN_RADIUS = register(ConditionConfiguration.of(MedievalOrigins.loc("entity_in_radius"), EntityInRadiusConditionType.DATA_FACTORY));
    public static final ConditionConfiguration<CreativeModeConditionType> CREATIVE_MODE = register(ConditionConfiguration.of(MedievalOrigins.loc("creative_mode"), CreativeModeConditionType.DATA_FACTORY));
    public static final ConditionConfiguration<OptionalDimensionConditionType> OPTIONAL_DIMENSION = register(ConditionConfiguration.of(MedievalOrigins.loc("dimension"), OptionalDimensionConditionType.DATA_FACTORY));
    public static final ConditionConfiguration<CoveredByBlockConditionType> COVERED_BY_BLOCK = register(ConditionConfiguration.of(MedievalOrigins.loc("covered_by_block"), CoveredByBlockConditionType.DATA_FACTORY));

    public static void register() {
    }

    @SuppressWarnings("unchecked")
    private static <CT extends EntityConditionType> ConditionConfiguration<CT> register(ConditionConfiguration<CT> configuration) {
        ConditionConfiguration<EntityConditionType> casted = (ConditionConfiguration<EntityConditionType>) configuration;
        Registry.register(ApoliRegistries.ENTITY_CONDITION_TYPE, casted.id(), casted);
        return configuration;
    }
}