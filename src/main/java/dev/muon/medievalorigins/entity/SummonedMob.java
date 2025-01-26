package dev.muon.medievalorigins.entity;

import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.TeamManager;
import net.fabricmc.loader.api.FabricLoader;
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

    default boolean isAlliedOwner(UUID otherOwnerId) {
        UUID myOwnerId = getOwnerUUID();
        if (myOwnerId == null || otherOwnerId == null) {
            return false;
        }

        if (myOwnerId.equals(otherOwnerId)) {
            return true;
        }

        if (FabricLoader.getInstance().isModLoaded("ftbteams")) {
            TeamManager manager = FTBTeamsAPI.api().getManager();
            if (manager.arePlayersInSameTeam(myOwnerId, otherOwnerId)) {
                return true;
            }
        }

        LivingEntity myOwner = getOwner();
        LivingEntity otherOwner = getWorld().getPlayerByUUID(otherOwnerId);
        return myOwner != null && otherOwner != null && myOwner.isAlliedTo(otherOwner);
    }

    default boolean isAllied(Entity entity) {
        LivingEntity owner = this.getOwner();
        if (owner == null) {
            return getSelfAsMob().isAlliedTo(entity);
        }

        if (entity == owner) {
            return true;
        }

        if (FabricLoader.getInstance().isModLoaded("ftbteams")) {
            TeamManager manager = FTBTeamsAPI.api().getManager();
            if (entity instanceof Player player && owner instanceof Player ownerPlayer) {
                if (manager.arePlayersInSameTeam(ownerPlayer.getUUID(), player.getUUID())) {
                    return true;
                }
            }
        }

        if (entity instanceof OwnableEntity ownable) {
            UUID otherOwnerId = ownable.getOwnerUUID();
            return isAlliedOwner(otherOwnerId);
        }

        return owner.isAlliedTo(entity);
    }
}