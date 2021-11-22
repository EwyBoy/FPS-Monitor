package com.ewyboy.fps.client;

import com.ewyboy.fps.FpsMonitor;
import com.ewyboy.fps.config.Settings;
import com.ewyboy.fps.config.SettingsLoader;
import com.ewyboy.fps.util.Translation;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Keybindings {

    private static KeyBinding fps;
    private static KeyBinding ping;
    private static KeyBinding memory;
    private static KeyBinding all;

    public static void setup() {
        initKeyBinding();
        clickEvent();
    }

    private static void initKeyBinding() {
        fps = new KeyBinding(Translation.Key.FPS, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, FpsMonitor.NAME);
        KeyBindingHelper.registerKeyBinding(fps);
        ping = new KeyBinding(Translation.Key.PING, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, FpsMonitor.NAME);
        KeyBindingHelper.registerKeyBinding(ping);
        memory = new KeyBinding(Translation.Key.MEMORY, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, FpsMonitor.NAME);
        KeyBindingHelper.registerKeyBinding(memory);
        all = new KeyBinding(Translation.Key.ALL, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, FpsMonitor.NAME);
        KeyBindingHelper.registerKeyBinding(all);
    }

    private static boolean toggleAll = false;

    private static void clickEvent() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            Settings clientSettings = SettingsLoader.CONFIG;

            while (fps.wasPressed()) clientSettings.toggleFps = !clientSettings.toggleFps;
            while (ping.wasPressed()) clientSettings.togglePing = !clientSettings.togglePing;
            while (memory.wasPressed()) clientSettings.toggleMemory = !clientSettings.toggleMemory;

            if(all.wasPressed()) {
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
