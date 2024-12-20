package dev.muon.medievalorigins.action.entity;

import dev.muon.medievalorigins.action.ModEntityActionTypes;
import dev.muon.medievalorigins.entity.SummonedMob;
import io.github.apace100.apoli.action.ActionConfiguration;
import io.github.apace100.apoli.action.BiEntityAction;
import io.github.apace100.apoli.action.EntityAction;
import io.github.apace100.apoli.action.type.EntityActionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.apoli.util.MiscUtil;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class SummonEntityActionType extends EntityActionType {
    public static final TypedDataObjectFactory<SummonEntityActionType> DATA_FACTORY = TypedDataObjectFactory.simple(
            new SerializableData()
                    .add("entity_type", SerializableDataTypes.ENTITY_TYPE)
                    .add("weapon", SerializableDataTypes.ITEM_STACK, null)
                    .add("duration", SerializableDataTypes.INT.optional(), Optional.empty())
                    .add("tag", SerializableDataTypes.NBT_COMPOUND, new CompoundTag())
                    .add("entity_action", EntityAction.DATA_TYPE.optional(), Optional.empty())
                    .add("bientity_action", BiEntityAction.DATA_TYPE.optional(), Optional.empty()),
            data -> new SummonEntityActionType(
                    data.get("entity_type"),
                    data.get("weapon"),
                    data.get("duration"),
                    data.get("tag"),
                    data.get("entity_action"),
                    data.get("bientity_action")
            ),
            (type, data) -> data.instance()
                    .set("entity_type", type.entityType)
                    .set("weapon", type.weapon)
                    .set("duration", type.duration)
                    .set("tag", type.entityNbt)
                    .set("entity_action", type.entityAction)
                    .set("bientity_action", type.biEntityAction)
    );

    private final EntityType<?> entityType;
    private final ItemStack weapon;
    private final Optional<Integer> duration;
    private final CompoundTag entityNbt;
    private final Optional<EntityAction> entityAction;
    private final Optional<BiEntityAction> biEntityAction;

    public SummonEntityActionType(EntityType<?> entityType, ItemStack weapon, Optional<Integer> duration,
                                  CompoundTag entityNbt, Optional<EntityAction> entityAction,
                                  Optional<BiEntityAction> biEntityAction) {
        this.entityType = entityType;
        this.weapon = weapon;
        this.duration = duration;
        this.entityNbt = entityNbt;
        this.entityAction = entityAction;
        this.biEntityAction = biEntityAction;
    }

    @Override
    protected void execute(Entity entity) {
        if (!(entity.level() instanceof ServerLevel serverWorld) || !(entity instanceof LivingEntity livingCaster)) {
            return;
        }

        Optional<Entity> entityToSpawn = MiscUtil.getEntityWithPassengers(
                serverWorld,
                entityType,
                entityNbt,
                entity.position(),
                entity.getYRot(),
                entity.getXRot()
        );

        if (entityToSpawn.isEmpty()) {
            return;
        }

        Entity actualEntityToSpawn = entityToSpawn.get();

        if (actualEntityToSpawn instanceof Mob mob) {
            DifficultyInstance difficulty = serverWorld.getCurrentDifficultyAt(mob.blockPosition());
            mob.finalizeSpawn(serverWorld, difficulty, MobSpawnType.MOB_SUMMONED, null);
            mob.setPersistenceRequired();
        }

        if (actualEntityToSpawn instanceof SummonedMob summon) {
            duration.ifPresentOrElse(
                    ticks -> {
                        summon.setLifeTicks(ticks);
                        summon.setIsLimitedLife(true);
                    },
                    () -> summon.setIsLimitedLife(false)
            );

            summon.setOwner(livingCaster);
            summon.setOwnerID(entity.getUUID());

            if (weapon != null) {
                summon.setWeapon(weapon);
            }
        }

        serverWorld.tryAddFreshEntityWithPassengers(actualEntityToSpawn);

        entityAction.ifPresent(action -> action.execute(actualEntityToSpawn));
        biEntityAction.ifPresent(action -> action.execute(entity, actualEntityToSpawn));
    }

    @Override
    public @NotNull ActionConfiguration<?> getConfig() {
        return ModEntityActionTypes.SUMMON_ENTITY;
    }

}