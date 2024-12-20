package dev.muon.medievalorigins;

import dev.muon.medievalorigins.action.ModEntityActions;
import dev.muon.medievalorigins.action.ModBientityActions;
import dev.muon.medievalorigins.condition.ModBientityConditions;
import dev.muon.medievalorigins.condition.ModEntityConditions;
import dev.muon.medievalorigins.condition.ModItemConditions;
import dev.muon.medievalorigins.entity.ModEntities;
import dev.muon.medievalorigins.power.ModPowers;
import dev.muon.medievalorigins.sounds.ModSounds;
import net.fabricmc.api.ModInitializer;
import dev.muon.medievalorigins.enchantment.ModEnchantments;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MedievalOrigins implements ModInitializer {
	public static String MOD_ID = "medievalorigins";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	public static ResourceLocation loc(String id) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Loading Medieval Origins");

		ModEnchantments.register();
		ModEntities.register();
		ModEntityActions.register();
		ModBientityActions.register();
		ModItemConditions.register();
		ModEntityConditions.register();
		ModBientityConditions.register();
		ModSounds.register();
		ModPowers.register();
	}

}