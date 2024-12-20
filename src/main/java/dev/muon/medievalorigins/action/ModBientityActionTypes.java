package dev.muon.medievalorigins.action;

import dev.muon.medievalorigins.MedievalOrigins;
import dev.muon.medievalorigins.action.bientity.CastSpellBientityActionType;
import dev.muon.medievalorigins.action.bientity.SpellDamageActionType;
import dev.muon.medievalorigins.action.bientity.TransferItemActionType;
import io.github.apace100.apoli.action.ActionConfiguration;
import io.github.apace100.apoli.action.type.BiEntityActionType;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.util.IdentifierAlias;
import net.minecraft.core.Registry;

public class ModBientityActionTypes {
    public static final IdentifierAlias ALIASES = new IdentifierAlias();
    public static final SerializableDataType<ActionConfiguration<BiEntityActionType>> DATA_TYPE = SerializableDataType.registry(
            ApoliRegistries.BIENTITY_ACTION_TYPE,
            MedievalOrigins.MOD_ID,
            ALIASES,
            (configurations, id) -> "Bi-entity action type \"" + id + "\" is undefined!"
    );

    public static final ActionConfiguration<CastSpellBientityActionType> CAST_SPELL = register(ActionConfiguration.of(MedievalOrigins.loc("cast_spell"), CastSpellBientityActionType.DATA_FACTORY));
    public static final ActionConfiguration<SpellDamageActionType> SPELL_DAMAGE = register(ActionConfiguration.of(MedievalOrigins.loc("cast_spell"), SpellDamageActionType.DATA_FACTORY));
    public static final ActionConfiguration<TransferItemActionType> TRANSFER_ITEM = register(ActionConfiguration.of(MedievalOrigins.loc("cast_spell"), TransferItemActionType.DATA_FACTORY));

    public static void register() {}

    @SuppressWarnings("unchecked")
    public static <T extends BiEntityActionType> ActionConfiguration<T> register(ActionConfiguration<T> configuration) {
        ActionConfiguration<BiEntityActionType> casted = (ActionConfiguration<BiEntityActionType>) configuration;
        Registry.register(ApoliRegistries.BIENTITY_ACTION_TYPE, casted.id(), casted);
        return configuration;
    }
}