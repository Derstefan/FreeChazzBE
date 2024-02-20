package com.freechazz.game.core;

import java.util.Random;

public enum ESize {

    tiny(10, 10), small(15, 15), medium(20, 20), big(30, 30);

    int width;
    int height;
    private static final Random RANDOM = new Random();

    ESize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static ESize getRandomESize() {
        return values()[RANDOM.nextInt(values().length)];
    }
}
