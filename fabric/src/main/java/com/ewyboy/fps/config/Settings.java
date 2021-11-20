package com.ewyboy.fps.config;

import com.ewyboy.fps.enums.TextColor;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

@Config(name = "fps")
public class Settings implements ConfigData {

    public Integer posX = 2;
    public Integer posY = 2;

    @ConfigEntry.BoundedDiscrete(min = 0, max = 255)
    public Integer transparency = 215;
    public Boolean shadow = true;

    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public TextColor fpsColor = TextColor.CYAN;
    public Boolean toggleFps = true;

    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public TextColor pingColor = TextColor.LIGHT_PURPLE;
    public Boolean togglePing = true;

    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public TextColor memoryColor = TextColor.GOLD;
    public Boolean toggleMemory = true;

}