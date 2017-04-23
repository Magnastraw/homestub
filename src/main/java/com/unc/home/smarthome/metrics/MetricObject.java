package com.unc.home.smarthome.metrics;


import com.unc.home.requests.RequestObject;

public class MetricObject implements RequestObject {
    private long objectId;
    private long subobjectId;
    private double value;
    private String registryDate;
    private long specId;

    public MetricObject() {
    }

    public MetricObject(long objectId, long subobjectId, double value, String registryDate, long specId) {
        this.objectId = objectId;
        this.subobjectId = subobjectId;
        this.value = value;
        this.registryDate = registryDate;
        this.specId = specId;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public long getSubobjectId() {
        return subobjectId;
    }

    public void setSubobjectId(long subobjectId) {
        this.subobjectId = subobjectId;
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

    public long getSpecId() {
        return specId;
    }

    public void setSpecId(long specId) {
        this.specId = specId;
    }
}
