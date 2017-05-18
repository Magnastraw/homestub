package com.unc.home.generators.metrics;


import com.unc.home.Utils;
import com.unc.home.generators.Generator;
import com.unc.home.requests.RequestObject;
import com.unc.home.smarthome.inventory.InventoryObject;
import org.apache.commons.math3.util.Precision;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class PowerGenerator implements Generator<Double>{
    private Map<RequestObject, Double> valueMap = new HashMap<>();
    private Environment environment;

    public PowerGenerator(Environment environment) {
        this.environment=environment;
    }

    @Override
    public Double generate(InventoryObject object) {
            Double value = Precision.round(ThreadLocalRandom.current().nextDouble(Double.valueOf(environment.getProperty("normal.MIN_POWER")),
                    Double.valueOf(environment.getProperty("normal.MAX_POWER"))), 2);
            if (!valueMap.containsKey(object)) {
                valueMap.put(object, value);
            } else {
                valueMap.put(object, valueMap.get(object) + value);
            }
            return valueMap.get(object);
    }
}
