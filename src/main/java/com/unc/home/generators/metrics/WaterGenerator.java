package com.unc.home.generators.metrics;


import com.unc.home.generators.Generator;
import com.unc.home.requests.RequestObject;
import com.unc.home.smarthome.inventory.InventoryObject;
import org.apache.commons.math3.util.Precision;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class WaterGenerator implements Generator<Double> {
    private Map<RequestObject, Double> valueMap = new HashMap<>();
    private final double MIN_WATER_AMOUNT = 0.2;
    private final double MAX_WATER_AMOUNT = 0.5;

    @Override
    public Double generate(InventoryObject object) {
        Double value = Precision.round(ThreadLocalRandom.current().nextDouble(MIN_WATER_AMOUNT, MAX_WATER_AMOUNT), 2);
        if (!valueMap.containsKey(object)) {
            valueMap.put(object, value);
        } else {
            valueMap.put(object, valueMap.get(object) + value);
        }
        return valueMap.get(object);

    }
}
