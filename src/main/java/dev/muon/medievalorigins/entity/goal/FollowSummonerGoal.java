package dev.muon.medievalorigins.entity.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import dev.muon.medievalorigins.entity.SummonedMob;
import net.minecraft.world.level.pathfinder.PathType;

import java.util.EnumSet;
import java.util.Map;
import java.util.HashMap;

public class FollowSummonerGoal extends Goal {
    private final SummonedMob summon;
    private final PathNavigation navigation;
    private final double speedModifier;
    private final float stopDistance;
    private final float startDistance;
    private LivingEntity owner;
    private int timeToRecalcPath;
    private final Map<PathType, Float> oldPathMalus = new HashMap<>();

    public FollowSummonerGoal(SummonedMob summon, double speedModifier, float startDistance, float stopDistance) {
        this.summon = summon;
        this.speedModifier = speedModifier;
        this.startDistance = startDistance;
        this.stopDistance = stopDistance;
        this.navigation = summon.getSelfAsMob().getNavigation();
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity owner = summon.getOwner();
        if (owner == null || owner.isSpectator()) {
            return false;
        }

        this.owner = owner;
        return summon.getSelfAsMob().distanceToSqr(owner) >= (startDistance * startDistance);
    }

    @Override
    public boolean canContinueToUse() {
        return !navigation.isDone() &&
                owner != null &&
                summon.getSelfAsMob().distanceToSqr(owner) > (stopDistance * stopDistance);
    }

    @Override
    public void start() {
        timeToRecalcPath = 0;

        // Store and modify path malus values for better following
        for (PathType type : new PathType[]{PathType.WATER, PathType.DAMAGE_FIRE, PathType.DANGER_FIRE,
                PathType.DANGER_OTHER, PathType.LAVA}) {
            oldPathMalus.put(type, summon.getSelfAsMob().getPathfindingMalus(type));
            summon.getSelfAsMob().setPathfindingMalus(type, type.getMalus() * 0.5F);
        }
    }

    @Override
    public void stop() {
        owner = null;
        navigation.stop();

        // Restore original path malus values
        oldPathMalus.forEach((type, value) ->
                summon.getSelfAsMob().setPathfindingMalus(type, value)
        );
        oldPathMalus.clear();
    }

    @Override
    public void tick() {
        summon.getSelfAsMob().getLookControl().setLookAt(owner, 10.0F, summon.getSelfAsMob().getMaxHeadXRot());
        if (--timeToRecalcPath <= 0) {
            timeToRecalcPath = adjustedTickDelay(10);
            if (!summon.getSelfAsMob().isLeashed() && !summon.getSelfAsMob().isPassenger()) {
                navigation.moveTo(owner, speedModifier);
            }
        }
    }
}