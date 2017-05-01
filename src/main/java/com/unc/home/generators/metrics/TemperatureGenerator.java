package com.unc.home.generators.metrics;


import com.unc.home.Utils;
import com.unc.home.generators.Generator;
import com.unc.home.smarthome.inventory.InventoryObject;
import org.apache.commons.math3.util.Precision;
import org.springframework.core.env.Environment;

import java.util.concurrent.ThreadLocalRandom;

public class TemperatureGenerator implements Generator<Double> {
    private Environment environment;

    public TemperatureGenerator(Environment environment) {
        this.environment=environment;
    }

    @Override
    public Double generate(InventoryObject object) {
        if (!Utils.isAlarm) {
            return Precision.round(ThreadLocalRandom.current().nextDouble(Double.valueOf(environment.getProperty("normal.MIN_TEMPERATURE")),
                    Double.valueOf(environment.getProperty("normal.MAX_TEMPERATURE"))), 2);
        } else {
            return Precision.round(ThreadLocalRandom.current().nextDouble(Double.valueOf(environment.getProperty("critical.MIN_TEMPERATURE")),
                    Double.valueOf(environment.getProperty("critical.MAX_TEMPERATURE"))), 2);
        }
    }
}
