package dev.muon.medievalorigins.condition.entity;

import dev.muon.medievalorigins.condition.ModEntityConditionTypes;
import io.github.apace100.apoli.condition.BlockCondition;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.context.BiEntityConditionContext;
import io.github.apace100.apoli.condition.context.EntityConditionContext;
import io.github.apace100.apoli.condition.type.EntityConditionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class CoveredByBlockConditionType extends EntityConditionType {
    private final BlockCondition blockCondition;

    public CoveredByBlockConditionType(BlockCondition blockCondition) {
        this.blockCondition = blockCondition;
    }

    public static final TypedDataObjectFactory<CoveredByBlockConditionType> DATA_FACTORY = TypedDataObjectFactory.simple(
            new SerializableData()
                    .add("block_condition", BlockCondition.DATA_TYPE),
            data -> new CoveredByBlockConditionType(
                    data.get("block_condition")
            ),
            (type, data) -> data.instance()
                    .set("block_condition", type.blockCondition)
    );

    @Override
    public boolean test(EntityConditionContext context) {
        Entity entity = context.entity();
        AABB boundingBox = entity.getBoundingBox();
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        BlockPos minPos = BlockPos.containing(boundingBox.minX + 0.001D, boundingBox.minY + 0.001D, boundingBox.minZ + 0.001D);
        BlockPos maxPos = BlockPos.containing(boundingBox.maxX - 0.001D, boundingBox.maxY - 0.001D, boundingBox.maxZ - 0.001D);

        // Check every block position that intersects with the entity's bounding box
        for (int x = minPos.getX(); x <= maxPos.getX(); x++) {
            for (int y = minPos.getY(); y <= maxPos.getY(); y++) {
                for (int z = minPos.getZ(); z <= maxPos.getZ(); z++) {
                    mutablePos.set(x, y, z);
                    // If any block position doesn't match the condition, the entity isn't fully covered
                    if (!blockCondition.test(entity.level(), mutablePos)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public @NotNull ConditionConfiguration<?> getConfig() {
        return ModEntityConditionTypes.COVERED_BY_BLOCK;
    }
}