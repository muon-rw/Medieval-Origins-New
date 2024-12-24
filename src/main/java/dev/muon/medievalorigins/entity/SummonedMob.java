package dev.muon.medievalorigins.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.UUID;

public interface SummonedMob extends OwnableEntity {
    int getLifeTicks();
    void setLifeTicks(int lifeTicks);
    void setIsLimitedLife(boolean bool);
    boolean isLimitedLife();
    int getMaxLifeTicks();
    void setWeapon(ItemStack item);
    void reassessWeaponGoal();
    boolean isOrderedToSit();
    void setOrderedToSit(boolean sitting);
    Level getWorld();
    Mob getSelfAsMob();

    @Override
    @Nullable
    UUID getOwnerUUID();

    void setOwnerID(UUID uuid);

    default void setOwner(LivingEntity owner) {
        setOwnerID(owner.getUUID());
    }

    default void setKillCredit(Entity target) {
        LivingEntity owner = getOwner();
        if (target instanceof LivingEntity livingTarget && owner instanceof Player player) {
            livingTarget.setLastHurtByPlayer(player);
        }
    }

    default LivingEntity getOwnerFromID() {
        try {
            UUID uuid = getOwnerUUID();
            return uuid == null ? null : getWorld().getPlayerByUUID(uuid);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}