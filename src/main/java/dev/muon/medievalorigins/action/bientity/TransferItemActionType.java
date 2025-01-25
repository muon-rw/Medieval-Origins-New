package dev.muon.medievalorigins.action.bientity;

import dev.muon.medievalorigins.action.ModBientityActionTypes;
import dev.muon.medievalorigins.entity.SummonedMob;
import io.github.apace100.apoli.action.ActionConfiguration;
import io.github.apace100.apoli.action.context.BiEntityActionContext;
import io.github.apace100.apoli.action.type.BiEntityActionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.NotNull;

public class TransferItemActionType extends BiEntityActionType {
    public static final TypedDataObjectFactory<TransferItemActionType> DATA_FACTORY = TypedDataObjectFactory.simple(
            new SerializableData(),
            data -> new TransferItemActionType(),
            (type, data) -> data.instance()
    );

    @Override
    public void accept(BiEntityActionContext context) {
        Entity actor = context.actor();
        Entity target = context.target();
        if (actor instanceof LivingEntity livingActor && target instanceof LivingEntity livingTarget) {
            ItemStack actorItem = livingActor.getMainHandItem().copy();

            if (!actorItem.isEmpty()) {
                if (target instanceof SummonedMob summon && livingActor.getUUID().equals(summon.getOwnerUUID())) {
                    EquipmentSlot slot = getTargetSlot(actorItem);
                    if (slot != null) {
                        ItemStack oldItem = livingTarget.getItemBySlot(slot).copy();
                        livingTarget.setItemSlot(slot, actorItem);

                        if (livingTarget instanceof Mob mob) {
                            mob.setDropChance(slot, 1.0f);
                            mob.setPersistenceRequired();
                        }

                        livingActor.setItemInHand(InteractionHand.MAIN_HAND, oldItem);
                        if (slot == EquipmentSlot.MAINHAND) {
                            summon.setWeapon(actorItem);
                        }
                    }
                }
            }
        }
    }

    private EquipmentSlot getTargetSlot(ItemStack stack) {
        if (stack.getItem() instanceof ArmorItem armorItem) {
            return armorItem.getEquipmentSlot();
        } else if (stack.getItem() instanceof BowItem ||
                stack.getItem() instanceof SwordItem ||
                stack.getItem() instanceof DiggerItem) {
            return EquipmentSlot.MAINHAND;
        }
        return null;
    }

    @Override
    public @NotNull ActionConfiguration<?> getConfig() {
        return ModBientityActionTypes.TRANSFER_ITEM;
    }
}