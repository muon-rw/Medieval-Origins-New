package dev.muon.medievalorigins.action;

import dev.muon.medievalorigins.MedievalOrigins;
import io.github.apace100.apoli.action.ActionConfiguration;
import io.github.apace100.apoli.action.type.EntityActionType;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.util.IdentifierAlias;
import net.minecraft.core.Registry;

public class ModEntityActions {
    public static final IdentifierAlias ALIASES = new IdentifierAlias();
    public static final SerializableDataType<ActionConfiguration<EntityActionType>> DATA_TYPE = SerializableDataType.registry(
            ApoliRegistries.ENTITY_ACTION_TYPE,
            MedievalOrigins.MOD_ID,
            ALIASES,
            (configurations, id) -> "Entity action type \"" + id + "\" is undefined!"
    );

    public static final ActionConfiguration<CastSpellActionType> CAST_SPELL = register(ActionConfiguration.of(
            MedievalOrigins.loc("cast_spell"),
            CastSpellActionType.DATA_FACTORY
    ));

    public static final ActionConfiguration<ClearNegativeEffectsActionType> CLEAR_NEGATIVE_EFFECTS = register(ActionConfiguration.of(
            MedievalOrigins.loc("clear_negative_effects"),
            ClearNegativeEffectsActionType.DATA_FACTORY
    ));

    public static void register() {
    }

    @SuppressWarnings("unchecked")
    public static <T extends EntityActionType> ActionConfiguration<T> register(ActionConfiguration<T> configuration) {
        ActionConfiguration<EntityActionType> casted = (ActionConfiguration<EntityActionType>) configuration;
        Registry.register(ApoliRegistries.ENTITY_ACTION_TYPE, casted.id(), casted);
        return configuration;
    }
}