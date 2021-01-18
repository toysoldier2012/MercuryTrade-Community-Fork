package com.mercury.platform.ui.misc;

public class Ratio {
    public static String getRatio(double d1, double d2) {
        while (Math.max(d1, d2) < Long.MAX_VALUE && d1 != (long) d1 && d2 != (long) d2) {
            d1 *= 10;
            d2 *= 10;
        }
        try {
            double gcd = getGCD(d1, d2);
            return ((long) (d1 / gcd)) + ":" + ((long) (d2 / gcd));
        } catch (StackOverflowError er) {
            throw new ArithmeticException("Irrational ratio: " + d1 + " to " + d2);
        }
    }

    private static double getGCD(double i1, double i2)
    {
        if (i1 == i2)
            return i1;//25
        if (i1 > i2)
            return getGCD(i1 - i2, i2);
        return getGCD(i1, i2 - i1);
    }
}