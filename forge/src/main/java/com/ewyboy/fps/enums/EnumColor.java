package com.ewyboy.fps.enums;

import com.google.common.collect.Lists;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public enum EnumColor {

    BLACK("BLACK", '0', 0, 0),
    DARK_BLUE("DARK_BLUE", '1', 1, 170),
    DARK_GREEN("DARK_GREEN", '2', 2, 43520),
    DARK_AQUA("DARK_AQUA", '3', 3, 43690),
    DARK_RED("DARK_RED", '4', 4, 11141120),
    DARK_PURPLE("DARK_PURPLE", '5', 5, 11141290),
    GOLD("GOLD", '6', 6, 16755200),
    GRAY("GRAY", '7', 7, 11184810),
    DARK_GRAY("DARK_GRAY", '8', 8, 5592405),
    BLUE("BLUE", '9', 9, 5592575),
    GREEN("GREEN", 'a', 10, 5635925),
    AQUA("AQUA", 'b', 11, 5636095),
    RED("RED", 'c', 12, 16733525),
    LIGHT_PURPLE("LIGHT_PURPLE", 'd', 13, 16733695),
    YELLOW("YELLOW", 'e', 14, 16777045),
    WHITE("WHITE", 'f', 15, 16777215);

    private static final Map<String, EnumColor> FORMATTING_BY_NAME = Arrays.stream(values()).collect(Collectors.toMap((targetColor) -> cleanName(targetColor.name), (enumColor) -> enumColor));
    private static final Pattern STRIP_FORMATTING_PATTERN = Pattern.compile("(?i)\u00a7[0-9A-FK-OR]");
    private final String name;
    private final char code;
    private final boolean isFormat;
    private final String toString;
    private final int id;

    @Nullable
    private final Integer color;

    private static String cleanName(String text) {
        return text.toLowerCase(Locale.ROOT).replaceAll("[^a-z]", "");
    }

    EnumColor(String text, char code, int id, @Nullable Integer color) {
        this(text, code, false, id, color);
    }

    EnumColor(String p_i46292_3_, char code, boolean isFormat) {
        this(p_i46292_3_, code, isFormat, -1, null);
    }

    EnumColor(String name, char code, boolean isFormat, int id, @Nullable Integer color) {
        this.name = name;
        this.code = code;
        this.isFormat = isFormat;
        this.id = id;
        this.color = color;
        this.toString = "\u00a7" + code;
    }

    public int getId() {
        return this.id;
    }

    public boolean isFormat() {
        return this.isFormat;
    }

    public boolean isColor() {
        return !this.isFormat;
    }

    @Nullable
    public Integer getColor() {
        return this.color;
    }

    public String getName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public String toString() {
        return this.toString;
    }

    @Nullable
    public static String stripFormatting(@Nullable String text) {
        return text == null ? null : STRIP_FORMATTING_PATTERN.matcher(text).replaceAll("");
    }

    @Nullable
    public static EnumColor getByName(@Nullable String name) {
        return name == null ? null : FORMATTING_BY_NAME.get(cleanName(name));
    }

    @Nullable
    public static EnumColor getById(int p_175744_0_) {
        for(EnumColor color : values()) {
            if (color.getId() == p_175744_0_) {
                return color;
            }
        }
        return null;
    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    public static EnumColor getByCode(char code) {
        char c0 = Character.toString(code).toLowerCase(Locale.ROOT).charAt(0);

        for(EnumColor color : values()) {
            if (color.code == c0) {
                return color;
            }
        }

        return null;
    }

    public static Collection<String> getNames(boolean isFormat, boolean bool) {
        List<String> list = Lists.newArrayList();

        for(EnumColor color : values()) {
            if ((!color.isColor() || isFormat) && (!color.isFormat() || bool)) {
                list.add(color.getName());
            }
        }

        return list;
    }

}
