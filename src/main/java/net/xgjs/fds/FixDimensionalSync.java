package net.xgjs.fds;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FixDimensionalSync implements ModInitializer {
	public static final String MOD_ID = "fix_dimensional_sync";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("{} mod has been installed.", MOD_ID);
	}
}