package com.unc.home.smarthome;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.unc.home.requests.RequestObject;

import java.util.Map;

public class HomeParameters implements RequestObject {
    @JsonIgnore
    private String homeId;

    private Map<String,AdditionalParameters> parameters;

    public HomeParameters() {
    }

    public HomeParameters(String homeId, Map<String, AdditionalParameters> parameters) {
        this.homeId = homeId;
        this.parameters = parameters;
    }

    public String getHomeId() {
        return homeId;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    @JsonValue
    public Map<String, AdditionalParameters> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, AdditionalParameters> parameters) {
        this.parameters = parameters;
    }
}
