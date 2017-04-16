package com.unc.home.generator;


import org.apache.commons.math3.util.Precision;

import java.util.concurrent.ThreadLocalRandom;

public class TemperatureGenerator implements Generator {
    private final double MIN_TEMPERATURE = 15;
    private final double MAX_TEMPERATURE = 25;

    @Override
    public double generate() {
        return Precision.round(ThreadLocalRandom.current().nextDouble(MIN_TEMPERATURE, MAX_TEMPERATURE),2);
    }
}
