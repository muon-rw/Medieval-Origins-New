package dev.muon.medievalorigins.action.bientity;

import dev.muon.medievalorigins.action.ModBientityActionTypes;
import io.github.apace100.apoli.action.ActionConfiguration;
import io.github.apace100.apoli.action.type.BiEntityActionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class RaycastBetweenActionType extends BiEntityActionType {
    private final ParticleOptions particle;
    private final double spacing;

    public static final TypedDataObjectFactory<RaycastBetweenActionType> DATA_FACTORY = TypedDataObjectFactory.simple(
            new SerializableData()
                    .add("particle", SerializableDataTypes.PARTICLE_EFFECT_OR_TYPE)
                    .add("spacing", SerializableDataTypes.DOUBLE, 0.5),
            data -> new RaycastBetweenActionType(
                    data.get("particle"),
                    data.getDouble("spacing")
            ),
            (type, data) -> data.instance()
                    .set("particle", type.particle)
                    .set("spacing", type.spacing)
    );

    public RaycastBetweenActionType(ParticleOptions particle, double spacing) {
        this.particle = particle;
        this.spacing = spacing;
    }

    @Override
    protected void execute(Entity actor, Entity target) {
        if (actor == null || target == null) {
            return;
        }

        double distance = actor.distanceTo(target);
        Vec3 direction = createDirectionVector(actor.position(), target.position());

        createParticlesAtHitPos(actor, new EntityHitResult(target));
    }

    protected Vec3 createDirectionVector(Vec3 pos1, Vec3 pos2) {
        return new Vec3(pos2.x() - pos1.x(), pos2.y() - pos1.y(), pos2.z() - pos1.z()).normalize();
    }

    protected void createParticlesAtHitPos(Entity entity, HitResult hitResult) {
        if (entity.level().isClientSide()) return;

        double distanceTo = hitResult.distanceTo(entity);

        for (double d = spacing; d < distanceTo; d += spacing) {
            double lerpValue = Mth.clamp(d / distanceTo, 0.0, 1.0);
            ((ServerLevel)entity.level()).sendParticles(
                    particle,
                    Mth.lerp(lerpValue, entity.getEyePosition().x(), hitResult.getLocation().x()),
                    Mth.lerp(lerpValue, entity.getEyePosition().y(), hitResult.getLocation().y()),
                    Mth.lerp(lerpValue, entity.getEyePosition().z(), hitResult.getLocation().z()),
                    1, 0, 0, 0, 0
            );
        }
    }

    @Override
    public @NotNull ActionConfiguration<?> getConfig() {
        return ModBientityActionTypes.RAYCAST_BETWEEN;
    }
}