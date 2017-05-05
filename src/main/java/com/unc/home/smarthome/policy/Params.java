package com.unc.home.smarthome.policy;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Params implements Serializable {
    private Map<String, Object> params;

    public Params() {
        params = new HashMap<>();
    }

    public Params(Map<String, Object> params) {
        this.params = params;
    }

    @JsonAnyGetter
    public Map<String, Object> getParams() {
        return params;
    }

    @JsonAnySetter
    public void setParam(String name, Object value) {
        this.params.put(name, value);
    }
}
