package com.ewyboy.fps.mixin;

import com.ewyboy.fps.config.Settings;
import com.ewyboy.fps.config.SettingsLoader;
import com.ewyboy.fps.util.Translation;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
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
				row += mc.textRenderer.fontHeight + (mc.textRenderer.fontHeight / 2);
				draw(stack, mc, entry, textPosX, textPosY, getTextColorAndAlpha(clientSettings.transparency, colors.get(index++)), clientSettings.shadow);
			}

			if (clientSettings.enableGameWindowInfo) updateTitle(mc, fps, memory, ping);

		}
	}

	private void updateTitle(MinecraftClient mc, String fps, String memory, String ping) {
		if (!FabricLoader.getInstance().isModLoaded("barista")) {
			mc.getWindow().setTitle("Minecraft " + SharedConstants.getGameVersion().getName()  + " | " + fps + " | " + memory + " | " + ping);
		}
	}

	private String getFps(MinecraftClient mc) {
		return formatText(mc.fpsDebugString.split("\\s+")[0], Translation.Display.FPS);
	}

	private String getPing(MinecraftClient mc) {
		PlayerListEntry entry = Objects.requireNonNull(mc.player).networkHandler.getPlayerListEntry(mc.player.getUuid());
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

	private int getTextColorAndAlpha(int alpha, int color) {
		return ((alpha & 0xFF) << 24) | color;
	}

	private String formatText(String text, String translation) {
		Text fpsString = new TranslatableText(translation, text);
		return fpsString.getString();
	}

	private String formatText(String text1, String text2, String text3, String translation) {
		Text fpsString = new TranslatableText(translation, text1, text2, text3);
		return fpsString.getString();
	}

	private void draw(MatrixStack stack, MinecraftClient mc, String text, float posX, float posY, int color, boolean shadow) {
		if (shadow) {
			mc.textRenderer.drawWithShadow(stack, text, posX, posY, color);
		} else {
			mc.textRenderer.draw(stack, text, posX, posY, color);
		}
	}
}
