package com.unc.home.generators.metrics;


import com.unc.home.generators.Generator;
import com.unc.home.smarthome.inventory.InventoryObject;
import org.apache.commons.math3.util.Precision;

import java.util.concurrent.ThreadLocalRandom;

public class TemperatureGenerator implements Generator<Double> {
    private final double MIN_TEMPERATURE = 15;
    private final double MAX_TEMPERATURE = 25;

    @Override
    public Double generate(InventoryObject object) {
        return Precision.round(ThreadLocalRandom.current().nextDouble(MIN_TEMPERATURE, MAX_TEMPERATURE),2);
    }
}
