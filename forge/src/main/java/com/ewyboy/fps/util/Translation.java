package com.ewyboy.fps.util;

public class Translation {

    public static final class Display {
        public static final String FPS = "fps.display.fps";
        public static final String PING = "fps.display.ping";
        public static final String MEMORY = "fps.display.memory";
    }

    public static final class Key {
        public static final String FPS = "fps.key.toggle_fps";
        public static final String PING = "fps.key.toggle_ping";
        public static final String MEMORY = "fps.key.toggle_memory";
        public static final String ALL = "fps.key.toggle_all";
    }

    public static final class Settings {

        public static final class Display {
            public static final String DISPLAY_POS_X = "fps.settings.display.display_pos_x";
            public static final String DISPLAY_POS_Y = "fps.settings.display.display_pos_y";
            public static final String TEXT_TRANSPARENCY = "fps.settings.display.text_transparency";
            public static final String HAS_TEXT_SHADOW = "fps.settings.display.has_text_shadow";
            public static final String GAME_WINDOW_INFO = "fps.settings.display.game_window_info";
        }

        public static final class Components {

            public static final class Fps {
                public static final String TOGGLE_FPS = "fps.settings.components.fps.toggle_fps";
                public static final String COLOR = "fps.settings.components.fps.color";
            }
            public static final class Ping {
                public static final String TOGGLE_PING = "fps.settings.components.ping.toggle_ping";
                public static final String COLOR = "fps.settings.components.ping.color";
            }
            public static final class Memory {
                public static final String TOGGLE_MEMORY = "fps.settings.components.memory.toggle_memory";
                public static final String COLOR = "fps.settings.components.memory.color";
            }
        }
    }

}
