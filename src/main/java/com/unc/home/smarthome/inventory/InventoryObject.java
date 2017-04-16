package com.unc.home.smarthome.inventory;


import com.unc.home.smarthome.AdditionalParameters;
import com.unc.home.requests.RequestObject;

import java.util.Map;
import java.util.Objects;


public class InventoryObject implements Comparable<InventoryObject>,RequestObject {
    private long objectId;
    private String objectName;
    private String objectType;
    private Map<String, AdditionalParameters> parameters;
    private long parentId;

    public InventoryObject() {
    }

    public InventoryObject(long objectId, String objectName, String objectType, Map<String, AdditionalParameters> parameters, long parentId) {
        this.objectId = objectId;
        this.objectName = objectName;
        this.objectType = objectType;
        this.parentId = parentId;
        this.parameters = parameters;
    }

    public long getObjectId() {
        return objectId;
    }

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

    public Map<String, AdditionalParameters> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, AdditionalParameters> parameters) {
        this.parameters = parameters;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
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

    @Override
    public int compareTo(InventoryObject o) {
        return (objectId < o.objectId) ? -1 : (objectId > o.objectId) ? 1 : 0;

    }
}
