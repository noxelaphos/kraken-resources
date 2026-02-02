package dev.krakenresources;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import dev.krakenresources.item.AncientCoinItem;

public class KrakenResources implements ModInitializer {
	public static final String MOD_ID = "kraken-resources";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Item ANCIENT_COIN = Registry.register(
			BuiltInRegistries.ITEM,
			new ResourceLocation(MOD_ID, "ancient_coin"),
			new AncientCoinItem(new Item.Properties().stacksTo(16))
	);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("krakening it up");
	}
}