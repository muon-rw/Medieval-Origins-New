package dev.muon.medievalorigins.entity;

import dev.muon.medievalorigins.MedievalOrigins;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class ModEntities {
    public static EntityType<SummonedSkeleton> SUMMON_SKELETON = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            MedievalOrigins.loc("summon_skeleton"),
            EntityType.Builder.<SummonedSkeleton>of(SummonedSkeleton::new, MobCategory.CREATURE)
                    .sized(1.0F, 1.8F)
                    .clientTrackingRange(10)
                    .build(MedievalOrigins.loc("summon_skeleton").toString())
    );

    public static EntityType<SummonedZombie> SUMMON_ZOMBIE = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            MedievalOrigins.loc("summon_zombie"),
            EntityType.Builder.<SummonedZombie>of(SummonedZombie::new, MobCategory.CREATURE)
                    .sized(1.0F, 1.8F)
                    .clientTrackingRange(10)
                    .build(MedievalOrigins.loc("summon_zombie").toString())
    );

    public static EntityType<SummonedWitherSkeleton> SUMMON_WITHER_SKELETON = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            MedievalOrigins.loc("summon_wither_skeleton"),
            EntityType.Builder.<SummonedWitherSkeleton>of(SummonedWitherSkeleton::new, MobCategory.CREATURE)
                    .sized(1.0F, 2.1F)
                    .clientTrackingRange(10)
                    .build(MedievalOrigins.loc("summon_wither_skeleton").toString())
    );

    public static void register() {
        FabricDefaultAttributeRegistry.register(ModEntities.SUMMON_SKELETON,
                Monster.createMonsterAttributes()
                        .add(Attributes.MOVEMENT_SPEED, 0.25D));

        FabricDefaultAttributeRegistry.register(ModEntities.SUMMON_WITHER_SKELETON,
                Monster.createMonsterAttributes()
                        .add(Attributes.MOVEMENT_SPEED, 0.25D));

        FabricDefaultAttributeRegistry.register(ModEntities.SUMMON_ZOMBIE,
                Monster.createMonsterAttributes()
                        .add(Attributes.MOVEMENT_SPEED, 0.18D)
                        .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D));
    }
}