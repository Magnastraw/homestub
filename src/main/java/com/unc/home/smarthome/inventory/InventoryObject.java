package com.unc.home.smarthome.inventory;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unc.home.smarthome.AdditionalParameters;
import com.unc.home.requests.RequestObject;

import java.util.List;
import java.util.Map;
import java.util.Objects;


public class InventoryObject implements RequestObject {
    @JsonIgnore
    private long objectId;
    private String objectName;
    private String objectType;
    private String objectDescription;
    private List<String> supportedMetrics;
    private List<String> supportedEvents;
    private Map<String, AdditionalParameters> parameters;
    @JsonIgnore
    private long parentId;

    public InventoryObject() {
    }

    @JsonProperty
    public long getObjectId() {
        return objectId;
    }

    @JsonIgnore
    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getObjectDescription() {
        return objectDescription;
    }

    public void setObjectDescription(String objectDescription) {
        this.objectDescription = objectDescription;
    }

    public Map<String, AdditionalParameters> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, AdditionalParameters> parameters) {
        this.parameters = parameters;
    }

    @JsonProperty
    public long getParentId() {
        return parentId;
    }

    @JsonIgnore
    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    @JsonIgnore
    public List<String> getSupportedMetrics() {
        return supportedMetrics;
    }

    @JsonProperty
    public void setSupportedMetrics(List<String> supportedMetrics) {
        this.supportedMetrics = supportedMetrics;
    }

    @JsonIgnore
    public List<String> getSupportedEvents() {
        return supportedEvents;
    }

    @JsonProperty
    public void setSupportedEvents(List<String> supportedEvents) {
        this.supportedEvents = supportedEvents;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof InventoryObject)) {
            return false;
        }
        InventoryObject inventoryObject = (InventoryObject) o;
        return objectId == inventoryObject.objectId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectId);
    }

}
