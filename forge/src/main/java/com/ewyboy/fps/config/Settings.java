package com.ewyboy.fps.config;

import com.ewyboy.fps.enums.EnumColor;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class Settings {

    public static final ForgeConfigSpec SETTING_SPEC;
    public static final Settings.ClientSettings CLIENT_SETTINGS;

    static {
        Pair<ClientSettings, ForgeConfigSpec> specPair = (new ForgeConfigSpec.Builder()).configure(Settings.ClientSettings :: new);
        SETTING_SPEC = specPair.getRight();
        CLIENT_SETTINGS = specPair.getLeft();
    }

    public static class ClientSettings {

        public final ForgeConfigSpec.ConfigValue<Boolean> displayFps;
        public final ForgeConfigSpec.ConfigValue<Integer> fpsPosX;
        public final ForgeConfigSpec.ConfigValue<Integer> fpsPosY;
        public final ForgeConfigSpec.ConfigValue<Integer> fpsTransparency;
        public final ForgeConfigSpec.ConfigValue<Boolean> fpsShadow;
        public final ForgeConfigSpec.EnumValue<EnumColor> fpsFormatting;

        public Boolean getDisplayFps() {
            return displayFps.get();
        }

        public Integer getFpsPosX() {
            return fpsPosX.get();
        }

        public Integer getFpsPosY() {
            return fpsPosY.get();
        }

        public Integer getFpsTransparency() {
            return fpsTransparency.get();
        }

        public Boolean getFpsShadow() {
            return fpsShadow.get();
        }

        public EnumColor getFpsFormatting() {
            return fpsFormatting.get();
        }

        public final ForgeConfigSpec.ConfigValue<Boolean> displayPing;
        public final ForgeConfigSpec.ConfigValue<Integer> pingPosX;
        public final ForgeConfigSpec.ConfigValue<Integer> pingPosY;
        public final ForgeConfigSpec.ConfigValue<Integer> pingTransparency;
        public final ForgeConfigSpec.ConfigValue<Boolean> pingShadow;
        public final ForgeConfigSpec.EnumValue<EnumColor> pingFormatting;

        public Boolean getDisplayPing() {
            return displayPing.get();
        }

        public Integer getPingPosX() {
            return pingPosX.get();
        }

        public Integer getPingPosY() {
            return pingPosY.get();
        }

        public Integer getPingTransparency() {
            return pingTransparency.get();
        }

        public Boolean getPingShadow() {
            return pingShadow.get();
        }

        public EnumColor getPingFormatting() {
            return pingFormatting.get();
        }

        public final ForgeConfigSpec.ConfigValue<Boolean> displayMemory;
        public final ForgeConfigSpec.ConfigValue<Integer> memoryPosX;
        public final ForgeConfigSpec.ConfigValue<Integer> memoryPosY;
        public final ForgeConfigSpec.ConfigValue<Integer> memoryTransparency;
        public final ForgeConfigSpec.ConfigValue<Boolean> memoryShadow;
        public final ForgeConfigSpec.EnumValue<EnumColor> memoryFormatting;

        public Boolean getDisplayMemory() {
            return displayMemory.get();
        }

        public Integer getMemoryPosX() {
            return memoryPosX.get();
        }

        public Integer getMemoryPosY() {
            return memoryPosY.get();
        }

        public Integer getMemoryTransparency() {
            return memoryTransparency.get();
        }

        public Boolean getMemoryShadow() {
            return memoryShadow.get();
        }

        public EnumColor getMemoryFormatting() {
            return memoryFormatting.get();
        }

        ClientSettings(ForgeConfigSpec.Builder builder) {
            builder.comment("FPS Monitor - Settings File");
            builder.push("fps");
                this.displayFps = builder
                    .comment("Display FPS")
                    .translation("fps.settings.fps.display_fps")
                    .define("display_fps", true
                );
                this.fpsPosX = builder
                    .comment("Sets the vertical")
                    .translation("fps.settings.fps.pos_x")
                    .define("pos_x", 2
                );
                this.fpsPosY = builder
                    .comment("Sets the y")
                    .translation("fps.settings.fps.posY")
                    .define("pos_y", 2
                );
                this.fpsTransparency = builder
                    .comment("Sets text color")
                    .translation("fps.settings.fps.transparency")
                    .defineInRange("transparency", 255, 0, 255
                );
                this.fpsShadow = builder
                    .comment("Sets text shadow")
                    .translation("fps.settings.fps.shadow")
                    .define("shadow", true
                );
                this.fpsFormatting = builder
                    .comment("Set text color")
                    .translation("fps.settings.fps.formatting")
                    .defineEnum("formatting", EnumColor.RED
                );
            builder.pop();

            builder.push("ping");
                this.displayPing = builder
                        .comment("Display Ping")
                        .translation("fps.settings.ping.display_ping")
                        .define("display_ping", true
                );
                this.pingPosX = builder
                        .comment("Sets the x")
                        .translation("fps.settings.ping.pos_x")
                        .define("pos_x", 2
                );
                this.pingPosY = builder
                        .comment("Sets the y")
                        .translation("fps.settings.ping.pos_y")
                        .define("pos_y", 12
                );
                this.pingTransparency = builder
                        .comment("Sets text color")
                        .translation("fps.settings.ping.transparency")
                        .defineInRange("transparency", 255, 0, 255
                );
                this.pingShadow = builder
                        .comment("Sets text shadow")
                        .translation("fps.settings.ping.shadow")
                        .define("shadow", true
                );
                this.pingFormatting = builder
                    .comment("Set text color")
                    .translation("fps.settings.ping.formatting")
                    .defineEnum("formatting", EnumColor.AQUA
                );
            builder.pop();

            builder.push("memory");
                this.displayMemory = builder
                    .comment("Display Ping")
                    .translation("fps.settings.memory.display_ping")
                    .define("display_ping", true
                    );
                this.memoryPosX = builder
                    .comment("Sets the x")
                    .translation("fps.settings.memory.pos_x")
                    .define("pos_x", 2
                    );
                this.memoryPosY = builder
                    .comment("Sets the y")
                    .translation("fps.settings.memory.pos_y")
                    .define("pos_y", 22
                    );
                this.memoryTransparency = builder
                    .comment("Sets text color")
                    .translation("fps.settings.memory.transparency")
                    .defineInRange("transparency", 255, 0, 255
                    );
                this.memoryShadow = builder
                    .comment("Sets text shadow")
                    .translation("fps.settings.memory.shadow")
                    .define("shadow", true
                    );
                this.memoryFormatting = builder
                    .comment("Set text color")
                    .translation("fps.settings.memory.formatting")
                    .defineEnum("formatting", EnumColor.GOLD
                );
            builder.pop();
        }
    }

    public static void setup() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Settings.SETTING_SPEC);
    }

}
