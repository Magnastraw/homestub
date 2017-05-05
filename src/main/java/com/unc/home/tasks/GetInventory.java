package com.unc.home.tasks;

import com.unc.home.HomestubApplication;
import com.unc.home.requests.HttpRequestManager;
import com.unc.home.smarthome.AdditionalParameters;
import com.unc.home.smarthome.inventory.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.Map;


public class GetInventory implements Task {
    private static final Logger LOG = LoggerFactory.getLogger(HomestubApplication.class);
    private Inventory inventory;
    private String houseId;

    public GetInventory(Inventory inventory, String houseId) {
        this.inventory = inventory;
        this.houseId = houseId;
    }

    @Override
    public void action(Map<String, AdditionalParameters> parameters) {
        HttpStatus status = HttpRequestManager.postRequestList(inventory.getInventoryObjectList(), "inventories", houseId);
        LOG.info("Get inventory: "+status.toString());

    }
}
