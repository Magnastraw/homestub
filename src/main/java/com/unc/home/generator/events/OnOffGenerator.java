package com.unc.home.generator.events;


import com.unc.home.generator.Generator;
import com.unc.home.requests.RequestObject;
import com.unc.home.smarthome.inventory.InventoryObject;

import java.util.HashMap;
import java.util.Map;

public class OnOffGenerator implements Generator<String> {
    private Map<RequestObject, String> valueMap = new HashMap<>();

    @Override
    public String generate(InventoryObject object) {
        if (!valueMap.containsKey(object)) {
            valueMap.put(object, "On");
        } else {
            valueMap.put(object, valueMap.get(object).equals("On") ? "Off" : "On" );
        }
        return valueMap.get(object);
    }
}
