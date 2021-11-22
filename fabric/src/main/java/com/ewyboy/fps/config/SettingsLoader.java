package com.ewyboy.fps.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

public class SettingsLoader {

    public static Settings CONFIG;

    public static void setup() {
        AutoConfig.register(Settings.class, GsonConfigSerializer :: new);
        CONFIG = AutoConfig.getConfigHolder(Settings.class).getConfig();
    }

}
