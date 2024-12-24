package dev.muon.medievalorigins.entity;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerLevel;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SummonTracker {
    private static final Map<UUID, Set<SummonedMob>> OWNER_TO_SUMMONS = new ConcurrentHashMap<>();
    private static int cleanupTicks = 0;
    private static final int CLEANUP_INTERVAL = 200;

    public static void init() {

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof SummonedMob summon) {
                trackSummon(summon);
            }
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (++cleanupTicks >= CLEANUP_INTERVAL) {
                cleanupTicks = 0;
                cleanupInvalidSummons();
            }
        });

        ServerWorldEvents.UNLOAD.register((server, world) -> {
            OWNER_TO_SUMMONS.clear();
        });
    }
    public static void trackSummon(SummonedMob summon) {
        UUID ownerID = summon.getOwnerUUID();
        if (ownerID != null) {
            OWNER_TO_SUMMONS.computeIfAbsent(ownerID, k -> ConcurrentHashMap.newKeySet())
                    .add(summon);
        }
    }

    public static void untrackSummon(SummonedMob summon) {
        UUID ownerID = summon.getOwnerUUID();
        if (ownerID != null) {
            Set<SummonedMob> summons = OWNER_TO_SUMMONS.get(ownerID);
            if (summons != null) {
                summons.remove(summon);
                if (summons.isEmpty()) {
                    OWNER_TO_SUMMONS.remove(ownerID);
                }
            }
        }
    }

    public static Collection<SummonedMob> getSummonsForOwner(UUID ownerID) {
        return OWNER_TO_SUMMONS.getOrDefault(ownerID, Collections.emptySet());
    }

    public static void cleanupInvalidSummons() {
        OWNER_TO_SUMMONS.values().forEach(summons ->
                summons.removeIf(summon ->
                        !summon.getSelfAsMob().isAlive() ||
                                summon.getOwnerUUID() == null
                )
        );
    }
}