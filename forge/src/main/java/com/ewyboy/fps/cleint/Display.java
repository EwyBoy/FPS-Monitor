package com.ewyboy.fps.cleint;

import com.ewyboy.fps.config.Settings;
import com.ewyboy.fps.util.Translation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Display {

    private static final Integer defaultColor = 0x00FFAA;

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            renderOverlay(event.getPoseStack());
        }
    }

    public void renderOverlay(PoseStack stack) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.options.renderDebug) {
            return;
        }

        Settings.ClientSettings clientSettings = Settings.CLIENT_SETTINGS;

        if (clientSettings.getToggleFps() || clientSettings.getTogglePing() || clientSettings.getToggleMemory() || clientSettings.getGameWindowInfo()) {
            List<String> entries = new ArrayList<>();

            String fps = getFps(mc);
            String memory = getMemory();
            String ping = getPing(mc);

            if (clientSettings.getToggleFps()) entries.add(clientSettings.getFpsColor() + fps);
            if (clientSettings.getToggleMemory()) entries.add(clientSettings.getMemoryColor() + memory);
            if (clientSettings.getTogglePing()) entries.add(clientSettings.getPingColor() + ping);

            int row = 0;

            for (String entry : entries) {
                float textPosX = clampVertical(mc, clientSettings.getPosX(), entry);
                float textPosY = clampHorizontal(mc, clientSettings.getPosY() + row);
                row += mc.font.lineHeight + (mc.font.lineHeight / 2);
                draw(stack, mc, entry, textPosX, textPosY, getTextColorAndAlpha(clientSettings.getTransparency(), defaultColor), clientSettings.getShadow());
            }

            if (clientSettings.getGameWindowInfo()) updateTitle(mc, fps, memory, ping);

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
        Component fpsString = Component.translatable(translation, text);
        return fpsString.getString();
    }

    private String formatText(String text1, String text2, String text3, String translation) {
        Component fpsString = Component.translatable(translation, text1, text2, text3);
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
