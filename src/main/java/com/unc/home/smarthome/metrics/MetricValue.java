package com.unc.home.smarthome.metrics;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.unc.home.requests.RequestObject;

@JsonSerialize(using = MetricSerializer.class)
public class MetricValue implements RequestObject {
    private double value;
    private String registryDate;
    private Metric metric;

    public MetricValue(double value, String registryDate, Metric metric) {
        this.value = value;
        this.registryDate = registryDate;
        this.metric = metric;
    }

    public MetricValue() {
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getRegistryDate() {
        return registryDate;
    }

    public void setRegistryDate(String registryDate) {
        this.registryDate = registryDate;
    }

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }
}
