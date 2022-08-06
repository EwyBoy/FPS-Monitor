package com.ewyboy.fps.cleint;

import com.ewyboy.fps.FpsMonitor;
import com.ewyboy.fps.config.Settings;
import com.ewyboy.fps.util.Translation;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class Keybindings {

    public static KeyMapping fps;
    public static KeyMapping ping;
    public static KeyMapping memory;
    public static KeyMapping all;

    @SubscribeEvent
    public static void onRegisterKeyBinds(RegisterKeyMappingsEvent event) {
        fps = new KeyMapping(Translation.Key.FPS, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_DONT_CARE, FpsMonitor.NAME);
        ping = new KeyMapping(Translation.Key.PING, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_DONT_CARE, FpsMonitor.NAME);
        memory = new KeyMapping(Translation.Key.MEMORY, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_DONT_CARE, FpsMonitor.NAME);
        all = new KeyMapping(Translation.Key.ALL, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_DONT_CARE, FpsMonitor.NAME);

        event.register(fps);
        event.register(ping);
        event.register(memory);
        event.register(all);
    }

    private static boolean toggleAll = false;

    @SubscribeEvent
    public void onKeyInput(InputEvent.Key event) {
        if(fps.consumeClick()) Settings.ClientSettings.toggle(Settings.CLIENT_SETTINGS.toggleFps);
        if(ping.consumeClick()) Settings.ClientSettings.toggle(Settings.CLIENT_SETTINGS.togglePing);
        if(memory.consumeClick()) Settings.ClientSettings.toggle(Settings.CLIENT_SETTINGS.toggleMemory);

        if(all.consumeClick()) {
            toggleAll = !toggleAll;

            if (Settings.CLIENT_SETTINGS.getToggleFps() != toggleAll) Settings.ClientSettings.toggle(Settings.CLIENT_SETTINGS.toggleFps);
            if (Settings.CLIENT_SETTINGS.getTogglePing() != toggleAll) Settings.ClientSettings.toggle(Settings.CLIENT_SETTINGS.togglePing);
            if (Settings.CLIENT_SETTINGS.getToggleMemory() != toggleAll) Settings.ClientSettings.toggle(Settings.CLIENT_SETTINGS.toggleMemory);

            Settings.ClientSettings.toggle(Settings.CLIENT_SETTINGS.toggleFps);
            Settings.ClientSettings.toggle(Settings.CLIENT_SETTINGS.togglePing);
            Settings.ClientSettings.toggle(Settings.CLIENT_SETTINGS.toggleMemory);
        }
    }

}
