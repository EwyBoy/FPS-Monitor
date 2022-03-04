package com.ewyboy.fps.client;

import com.ewyboy.fps.FpsMonitor;
import com.ewyboy.fps.config.Settings;
import com.ewyboy.fps.config.SettingsLoader;
import com.ewyboy.fps.util.Translation;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class Keybindings {

    private static KeyMapping fps;
    private static KeyMapping ping;
    private static KeyMapping memory;
    private static KeyMapping all;

    public static void setup() {
        initKeyBinding();
        clickEvent();
    }

    private static void initKeyBinding() {
        fps = new KeyMapping(Translation.Key.FPS, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, FpsMonitor.NAME);
        KeyBindingHelper.registerKeyBinding(fps);
        ping = new KeyMapping(Translation.Key.PING, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, FpsMonitor.NAME);
        KeyBindingHelper.registerKeyBinding(ping);
        memory = new KeyMapping(Translation.Key.MEMORY, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, FpsMonitor.NAME);
        KeyBindingHelper.registerKeyBinding(memory);
        all = new KeyMapping(Translation.Key.ALL, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, FpsMonitor.NAME);
        KeyBindingHelper.registerKeyBinding(all);
    }

    private static boolean toggleAll = false;

    private static void clickEvent() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            Settings clientSettings = SettingsLoader.CONFIG;

            while (fps.consumeClick()) clientSettings.toggleFps = !clientSettings.toggleFps;
            while (ping.consumeClick()) clientSettings.togglePing = !clientSettings.togglePing;
            while (memory.consumeClick()) clientSettings.toggleMemory = !clientSettings.toggleMemory;

            if(all.consumeClick()) {
                toggleAll = !toggleAll;

                if (clientSettings.toggleFps != toggleAll) clientSettings.toggleFps = !clientSettings.toggleFps;
                if (clientSettings.togglePing != toggleAll) clientSettings.togglePing = !clientSettings.togglePing;
                if (clientSettings.toggleMemory != toggleAll) clientSettings.toggleMemory = !clientSettings.toggleMemory;

                clientSettings.toggleFps = !clientSettings.toggleFps;
                clientSettings.togglePing = !clientSettings.togglePing;
                clientSettings.toggleMemory = !clientSettings.toggleMemory;
            }

        });
    }

}
