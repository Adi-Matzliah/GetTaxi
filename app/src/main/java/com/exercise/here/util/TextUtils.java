package com.exercise.here.util;

public class TextUtils {

    public TextUtils() {
    }

    public static String timeConversion(int seconds) {

        String result = "";
        final int MINUTES_IN_AN_HOUR = 60;
        final int SECONDS_IN_A_MINUTE = 60;

        int minutes = seconds / SECONDS_IN_A_MINUTE;
        seconds -= minutes * SECONDS_IN_A_MINUTE;

        int hours = minutes / MINUTES_IN_AN_HOUR;
        minutes -= hours * MINUTES_IN_AN_HOUR;

        if (hours > 0) result += hours + "H ";
        if (minutes > 0) result += minutes + "m ";
        if (seconds > 0) result += seconds + "s";
        return result;
    }
}
