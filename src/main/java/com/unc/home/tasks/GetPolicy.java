package com.unc.home.tasks;


import com.unc.home.smarthome.AdditionalParameters;
import com.unc.home.smarthome.inventory.Inventory;

import java.util.Map;


public class GetPolicy implements Task<String> {
    private Inventory inventory;
    private String houseId;

    public GetPolicy(Inventory inventory, String houseId) {
        this.inventory = inventory;
        this.houseId = houseId;
    }

    @Override
    public String action(Map<String, AdditionalParameters> parameters) {

        return null;
    }
}
