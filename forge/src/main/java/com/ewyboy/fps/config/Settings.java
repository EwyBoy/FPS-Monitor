package com.ewyboy.fps.config;

import com.electronwill.nightconfig.core.Config;
import com.ewyboy.fps.enums.TextColor;
import com.ewyboy.fps.util.Translation;
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

        private final ForgeConfigSpec.ConfigValue<Integer> posX;
        private final ForgeConfigSpec.ConfigValue<Integer> posY;
        private final ForgeConfigSpec.ConfigValue<Integer> transparency;
        private final ForgeConfigSpec.ConfigValue<Boolean> shadow;

        public Integer getPosX() {
            return posX.get();
        }

        public Integer getPosY() {
            return posY.get();
        }

        public Integer getTransparency() {
            return transparency.get();
        }

        public Boolean getShadow() {
            return shadow.get();
        }

        public final ForgeConfigSpec.ConfigValue<Boolean> toggleFps;
        private final ForgeConfigSpec.EnumValue<TextColor> fpsColor;

        public Boolean getToggleFps() {
            return toggleFps.get();
        }

        public TextColor getFpsColor() {
            return fpsColor.get();
        }

        public final ForgeConfigSpec.ConfigValue<Boolean> togglePing;
        private final ForgeConfigSpec.EnumValue<TextColor> pingColor;

        public Boolean getTogglePing() {
            return togglePing.get();
        }

        public TextColor getPingColor() {
            return pingColor.get();
        }

        public final ForgeConfigSpec.ConfigValue<Boolean> toggleMemory;
        private final ForgeConfigSpec.EnumValue<TextColor> memoryColor;

        public Boolean getToggleMemory() {
            return toggleMemory.get();
        }

        public TextColor getMemoryColor() {
            return memoryColor.get();
        }

        public static void toggle(ForgeConfigSpec.ConfigValue<Boolean> booleanConfigValue) {
            booleanConfigValue.set(booleanConfigValue.get() ? Boolean.FALSE : Boolean.TRUE);
        }

        ClientSettings(ForgeConfigSpec.Builder builder) {
            builder.comment("FPS Monitor - Settings File");

            builder.push("display");
                this.posX = builder
                    .comment("Display Pos - Horizontal [0 = LEFT]")
                    .translation(Translation.Settings.Display.DISPLAY_POS_X)
                    .define("display_pos_x", 2
                );
                this.posY = builder
                    .comment("Display Pos - Vertical [0 = TOP]")
                    .translation(Translation.Settings.Display.DISPLAY_POS_Y)
                    .define("display_pos_y", 2
                );

                this.transparency = builder
                    .comment("Text Transparency [0 = INVISIBLE | 255 = SOLID]")
                    .translation(Translation.Settings.Display.TEXT_TRANSPARENCY)
                    .defineInRange("text_transparency", 215, 0, 255
                );
                this.shadow = builder
                    .comment("Text Shadow [Applies Drop-Shadow to Font]")
                    .translation(Translation.Settings.Display.HAS_TEXT_SHADOW)
                    .define("has_text_shadow", true
                );

            builder.pop();

            builder.push("components");

                builder.push("fps");
                    this.toggleFps = builder
                        .comment("Toggles FPS HUD")
                        .translation(Translation.Settings.Components.Fps.TOGGLE_FPS)
                        .define("toggle_fps", true
                    );
                    this.fpsColor = builder
                        .comment("Selects FPS Font Color")
                        .translation(Translation.Settings.Components.Fps.COLOR)
                        .defineEnum("color", TextColor.CYAN
                    );
                builder.pop();

                builder.push("ping");
                    this.togglePing = builder
                        .comment("Toggles Ping HUD")
                        .translation(Translation.Settings.Components.Ping.TOGGLE_PING)
                        .define("toggle_ping", false
                    );
                    this.pingColor = builder
                        .comment("Selects Ping Font Color")
                        .translation(Translation.Settings.Components.Ping.COLOR)
                        .defineEnum("color", TextColor.LIGHT_PURPLE
                    );
                builder.pop();

                builder.push("memory");
                    this.toggleMemory = builder
                        .comment("Toggles Memory HUD")
                        .translation(Translation.Settings.Components.Memory.TOGGLE_MEMORY)
                        .define("toggle_memory", false
                    );
                    this.memoryColor = builder
                        .comment("Selects Memory Font Color")
                        .translation(Translation.Settings.Components.Memory.COLOR)
                        .defineEnum("color", TextColor.GOLD
                    );
                builder.pop();

            builder.pop();
        }
    }

    public static void setup() {
        Config.setInsertionOrderPreserved(true);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Settings.SETTING_SPEC);
    }

}
