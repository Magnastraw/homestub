package com.unc.home.generators.metrics;

import com.unc.home.generators.Generator;
import com.unc.home.smarthome.inventory.InventoryObject;
import org.apache.commons.math3.util.Precision;

import java.util.concurrent.ThreadLocalRandom;

public class HumidityGenerator implements Generator<Double> {
    private final double MIN_HUMIDITY = 60;
    private final double MAX_HUMIDITY = 70;

    @Override
    public Double generate(InventoryObject object) {
        return Precision.round(ThreadLocalRandom.current().nextDouble(MIN_HUMIDITY, MAX_HUMIDITY),2);
    }
}
