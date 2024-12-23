package dev.muon.medievalorigins.mixin;

import dev.muon.medievalorigins.power.MobsIgnorePowerType;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Unique
    private boolean shouldIgnoreTarget(Player player) {
        return PowerHolderComponent.hasPowerType(
                player,
                MobsIgnorePowerType.class,
                powerType -> powerType.shouldIgnore((LivingEntity)(Object)this, player)
        );
    }

    @Inject(method = "Lnet/minecraft/world/entity/LivingEntity;canAttack(Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At("HEAD"), cancellable = true)
    private void preventAttackValidation(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        if (target instanceof Player player && shouldIgnoreTarget(player)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "Lnet/minecraft/world/entity/LivingEntity;canAttack(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/ai/targeting/TargetingConditions;)Z", at = @At("HEAD"), cancellable = true)
    private void preventAttackValidationWithConditions(LivingEntity target, TargetingConditions condition, CallbackInfoReturnable<Boolean> cir) {
        if (target instanceof Player player && shouldIgnoreTarget(player)) {
            cir.setReturnValue(false);
        }
    }
}