package com.ewyboy.fps.config;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;

public class SettingsLoader {

    public static Settings CONFIG;

    public static void setup() {
        AutoConfig.register(Settings.class, JanksonConfigSerializer :: new);
        CONFIG = AutoConfig.getConfigHolder(Settings.class).getConfig();
    }

}
