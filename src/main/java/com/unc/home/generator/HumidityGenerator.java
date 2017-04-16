package com.unc.home.generator;

import org.apache.commons.math3.util.Precision;

import java.util.concurrent.ThreadLocalRandom;

public class HumidityGenerator implements Generator{
    private final double MIN_HUMIDITY = 60;
    private final double MAX_HUMIDITY = 70;

    @Override
    public double generate() {
        return Precision.round(ThreadLocalRandom.current().nextDouble(MIN_HUMIDITY, MAX_HUMIDITY),2);
    }
}
