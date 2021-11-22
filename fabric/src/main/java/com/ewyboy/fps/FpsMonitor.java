package com.ewyboy.fps;

import com.ewyboy.fps.client.Keybindings;
import com.ewyboy.fps.config.SettingsLoader;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class FpsMonitor implements ClientModInitializer {

	public static final String MOD_ID = "fps";
	public static final String NAME = "FPS Monitor";

	@Override
	public void onInitializeClient() {
		Keybindings.setup();
		SettingsLoader.setup();
	}

}
