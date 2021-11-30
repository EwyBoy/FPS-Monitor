package com.ewyboy.fps.cleint;

import com.ewyboy.fps.FpsMonitor;
import com.ewyboy.fps.config.Settings;
import com.ewyboy.fps.util.Translation;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

public class Keybindings {

    private static KeyMapping fps;
    private static KeyMapping ping;
    private static KeyMapping memory;
    private static KeyMapping all;

    public static void setup() {
        initKeyBinding();
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, Keybindings :: onKeyInput);
    }

    public static void initKeyBinding() {
        fps = new KeyMapping(Translation.Key.FPS, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_DONT_CARE, FpsMonitor.NAME);
        ClientRegistry.registerKeyBinding(fps);
        ping = new KeyMapping(Translation.Key.PING, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_DONT_CARE, FpsMonitor.NAME);
        ClientRegistry.registerKeyBinding(ping);
        memory = new KeyMapping(Translation.Key.MEMORY, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_DONT_CARE, FpsMonitor.NAME);
        ClientRegistry.registerKeyBinding(memory);
        all = new KeyMapping(Translation.Key.ALL, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_DONT_CARE, FpsMonitor.NAME);
        ClientRegistry.registerKeyBinding(all);
    }

    private static boolean toggleAll = false;

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
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
