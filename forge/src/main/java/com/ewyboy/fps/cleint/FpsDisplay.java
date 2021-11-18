package com.ewyboy.fps.cleint;

import com.ewyboy.fps.config.Settings;
import com.ewyboy.fps.enums.EnumColor;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Objects;

public class FpsDisplay {

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            renderOverlay(event.getMatrixStack());
        }
    }

    public void renderOverlay(MatrixStack stack) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.options.renderDebug) {
            return;
        }

        Settings.ClientSettings clientSettings = Settings.CLIENT_SETTINGS;

        renderFps(stack, mc, clientSettings);
        renderPing(stack, mc, clientSettings);
        renderMemory(stack, mc, clientSettings);

    }

    private void renderFps(MatrixStack stack, Minecraft mc, Settings.ClientSettings clientSettings) {
        if (clientSettings.getDisplayFps()) {

            String formattedFps = formatText(mc.fpsString.split("\\s+")[0], "fps.display.fps", clientSettings.getFpsFormatting());

            float textPosX = clampVertical(mc, clientSettings.getFpsPosX(), formattedFps);
            float textPosY = clampHorizontal(mc, clientSettings.getFpsPosY());

            int color = getTextColorAndAlpha(clientSettings.getFpsTransparency());

            draw(stack, mc, formattedFps, textPosX, textPosY, color, clientSettings.getFpsShadow());
        }
    }

    private void renderPing(MatrixStack stack, Minecraft mc, Settings.ClientSettings clientSettings) {
        if (clientSettings.getDisplayPing()) {
            NetworkPlayerInfo entry = Objects.requireNonNull(mc.player).connection.getPlayerInfo(mc.player.getUUID());

            if (entry != null) {
                String formattedPing = formatText(String.valueOf(entry.getLatency()), "fps.display.ping", clientSettings.getPingFormatting());

                float textPosX = clampVertical(mc, clientSettings.getPingPosX(), formattedPing);
                float textPosY = clampHorizontal(mc, clientSettings.getPingPosY());

                int color = getTextColorAndAlpha(clientSettings.getPingTransparency());

                draw(stack, mc, formattedPing, textPosX, textPosY, color, clientSettings.getPingShadow());
            }
        }
    }

    private void renderMemory(MatrixStack stack, Minecraft mc, Settings.ClientSettings clientSettings) {
        if (clientSettings.getDisplayPing()) {

            int max = (int) (Runtime.getRuntime().maxMemory() / 1024 / 1024);
            int free = (int) (Runtime.getRuntime().freeMemory() / 1024 / 1024);
            int total = (int) (Runtime.getRuntime().totalMemory() / 1024 / 1024);

            int difference = total - free;

            String formattedMemory = formatText(
                    String.valueOf(difference * 100 / max),
                    String.valueOf(difference),
                    String.valueOf(max),
                    "fps.display.memory",
                    clientSettings.getMemoryFormatting()
            );

            float textPosX = clampVertical(mc, clientSettings.getMemoryPosX(), formattedMemory);
            float textPosY = clampHorizontal(mc, clientSettings.getMemoryPosY());

            int color = getTextColorAndAlpha(clientSettings.getMemoryTransparency());

            draw(stack, mc, formattedMemory, textPosX, textPosY, color, clientSettings.getMemoryShadow());
        }
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

    private int getTextColorAndAlpha(int alpha) {
        return ((alpha & 0xFF) << 24) | 0xffffff;
    }

    private String formatText(String text, String translation, EnumColor color) {
        ITextComponent fpsString = new TranslationTextComponent(translation, text);
        return color + fpsString.getString();
    }

    private String formatText(String text1, String text2, String text3, String translation, EnumColor color) {
        ITextComponent fpsString = new TranslationTextComponent(translation, text1, text2, text3);
        return color + fpsString.getString();
    }

    private void draw(MatrixStack stack, Minecraft mc, String text, float posX, float posY, int color, boolean shadow) {
        if (shadow) {
            mc.font.drawShadow(stack, text, posX, posY, color);
        } else {
            mc.font.draw(stack, text, posX, posY, color);
        }
    }

}
