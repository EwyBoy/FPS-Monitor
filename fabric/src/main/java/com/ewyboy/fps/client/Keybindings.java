package com.ewyboy.fps.client;

import com.ewyboy.fps.FpsMonitor;
import com.ewyboy.fps.util.Translation;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Keybindings {

    private static KeyBinding fps;
    private static KeyBinding ping;
    private static KeyBinding memory;

    public static void setup() {
        initKeyBinding();
    }

    private static void initKeyBinding() {
        fps = new KeyBinding(Translation.Key.FPS, InputUtil.Type.KEYSYM, GLFW.GLFW_DONT_CARE, FpsMonitor.NAME);
        KeyBindingHelper.registerKeyBinding(fps);
        ping = new KeyBinding(Translation.Key.PING, InputUtil.Type.KEYSYM, GLFW.GLFW_DONT_CARE, FpsMonitor.NAME);
        KeyBindingHelper.registerKeyBinding(ping);
        memory = new KeyBinding(Translation.Key.MEMORY, InputUtil.Type.KEYSYM, GLFW.GLFW_DONT_CARE, FpsMonitor.NAME);
        KeyBindingHelper.registerKeyBinding(memory);
    }

}
