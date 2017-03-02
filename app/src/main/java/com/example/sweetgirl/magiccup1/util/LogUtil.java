package com.example.sweetgirl.magiccup1.util;

public class LogUtil {
    @SuppressWarnings("unchecked")
    public static String makeLogTag(Class cls) {
        return "MagicCup_" + cls.getSimpleName();
    }
}
