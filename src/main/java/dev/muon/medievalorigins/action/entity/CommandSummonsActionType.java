package dev.muon.medievalorigins.action.entity;

import dev.muon.medievalorigins.action.ModEntityActionTypes;
import dev.muon.medievalorigins.entity.SummonTracker;
import dev.muon.medievalorigins.entity.SummonedMob;
import io.github.apace100.apoli.action.ActionConfiguration;
import io.github.apace100.apoli.action.type.EntityActionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class CommandSummonsActionType extends EntityActionType {
    private final String command;

    public static final TypedDataObjectFactory<CommandSummonsActionType> DATA_FACTORY = TypedDataObjectFactory.simple(
            new SerializableData()
                    .add("command", SerializableDataTypes.STRING),
            data -> new CommandSummonsActionType(data.getString("command")),
            (type, data) -> data.instance()
                    .set("command", type.command)
    );

    public CommandSummonsActionType(String command) {
        this.command = command;
    }

    @Override
    protected void execute(Entity entity) {
        if (!(entity instanceof LivingEntity living)) return;

        Collection<SummonedMob> summons = SummonTracker.getSummonsForOwner(living.getUUID());

        switch (command.toLowerCase()) {
            case "sit" -> {
                summons.forEach(summon -> {
                    summon.setOrderedToSit(true);
                    if (entity instanceof Player player) {
                        player.displayClientMessage(Component.translatable("message.medievalorigins.summon.sit"), true);
                    }
                });
            }
            case "follow" -> {
                summons.forEach(summon -> {
                    summon.setOrderedToSit(false);
                    if (entity instanceof Player player) {
                        player.displayClientMessage(Component.translatable("message.medievalorigins.summon.follow"), true);
                    }
                });
            }
            case "come" -> {
                summons.forEach(summon -> {
                    summon.getSelfAsMob().teleportTo(entity.getX(), entity.getY(), entity.getZ());
                    if (entity instanceof Player player) {
                        player.displayClientMessage(Component.translatable("message.medievalorigins.summon.come"), true);
                    }
                });
            }
        }
    }

    @Override
    public @NotNull ActionConfiguration<?> getConfig() {
        return ModEntityActionTypes.COMMAND_SUMMONS;
    }
}