package com.unc.home.smarthome.inventory;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unc.home.HomestubApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private long objectId=1;
    private List<InventoryObject> inventoryObjectList;
    private ObjectMapper objectMapper;
    private static final Logger LOG = LoggerFactory.getLogger(HomestubApplication.class);


    public Inventory(ObjectMapper objectMapper) {
        this.inventoryObjectList = new ArrayList<>();
        this.objectMapper = objectMapper;
    }

    public void buildInventoryFromDirectory(final File folder, long parentId) throws IOException {
        try {
            if (!isLeaf(folder)) {
                InventoryObject inventoryObject = getInventoryObjectFromFile(findController(folder), parentId);
                inventoryObjectList.add(inventoryObject);
                for (final File fileEntry : folder.listFiles()) {
                    if (fileEntry.isDirectory()) {
                        buildInventoryFromDirectory(fileEntry, inventoryObject.getObjectId());
                    }
                }
            } else {
                for (final File fileEntry : folder.listFiles()) {
                    inventoryObjectList.add(getInventoryObjectFromFile(fileEntry, parentId));
                }
            }
        } catch (NullPointerException ex) {
            LOG.error("Empty folder", ex);
        }

    }

    private InventoryObject getInventoryObjectFromFile(final File file, long parentId) throws IOException {
        InventoryObject inventoryObject = objectMapper.readValue(file, InventoryObject.class);
        inventoryObject.setObjectId(objectId++);
        inventoryObject.setParentId(parentId);
        return inventoryObject;
    }

    private File findController(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                return fileEntry;
            }
        }
        return null;
    }

    private boolean isLeaf(final File folder) {
        for (final File file : folder.listFiles()) {
            if (file.isDirectory()) {
                return false;
            }
        }
        return true;
    }

    public List<InventoryObject> getInventoryObjectList() {
        return inventoryObjectList;
    }

    public void setInventoryObjectList(List<InventoryObject> inventoryObjectList) {
        this.inventoryObjectList = inventoryObjectList;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }
}
