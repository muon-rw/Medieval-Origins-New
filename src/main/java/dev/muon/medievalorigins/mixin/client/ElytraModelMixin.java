package dev.muon.medievalorigins.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.muon.medievalorigins.power.PixieWingsPowerType;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ElytraModel.class)
public abstract class ElytraModelMixin<T extends LivingEntity> {
   @WrapOperation(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/player/AbstractClientPlayer;elytraRotX:F"))
    private void modifyElytraRotX(AbstractClientPlayer player, float elytraRotX, Operation<Void> original) {
       if (PowerHolderComponent.hasPowerType(player, PixieWingsPowerType.class)) {
            elytraRotX += (0.8981317F - elytraRotX) * 0.1F;
        }
        original.call(player, elytraRotX);
    }

    @WrapOperation(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/player/AbstractClientPlayer;elytraRotY:F"))
    private void modifyElytraRotY(AbstractClientPlayer player, float elytraRotY, Operation<Void> original) {
        if (PowerHolderComponent.hasPowerType(player, PixieWingsPowerType.class))  {
            elytraRotY += (0.58726646F - elytraRotY) * 0.1F;
        }
        original.call(player, elytraRotY);
    }

    @WrapOperation(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/player/AbstractClientPlayer;elytraRotZ:F"))
    private void modifyElytraRotZ(AbstractClientPlayer player, float elytraRotZ, Operation<Void> original) {
        if (PowerHolderComponent.hasPowerType(player, PixieWingsPowerType.class)) {
            elytraRotZ += (-0.5F - (float)Math.PI/4F - elytraRotZ) * 0.1F;
        }
        original.call(player, elytraRotZ);
    }
}