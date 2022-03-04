package com.ewyboy.fps.mixin;

import com.ewyboy.fps.config.Settings;
import com.ewyboy.fps.config.SettingsLoader;
import com.ewyboy.fps.util.Translation;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.vertex.PoseStack;
import com.terraformersmc.modmenu.util.TranslationUtil;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mixin(Gui.class)
public class Display {

	@Inject(at = @At("TAIL"), method = "render")
	public void init(PoseStack stack, float deltaTime, CallbackInfo info) {
		Minecraft mc = Minecraft.getInstance();

		if (mc.options.renderDebug) {
			return;
		}

		Settings clientSettings = SettingsLoader.CONFIG;

		if (clientSettings.toggleFps || clientSettings.togglePing || clientSettings.toggleMemory || clientSettings.enableGameWindowInfo) {

			List<String> entries = new ArrayList<>();
			List<Integer> colors = new ArrayList<>();

			String fps = getFps(mc);
			String memory = getMemory();
			String ping = getPing(mc);

			if (clientSettings.toggleFps) { entries.add(fps);colors.add(clientSettings.fpsColor); }
			if (clientSettings.toggleMemory) { entries.add(memory); colors.add(clientSettings.memoryColor); }
			if (clientSettings.togglePing) { entries.add(ping); colors.add(clientSettings.pingColor); }

			int row = 0;
			int index = 0;

			for (String entry : entries) {
				float textPosX = clampVertical(mc, clientSettings.posX, entry);
				float textPosY = clampHorizontal(mc, clientSettings.posY + row);
				row += mc.font.lineHeight + (mc.font.lineHeight / 2);
				draw(stack, mc, entry, textPosX, textPosY, getTextColorAndAlpha(clientSettings.transparency, colors.get(index++)), clientSettings.shadow);
			}

			if (clientSettings.enableGameWindowInfo) updateTitle(mc, fps, memory, ping);

		}
	}

	private void updateTitle(Minecraft mc, String fps, String memory, String ping) {
		mc.getWindow().setTitle("Minecraft " + SharedConstants.getCurrentVersion().getName()  + " | " + fps + " | " + memory + " | " + ping);
	}

	private String getFps(Minecraft mc) {
		return formatText(mc.fpsString.split("\\s+")[0], Translation.Display.FPS);
	}

	private String getPing(Minecraft mc) {
		PlayerInfo entry = Objects.requireNonNull(mc.player).connection.getPlayerInfo(mc.player.getUUID());
		return entry != null ? formatText(String.valueOf(entry.getLatency()), Translation.Display.PING) : "";
	}

	private String getMemory() {
		int max = (int) (Runtime.getRuntime().maxMemory() / 1024 / 1024);
		int free = (int) (Runtime.getRuntime().freeMemory() / 1024 / 1024);
		int total = (int) (Runtime.getRuntime().totalMemory() / 1024 / 1024);

		int difference = total - free;

		return formatText(
				String.valueOf(difference * 100 / max),
				String.valueOf(difference),
				String.valueOf(max),
				Translation.Display.MEMORY
		);
	}

	private float clampVertical(Minecraft mc, float posX, String text) {
		float maxPosX = mc.getWindow().getGuiScaledWidth() - mc.font.width(text);
		posX = Math.min(posX, maxPosX);

		return posX;
	}

	private float clampHorizontal(Minecraft mc, float posY) {
		float maxPosY = mc.getWindow().getGuiScaledHeight() - mc.font.lineHeight;
		posY = Math.min(posY, maxPosY);

		return posY;
	}

	private int getTextColorAndAlpha(int alpha, int color) {
		return ((alpha & 0xFF) << 24) | color;
	}

	private String formatText(String text, String translation) {
		Component fpsString = new TranslatableComponent(translation, text);
		return fpsString.getString();
	}

	private String formatText(String text1, String text2, String text3, String translation) {
		Component fpsString = new TranslatableComponent(translation, text1, text2, text3);
		return fpsString.getString();
	}

	private void draw(PoseStack stack, Minecraft mc, String text, float posX, float posY, int color, boolean shadow) {
		if (shadow) {
			mc.font.drawShadow(stack, text, posX, posY, color);
		} else {
			mc.font.draw(stack, text, posX, posY, color);
		}
	}
}
