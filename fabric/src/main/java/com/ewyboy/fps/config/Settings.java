package com.ewyboy.fps.config;


import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "fps")
public class Settings implements ConfigData {

    public Integer posX = 2;
    public Integer posY = 2;

    @ConfigEntry.BoundedDiscrete(min = 0, max = 255)
    public Integer transparency = 215;
    public Boolean shadow = true;

    @ConfigEntry.ColorPicker()
    public Integer fpsColor = 65450;
    public Boolean toggleFps = true;

    @ConfigEntry.ColorPicker()
    public Integer pingColor = 16733695;
    public Boolean togglePing = false;

    @ConfigEntry.ColorPicker()
    public Integer memoryColor = 16755200;
    public Boolean toggleMemory = false;

    public Boolean enableGameWindowInfo = false;

}