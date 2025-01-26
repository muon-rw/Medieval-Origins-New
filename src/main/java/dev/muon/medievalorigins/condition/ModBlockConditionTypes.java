package dev.muon.medievalorigins.condition;

import dev.muon.medievalorigins.MedievalOrigins;
import dev.muon.medievalorigins.condition.block.IsCropConditionType;
import dev.muon.medievalorigins.condition.block.IsFlowerConditionType;
import dev.muon.medievalorigins.condition.item.*;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.type.BlockConditionType;
import io.github.apace100.apoli.condition.type.ItemConditionType;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.util.IdentifierAlias;
import net.minecraft.core.Registry;

public class ModBlockConditionTypes {
    public static final IdentifierAlias ALIASES = new IdentifierAlias();
    public static final SerializableDataType<ConditionConfiguration<BlockConditionType>> DATA_TYPE =
            SerializableDataType.registry(ApoliRegistries.BLOCK_CONDITION_TYPE, MedievalOrigins.MOD_ID, ALIASES,
                    (configurations, id) -> "Block condition type \"" + id + "\" is undefined!");

    public static final ConditionConfiguration<IsCropConditionType> IS_CROP = register(ConditionConfiguration.simple(MedievalOrigins.loc("is_crop"), IsCropConditionType::new));
    public static final ConditionConfiguration<IsFlowerConditionType> IS_FLOWER = register(ConditionConfiguration.simple(MedievalOrigins.loc("is_flower"), IsFlowerConditionType::new));

    public static void register() {
    }

    @SuppressWarnings("unchecked")
    private static <CT extends BlockConditionType> ConditionConfiguration<CT> register(ConditionConfiguration<CT> configuration) {
        ConditionConfiguration<BlockConditionType> casted = (ConditionConfiguration<BlockConditionType>) configuration;
        Registry.register(ApoliRegistries.BLOCK_CONDITION_TYPE, casted.id(), casted);
        return configuration;
    }
}