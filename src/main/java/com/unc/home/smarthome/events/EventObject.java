package com.unc.home.smarthome.events;


import com.unc.home.requests.RequestObject;

import java.util.Objects;

public class EventObject implements RequestObject {
    private long objectId;
    private long subobjectId;
    private String severity;
    private long eventType;
    private String registryDate;
    private String eventParameters;

    public EventObject() {
    }

    public EventObject(long objectId, long subobjectId,String registryDate, String eventParameters, String severity, long eventType ) {
        this.objectId = objectId;
        this.subobjectId = subobjectId;
        this.severity = severity;
        this.registryDate = registryDate;
        this.eventParameters = eventParameters;
        this.eventType = eventType;
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

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public long getEventType() {
        return eventType;
    }

    public void setEventType(long eventType) {
        this.eventType = eventType;
    }

    public String getRegistryDate() {
        return registryDate;
    }

    public void setRegistryDate(String registryDate) {
        this.registryDate = registryDate;
    }

    public String getEventParameters() {
        return eventParameters;
    }

    public void setEventParameters(String eventParameters) {
        this.eventParameters = eventParameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventObject that = (EventObject) o;
        return objectId == that.objectId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectId);
    }
}
