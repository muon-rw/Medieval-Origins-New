package dev.muon.medievalorigins.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.cammiescorner.icarus.client.IcarusClient;
import dev.cammiescorner.icarus.util.IcarusHelper;
import dev.muon.medievalorigins.MedievalOrigins;
import dev.muon.medievalorigins.enchantment.ModEnchantments;
import dev.muon.medievalorigins.power.IcarusWingsPowerType;
import dev.muon.medievalorigins.power.PixieWingsPowerType;
import dev.muon.medievalorigins.util.ItemDataUtil;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.PowerReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.item.ArmorItem;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(IcarusClient.class)
public abstract class IcarusClientMixin {

    /*
     * TODO: Rewrite to be less invasive
     *  Use ModifyExpressionValue, target getArmorValue
     */
    @ModifyVariable(method = "onPlayerTick(Lnet/minecraft/world/entity/player/Player;)V",
            at = @At(value = "STORE", opcode = Opcodes.FSTORE),
            ordinal = 0)
    private static float modifyArmorModifier(float modifier, Player player) {
        var cfg = IcarusHelper.getConfigValues(player);
        int armorValueSum = 0;
        Iterable<ItemStack> armorItems = player.getArmorSlots();
        for (ItemStack armorItem : armorItems) {
            if (armorItem.isEmpty() || ItemDataUtil.getEnchantmentLevel(armorItem, ModEnchantments.FEATHERWEIGHT, player.level()) > 0) {
                continue;
            }
            if (armorItem.getItem() instanceof ArmorItem armor) {
                armorValueSum += armor.getDefense();
            }
        }
        return cfg.armorSlows() ? Math.max(1.0F, armorValueSum / 20.0F * cfg.maxSlowedMultiplier()) : 1.0F;
    }

    private static final PowerReference ICARUS_WINGS = PowerReference.of(MedievalOrigins.loc("icarus_wings"));
    private static final PowerReference PIXIE_WINGS = PowerReference.of(MedievalOrigins.loc("pixie_wings"));

    @ModifyReturnValue(method = "getWingsForRendering", at = @At(value = "RETURN"))
    private static ItemStack renderOriginWings(ItemStack original, LivingEntity entity) {
        var powerHolder = PowerHolderComponent.getOptional(entity);
        if (original.isEmpty()) {
            if (powerHolder.isPresent() && powerHolder.get().hasPower(ICARUS_WINGS)) {
                return ((IcarusWingsPowerType)powerHolder.get().getPowerType(ICARUS_WINGS.getPower())).getWingsType();
            }
        } else if (powerHolder.isPresent() && powerHolder.get().hasPower(PIXIE_WINGS)) {
            return new ItemStack(Items.AIR);
        }
        return original;
    }
}