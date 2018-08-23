package com.kwabenaberko.cointrack.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Kwabena Berko on 8/23/2018.
 */

public class Helpers {
    public static String round(double number) {
        NumberFormat numberFormat = new DecimalFormat("##.##");
        return (numberFormat.format(number));
    }
}
