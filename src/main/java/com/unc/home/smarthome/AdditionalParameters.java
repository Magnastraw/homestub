package com.unc.home.smarthome;

public class AdditionalParameters {
    private String value;
    private String type;

    public AdditionalParameters() {
    }

    public AdditionalParameters(String value, String type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
