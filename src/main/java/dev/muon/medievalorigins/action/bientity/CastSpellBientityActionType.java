package dev.muon.medievalorigins.action.bientity;

import dev.muon.medievalorigins.action.ModBientityActionTypes;
import dev.muon.medievalorigins.util.SpellCastingUtil;
import io.github.apace100.apoli.action.ActionConfiguration;
import io.github.apace100.apoli.action.type.BiEntityActionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.casting.SpellCast;
import net.spell_engine.utils.TargetHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CastSpellBientityActionType extends BiEntityActionType {
    public static final TypedDataObjectFactory<CastSpellBientityActionType> DATA_FACTORY;

    private final ResourceLocation spellId;
    private final boolean requireAmmo;

    public CastSpellBientityActionType(ResourceLocation spellId, boolean requireAmmo) {
        this.spellId = spellId;
        this.requireAmmo = requireAmmo;
    }

    @Override
    protected void execute(Entity actor, Entity target) {
        if (!(actor instanceof Player player) || target == null) {
            return;
        }

        ItemStack itemStack = player.getMainHandItem();

        SpellCastingUtil.setBypassesCooldown(true);
        SpellCast.Attempt attempt = SpellHelper.attemptCasting(player, itemStack, spellId, requireAmmo);
        if (!attempt.isSuccess()) {
            return;
        }

        SpellCastingUtil.setRequireAmmo(requireAmmo);
        try {
            if (actor instanceof ServerPlayer) {
                List<Entity> targets = List.of(target);
                SpellHelper.performSpell(player.level(), player, spellId, TargetHelper.SpellTargetResult.of(targets), SpellCast.Action.RELEASE, 1.0f);
            }
        } finally {
            SpellCastingUtil.setRequireAmmo(true);
        }
    }

    @Override
    public @NotNull ActionConfiguration<?> getConfig() {
        return ModBientityActionTypes.CAST_SPELL;
    }

    static {
        DATA_FACTORY = TypedDataObjectFactory.simple(
                new SerializableData()
                        .add("spell", SerializableDataTypes.IDENTIFIER)
                        .add("require_ammo", SerializableDataTypes.BOOLEAN, false),
                data -> new CastSpellBientityActionType(
                        data.getId("spell"),
                        data.getBoolean("require_ammo")
                ),
                (actionType, serializableData) -> serializableData.instance()
                        .set("spell", actionType.spellId)
                        .set("require_ammo", actionType.requireAmmo)
        );
    }
}