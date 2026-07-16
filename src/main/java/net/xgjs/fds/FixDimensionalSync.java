package net.xgjs.fds;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FixDimensionalSync implements ModInitializer {
	public static final String MOD_ID = "fix_dimensional_sync";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("{} mod has been installed.", MOD_ID);
	}
}