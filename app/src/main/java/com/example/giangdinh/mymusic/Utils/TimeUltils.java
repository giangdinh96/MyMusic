package com.example.giangdinh.mymusic.Utils;

/**
 * Created by GiangDinh on 18/01/2018.
 */

public class TimeUltils {
    public static String getMinuteFromDuration(int duration) {
        int minute = duration / 1000 / 60;
        return minute < 10 ? "0".concat(String.valueOf(minute)) : String.valueOf(minute);
    }

    public static String getResidualSecondFromDuration(int duration) {
        int residualSecond = duration / 1000 % 60;
        return residualSecond < 10 ? "0".concat(String.valueOf(residualSecond)) : String.valueOf(residualSecond);
    }

    public static String getTimeDuration(int duration) {
        return getMinuteFromDuration(duration).concat(":").concat(getResidualSecondFromDuration(duration));
    }
}
