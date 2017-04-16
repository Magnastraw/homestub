package com.unc.home.smarthome.metrics;

public class Metric {
    private long objectId;
    private long subobjectId;
    private long specId;

    public Metric() {
    }

    public Metric(long objectId, long subobjectId, long specId) {
        this.objectId = objectId;
        this.subobjectId = subobjectId;
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

    public long getSpecId() {
        return specId;
    }

    public void setSpecId(long specId) {
        this.specId = specId;
    }
}
