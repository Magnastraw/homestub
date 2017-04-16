package com.unc.home.generator;


import org.apache.commons.math3.util.Precision;

import java.util.concurrent.ThreadLocalRandom;

public class WaterGenerator implements Generator {
    private final double MIN_WATER_AMOUNT = 0.2;
    private final double MAX_WATER_AMOUNT = 0.5;

    @Override
    public double generate() {
        return Precision.round(ThreadLocalRandom.current().nextDouble(MIN_WATER_AMOUNT, MAX_WATER_AMOUNT),2);

    }
}
