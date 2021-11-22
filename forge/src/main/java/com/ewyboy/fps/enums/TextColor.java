package com.ewyboy.fps.enums;

public enum TextColor {

    BLACK("BLACK", '0', 0),
    DARK_BLUE("DARK_BLUE", '1', 170),
    DARK_GREEN("DARK_GREEN", '2', 43520),
    DARK_AQUA("DARK_AQUA", '3', 43690),
    CYAN("CYAN", 'x', 65450),
    DARK_RED("DARK_RED", '4', 11141120),
    DARK_PURPLE("DARK_PURPLE", '5', 11141290),
    GOLD("GOLD", '6', 16755200),
    GRAY("GRAY", '7', 11184810),
    DARK_GRAY("DARK_GRAY", '8', 5592405),
    BLUE("BLUE", '9', 5592575),
    GREEN("GREEN", 'a', 5635925),
    AQUA("AQUA", 'b', 5636095),
    RED("RED", 'c', 16733525),
    LIGHT_PURPLE("LIGHT_PURPLE", 'd', 16733695),
    YELLOW("YELLOW", 'e', 16777045),
    WHITE("WHITE", 'f', 16777215);

    private final String toString;

    private final Integer color;

    TextColor(String text, char code, Integer color) {
        this(code, color);
    }

    TextColor(char code, Integer color) {
        this.color = color;
        this.toString = "\u00a7" + code;
    }

    public Integer getColor() {
        return color;
    }

    public String toString() {
        return this.toString;
    }

}
