package com.kotb.game;

public class SoundManager {
    private static boolean isSoundEnabled = true;

    public static boolean isSoundEnabled() {
        return isSoundEnabled;
    }

    public static void toggleSound() {
        isSoundEnabled = !isSoundEnabled;
    }
}
