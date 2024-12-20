package dev.muon.medievalorigins.condition;

import dev.muon.medievalorigins.MedievalOrigins;
import dev.muon.medievalorigins.condition.bientity.AlliedConditionType;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.type.BiEntityConditionType;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.util.IdentifierAlias;
import net.minecraft.core.Registry;

public class ModBientityConditionTypes {
    public static final IdentifierAlias ALIASES = new IdentifierAlias();
    public static final SerializableDataType<ConditionConfiguration<BiEntityConditionType>> DATA_TYPE =
            SerializableDataType.registry(ApoliRegistries.BIENTITY_CONDITION_TYPE, MedievalOrigins.MOD_ID, ALIASES,
                    (configurations, id) -> "Bi-entity condition type \"" + id + "\" is undefined!");

    public static final ConditionConfiguration<AlliedConditionType> ALLIED = register(
            ConditionConfiguration.of(MedievalOrigins.loc("allied"), AlliedConditionType.DATA_FACTORY));

    public static void register() {
        // Add any aliases if needed
    }

    @SuppressWarnings("unchecked")
    private static <CT extends BiEntityConditionType> ConditionConfiguration<CT> register(ConditionConfiguration<CT> configuration) {
        ConditionConfiguration<BiEntityConditionType> casted = (ConditionConfiguration<BiEntityConditionType>) configuration;
        Registry.register(ApoliRegistries.BIENTITY_CONDITION_TYPE, casted.id(), casted);
        return configuration;
    }
}