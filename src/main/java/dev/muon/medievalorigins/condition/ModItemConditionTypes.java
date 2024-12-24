package dev.muon.medievalorigins.condition;

import dev.muon.medievalorigins.MedievalOrigins;
import dev.muon.medievalorigins.condition.item.*;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.type.ItemConditionType;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.util.IdentifierAlias;
import net.minecraft.core.Registry;

public class ModItemConditionTypes {
    public static final IdentifierAlias ALIASES = new IdentifierAlias();
    public static final SerializableDataType<ConditionConfiguration<ItemConditionType>> DATA_TYPE =
            SerializableDataType.registry(ApoliRegistries.ITEM_CONDITION_TYPE, MedievalOrigins.MOD_ID, ALIASES,
                    (configurations, id) -> "Item condition type \"" + id + "\" is undefined!");

    public static final ConditionConfiguration<IsAxeConditionType> IS_AXE = register(ConditionConfiguration.simple(MedievalOrigins.loc("is_axe"), IsAxeConditionType::new));
    public static final ConditionConfiguration<IsBowConditionType> IS_BOW = register(ConditionConfiguration.simple(MedievalOrigins.loc("is_bow"), IsBowConditionType::new));
    public static final ConditionConfiguration<IsCursedConditionType> IS_CURSED = register(ConditionConfiguration.simple(MedievalOrigins.loc("is_cursed"), IsCursedConditionType::new));
    public static final ConditionConfiguration<IsDaggerConditionType> IS_DAGGER = register(ConditionConfiguration.simple(MedievalOrigins.loc("is_dagger"), IsDaggerConditionType::new));
    public static final ConditionConfiguration<IsFistWeaponConditionType> IS_FIST_WEAPON = register(ConditionConfiguration.simple(MedievalOrigins.loc("is_fist_weapon"), IsFistWeaponConditionType::new));
    public static final ConditionConfiguration<IsGoldenArmorConditionType> IS_GOLDEN_ARMOR = register(ConditionConfiguration.simple(MedievalOrigins.loc("is_golden_armor"), IsGoldenArmorConditionType::new));
    public static final ConditionConfiguration<IsGoldenToolConditionType> IS_GOLDEN_TOOL = register(ConditionConfiguration.simple(MedievalOrigins.loc("is_golden_tool"), IsGoldenToolConditionType::new));
    public static final ConditionConfiguration<IsGoldenWeaponConditionType> IS_GOLDEN_WEAPON = register(ConditionConfiguration.simple(MedievalOrigins.loc("is_golden_weapon"), IsGoldenWeaponConditionType::new));
    public static final ConditionConfiguration<IsHeavyArmorConditionType> IS_HEAVY_ARMOR = register(ConditionConfiguration.simple(MedievalOrigins.loc("is_heavy_armor"), IsHeavyArmorConditionType::new));
    public static final ConditionConfiguration<IsMeleeWeaponConditionType> IS_MELEE_WEAPON = register(ConditionConfiguration.simple(MedievalOrigins.loc("is_melee_weapon"), IsMeleeWeaponConditionType::new));
    public static final ConditionConfiguration<IsSilverArmorConditionType> IS_SILVER_ARMOR = register(ConditionConfiguration.simple(MedievalOrigins.loc("is_silver_armor"), IsSilverArmorConditionType::new));
    public static final ConditionConfiguration<IsSilverToolConditionType> IS_SILVER_TOOL = register(ConditionConfiguration.simple(MedievalOrigins.loc("is_silver_tool"), IsSilverToolConditionType::new));
    public static final ConditionConfiguration<IsSilverWeaponConditionType> IS_SILVER_WEAPON = register(ConditionConfiguration.simple(MedievalOrigins.loc("is_silver_weapon"), IsSilverWeaponConditionType::new));
    public static final ConditionConfiguration<IsSummonEquipmentConditionType> IS_SUMMON_EQUIPMENT = register(ConditionConfiguration.simple(MedievalOrigins.loc("is_summon_equipment"), IsSummonEquipmentConditionType::new));
    public static final ConditionConfiguration<IsToolConditionType> IS_TOOL = register(ConditionConfiguration.simple(MedievalOrigins.loc("is_tool"), IsToolConditionType::new));
    public static final ConditionConfiguration<IsValkyrieWeaponConditionType> IS_VALKYRIE_WEAPON = register(ConditionConfiguration.simple(MedievalOrigins.loc("is_valkyrie_weapon"), IsValkyrieWeaponConditionType::new));

    public static void register() {
    }

    @SuppressWarnings("unchecked")
    private static <CT extends ItemConditionType> ConditionConfiguration<CT> register(ConditionConfiguration<CT> configuration) {
        ConditionConfiguration<ItemConditionType> casted = (ConditionConfiguration<ItemConditionType>) configuration;
        Registry.register(ApoliRegistries.ITEM_CONDITION_TYPE, casted.id(), casted);
        return configuration;
    }
}