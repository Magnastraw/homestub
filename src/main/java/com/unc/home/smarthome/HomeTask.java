package com.unc.home.smarthome;


import java.util.Map;

public class HomeTask {
    private String action;
    private Map<String,AdditionalParameters> parameters;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Map<String, AdditionalParameters> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, AdditionalParameters> parameters) {
        this.parameters = parameters;
    }
}
