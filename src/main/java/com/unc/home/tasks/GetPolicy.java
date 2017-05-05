package com.unc.home.tasks;


import com.unc.home.HomestubApplication;
import com.unc.home.requests.HttpRequestManager;
import com.unc.home.smarthome.AdditionalParameters;
import com.unc.home.smarthome.inventory.Inventory;
import com.unc.home.smarthome.policy.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


public class GetPolicy implements Task {
    private static final Logger LOG = LoggerFactory.getLogger(HomestubApplication.class);
    private List<Rule> ruleList;
    private String houseId;

    public GetPolicy(List<Rule> ruleList, String houseId) {
        this.ruleList = ruleList;
        this.houseId = houseId;
    }

    @Override
    public void action(Map<String, AdditionalParameters> parameters) {
        ruleList = HttpRequestManager.getRules("policies", houseId);
        LOG.info(ruleList.toString());
    }
}
