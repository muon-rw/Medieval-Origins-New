package dev.muon.medievalorigins.action.bientity;

import dev.muon.medievalorigins.action.ModBientityActionTypes;
import dev.muon.medievalorigins.entity.ISummon;
import io.github.apace100.apoli.action.ActionConfiguration;
import io.github.apace100.apoli.action.type.BiEntityActionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TransferItemActionType extends BiEntityActionType {
    public static final TypedDataObjectFactory<TransferItemActionType> DATA_FACTORY;

    @Override
    protected void execute(Entity actor, Entity target) {
        if (actor instanceof LivingEntity livingActor && target instanceof LivingEntity livingTarget) {
            ItemStack actorItem = livingActor.getMainHandItem().copy();
            ItemStack targetItem = livingTarget.getMainHandItem().copy();

            if (!actorItem.isEmpty() || !targetItem.isEmpty()) {
                boolean shouldTransfer;

                if (target instanceof ISummon summon) {
                    shouldTransfer = livingActor.getUUID().equals(summon.getOwnerUUID());
                    if (shouldTransfer) summon.setWeapon(actorItem);
                } else {
                    livingTarget.setItemInHand(InteractionHand.MAIN_HAND, actorItem);
                    shouldTransfer = true;
                }

                if (shouldTransfer) {
                    livingActor.setItemInHand(InteractionHand.MAIN_HAND, targetItem);
                }
            }
        }
    }

    @Override
    public @NotNull ActionConfiguration<?> getConfig() {
        return ModBientityActionTypes.TRANSFER_ITEM;
    }

    static {
        DATA_FACTORY = TypedDataObjectFactory.simple(
                new SerializableData(),
                data -> new TransferItemActionType(),
                (type, data) -> data.instance()
        );
    }
}