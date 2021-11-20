package com.ewyboy.fps.mixin;

import com.ewyboy.fps.config.Settings;
import com.ewyboy.fps.config.SettingsLoader;
import com.ewyboy.fps.enums.TextColor;
import com.ewyboy.fps.util.Translation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mixin(InGameHud.class)
public class Display {

	@Inject(at = @At("TAIL"), method = "render")
	public void init(MatrixStack stack, float deltaTime, CallbackInfo info) {
		MinecraftClient mc = MinecraftClient.getInstance();

		if (mc.options.debugEnabled) {
			return;
		}

		List<String> entries = new ArrayList<>();
		Settings clientSettings = SettingsLoader.CONFIG;

		String fps = getFps(mc, clientSettings);
		String memory = getMemory(clientSettings);
		String ping = getPing(mc, clientSettings);

		if (fps != null) entries.add(fps);
		if (memory != null) entries.add(memory);
		if (ping != null) entries.add(ping);

		int row = 0;

		for (String entry : entries) {
			float textPosX = clampVertical(mc, clientSettings.posX, entry);
			float textPosY = clampHorizontal(mc, clientSettings.posY + row);
			row += mc.textRenderer.fontHeight + (mc.textRenderer.fontHeight / 2);
			draw(stack, mc, entry, textPosX, textPosY, getTextColorAndAlpha(clientSettings.transparency, clientSettings.fpsColor.getColor()), clientSettings.shadow);
		}
	}

	private String getFps(MinecraftClient mc, Settings clientSettings) {
		if (clientSettings.toggleFps) {
			return formatText(mc.fpsDebugString.split("\\s+")[0], Translation.Display.FPS, clientSettings.fpsColor);
		}
		return null;
	}

	private String getPing(MinecraftClient mc, Settings clientSettings) {
		if (clientSettings.togglePing) {
			PlayerListEntry entry = Objects.requireNonNull(mc.player).networkHandler.getPlayerListEntry(mc.player.getUuid());
			if (entry != null) {
				return formatText(String.valueOf(entry.getLatency()), Translation.Display.PING, clientSettings.pingColor);
			}
		}
		return null;
	}

	private String getMemory(Settings clientSettings) {
		if (clientSettings.toggleMemory) {
			int max = (int) (Runtime.getRuntime().maxMemory() / 1024 / 1024);
			int free = (int) (Runtime.getRuntime().freeMemory() / 1024 / 1024);
			int total = (int) (Runtime.getRuntime().totalMemory() / 1024 / 1024);

			int difference = total - free;

			return formatText(
					String.valueOf(difference * 100 / max),
					String.valueOf(difference),
					String.valueOf(max),
					Translation.Display.MEMORY,
					clientSettings.memoryColor
			);
		}

		return null;
	}

	private float clampVertical(MinecraftClient mc, float posX, String text) {
		float maxPosX = mc.getWindow().getScaledWidth() - mc.textRenderer.getWidth(text);
		posX = Math.min(posX, maxPosX);

		return posX;
	}

	private float clampHorizontal(MinecraftClient mc, float posY) {
		float maxPosY = mc.getWindow().getScaledHeight() - mc.textRenderer.fontHeight;
		posY = Math.min(posY, maxPosY);

		return posY;
	}

	private int getTextColorAndAlpha(int alpha) {
		return ((alpha & 0xFF) << 24) | 0xffffff;
	}

	private int getTextColorAndAlpha(int alpha, int color) {
		return ((alpha & 0xFF) << 24) | color;
	}

	private String formatText(String text, String translation, TextColor color) {
		Text fpsString = new TranslatableText(translation, text);
		return color + fpsString.getString();
	}

	private String formatText(String text1, String text2, String text3, String translation, TextColor color) {
		Text fpsString = new TranslatableText(translation, text1, text2, text3);
		return color + fpsString.getString();
	}

	private void draw(MatrixStack stack, MinecraftClient mc, String text, float posX, float posY, int color, boolean shadow) {
		if (shadow) {
			mc.textRenderer.drawWithShadow(stack, text, posX, posY, color);
		} else {
			mc.textRenderer.draw(stack, text, posX, posY, color);
		}
	}
}
