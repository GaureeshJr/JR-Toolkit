package com.jrtk.utils;

import java.util.concurrent.ThreadLocalRandom;

public class Random {
    public static double Range(double min, double max)
    {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }
}
