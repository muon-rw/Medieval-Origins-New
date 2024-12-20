package dev.muon.medievalorigins.action;

import dev.muon.medievalorigins.util.SpellCastingUtil;
import io.github.apace100.apoli.action.ActionConfiguration;
import io.github.apace100.apoli.action.type.EntityActionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.casting.SpellCast;
import net.spell_engine.utils.TargetHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CastSpellActionType extends EntityActionType {
    public static final TypedDataObjectFactory<CastSpellActionType> DATA_FACTORY;

    private final ResourceLocation spellId;
    private final boolean requireAmmo;
    private final String targetType;
    private final float range;

    public CastSpellActionType(ResourceLocation spellId, boolean requireAmmo, String targetType, float range) {
        this.spellId = spellId;
        this.requireAmmo = requireAmmo;
        this.targetType = targetType;
        this.range = range;
    }

    @Override
    protected void execute(Entity entity) {
        if (entity instanceof Player player) {
            ItemStack itemStack = player.getMainHandItem();

            SpellCastingUtil.setBypassesCooldown(true);
            SpellCast.Attempt attempt = SpellHelper.attemptCasting(player, itemStack, spellId, requireAmmo);
            if (!attempt.isSuccess()) {
                return;
            }

            SpellCastingUtil.setRequireAmmo(requireAmmo);
            try {
                if (entity instanceof ServerPlayer) {
                    List<Entity> targets = switch (targetType) {
                        case "area" ->
                                TargetHelper.targetsFromArea(player, range, new Spell.Release.Target.Area(), e -> e instanceof LivingEntity);
                        case "raycast" -> {
                            Entity target = TargetHelper.targetFromRaycast(player, range, e -> e instanceof LivingEntity);
                            yield target != null ? List.of(target) : List.of();
                        }
                        case "raycast_multiple" ->
                                TargetHelper.targetsFromRaycast(player, range, e -> e instanceof LivingEntity);
                        default -> List.of();
                    };

                    if (!targets.isEmpty()) {
                        SpellHelper.performSpell(player.level(), player, spellId, TargetHelper.SpellTargetResult.of(targets), SpellCast.Action.RELEASE, 1.0f);
                    }
                }
            } finally {
                SpellCastingUtil.setRequireAmmo(true);
            }
        }
    }

    @Override
    public @NotNull ActionConfiguration<?> getConfig() {
        return ModEntityActions.CAST_SPELL;
    }

    static {
        DATA_FACTORY = TypedDataObjectFactory.simple(
                new SerializableData()
                        .add("spell", SerializableDataTypes.IDENTIFIER)
                        .add("require_ammo", SerializableDataTypes.BOOLEAN, false)
                        .add("target_type", SerializableDataTypes.STRING, "raycast")
                        .add("range", SerializableDataTypes.FLOAT, 20.0f),
                data -> new CastSpellActionType(
                        data.getId("spell"),
                        data.getBoolean("require_ammo"),
                        data.getString("target_type"),
                        data.getFloat("range")
                ),
                (actionType, serializableData) -> serializableData.instance()
                        .set("spell", (actionType).spellId)
                        .set("require_ammo", (actionType).requireAmmo)
                        .set("target_type", (actionType).targetType)
                        .set("range", (actionType).range)
        );
    }
}