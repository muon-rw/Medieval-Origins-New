package dev.muon.medievalorigins.entity;

import dev.muon.medievalorigins.entity.goal.FollowSummonerGoal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class SummonedSkeleton extends Skeleton implements SummonedMob {
    private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID =
            SynchedEntityData.defineId(SummonedSkeleton.class, EntityDataSerializers.OPTIONAL_UUID);

    private final RangedBowAttackGoal<AbstractSkeleton> bowGoal = new RangedBowAttackGoal<>(this, 1.0D, 20, 15.0F);
    private final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 1.2D, true) {
        public void stop() {
            super.stop();
            SummonedSkeleton.this.setAggressive(false);
        }

        public void start() {
            super.start();
            SummonedSkeleton.this.setAggressive(true);
        }
    };

    private boolean isLimitedLifespan;
    private int limitedLifeTicks;

    public SummonedSkeleton(EntityType<? extends Skeleton> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(OWNER_UUID, Optional.empty());
    }

    @Override
    public EntityType<?> getType() {
        return ModEntities.SUMMON_SKELETON;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new FollowSummonerGoal(this, 1.0, 9.0f, 3.0f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3.0F));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Mob.class, 8.0F));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Mob.class, 10, false, true,
                (entity) -> {
                    if (entity == null || entity.getKillCredit() == null) return false;
                    LivingEntity owner = getOwner();
                    return owner != null && entity.getKillCredit().equals(owner);
                }
        ));

        reassessWeaponGoal();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isLimitedLifespan && --this.limitedLifeTicks <= 0) {
            this.limitedLifeTicks = 20;
            this.hurt(getWorld().damageSources().starve(), 20.0F);
        }
    }

    @Override
    public PlayerTeam getTeam() {
        LivingEntity owner = this.getOwner();
        return owner != null ? owner.getTeam() : super.getTeam();
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        LivingEntity owner = this.getOwner();
        if (owner != null) {
            if (entity instanceof SummonedMob summon &&
                    summon.getOwnerUUID() != null &&
                    summon.getOwnerUUID().equals(this.getOwnerUUID())) {
                return true;
            }
            return entity == owner || owner.isAlliedTo(entity);
        }
        return super.isAlliedTo(entity);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("isLimited", this.isLimitedLifespan);
        if (this.isLimitedLifespan) {
            compound.putInt("LifeTicks", this.limitedLifeTicks);
        }
        UUID ownerUuid = this.getOwnerUUID();
        if (ownerUuid != null) {
            compound.putUUID("OwnerUUID", ownerUuid);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("isLimited")) {
            this.isLimitedLifespan = compound.getBoolean("isLimited");
        }
        if (compound.contains("LifeTicks")) {
            this.setLifeTicks(compound.getInt("LifeTicks"));
        }
        if (compound.hasUUID("OwnerUUID")) {
            this.setOwnerID(compound.getUUID("OwnerUUID"));
        }
    }

    @Override
    public void setLifeTicks(int lifeTicks) {
        this.limitedLifeTicks = lifeTicks;
    }

    @Override
    public int getTicksLeft() {
        return limitedLifeTicks;
    }

    @Override
    public void setIsLimitedLife(boolean bool) {
        this.isLimitedLifespan = bool;
    }

    @Override
    public void setWeapon(ItemStack item) {
        this.setItemSlot(EquipmentSlot.MAINHAND, item);
        this.reassessWeaponGoal();
    }

    @Override
    public void reassessWeaponGoal() {
        if (!this.level().isClientSide()) {
            this.goalSelector.removeGoal(meleeGoal);
            this.goalSelector.removeGoal(bowGoal);
            if (this.getMainHandItem().getItem() instanceof ProjectileWeaponItem) {
                this.goalSelector.addGoal(0, bowGoal);
            } else {
                this.goalSelector.addGoal(0, meleeGoal);
            }
        }
    }

    @Override
    public Level getWorld() {
        return this.level();
    }

    @Override
    public Mob getSelfAsMob() {
        return this;
    }

    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return this.entityData.get(OWNER_UUID).orElse(null);
    }

    @Override
    public void setOwnerID(UUID uuid) {
        this.entityData.set(OWNER_UUID, Optional.ofNullable(uuid));
    }

    @Override
    protected boolean shouldDropLoot() {
        return false;
    }

    @Override
    protected void populateDefaultEquipmentSlots(@NotNull RandomSource randomSource, @NotNull DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
    }

    @Override
    protected boolean isSunBurnTick() {
        return false;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty,
                                        @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        return spawnGroupData;
    }
}