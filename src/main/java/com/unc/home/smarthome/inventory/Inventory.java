package com.unc.home.smarthome.inventory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unc.home.HomestubApplication;
import com.unc.home.requests.HttpRequestManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Inventory {
    private long objectId=1;
    private List<InventoryObject> inventoryObjectList;
    private ObjectMapper objectMapper;
    private static final Logger LOG = LoggerFactory.getLogger(HomestubApplication.class);


    public Inventory(ObjectMapper objectMapper) {
        this.inventoryObjectList = new CopyOnWriteArrayList<>();
        this.objectMapper = objectMapper;
    }

    public void buildInventoryFromDirectory(final File folder, long parentId) throws IOException {
        try {
            if (!isLeaf(folder)) {
                File object = findController(folder);
                InventoryObject inventoryObject = getInventoryObjectFromFile(object, parentId);
                inventoryObject.setDir(Paths.get(object.getAbsolutePath()));
                inventoryObjectList.add(inventoryObject);
                for (final File fileEntry : folder.listFiles()) {
                    if (fileEntry.isDirectory()) {
                        buildInventoryFromDirectory(fileEntry, inventoryObject.getObjectId());
                    }
                }
            } else {
                for (final File fileEntry : folder.listFiles()) {
                    InventoryObject inventoryObject = getInventoryObjectFromFile(fileEntry, parentId);
                    inventoryObject.setDir(Paths.get(fileEntry.getAbsolutePath()));
                    inventoryObjectList.add(inventoryObject);
                }
            }
        } catch (NullPointerException ex) {
            LOG.error("Empty folder", ex);
        }
    }

    public void addNewInventoryObject(Path parentDir,Path newObjDir) throws IOException {
        for(InventoryObject inventoryObject:inventoryObjectList){
            if(Paths.get(inventoryObject.getDir().toFile().getParent()).toAbsolutePath().equals(parentDir)){
                InventoryObject newInventoryObject = getInventoryObjectFromFile(newObjDir.toFile(), inventoryObject.getObjectId());
                newInventoryObject.setDir(newObjDir);
                inventoryObjectList.add(newInventoryObject);

                List<InventoryObject> tempInventoryList = new ArrayList<>();
                tempInventoryList.add(newInventoryObject);

                HttpRequestManager.postRequestList(tempInventoryList, "inventories", "1");
            }
        }
    }

    public void deleteObject(Path objDir){
        for(InventoryObject inventoryObject:inventoryObjectList) {
            if (inventoryObject.getDir().toAbsolutePath().equals(objDir)) {
                HttpRequestManager.deleteObject("inventories", "1",String.valueOf(inventoryObject.getObjectId()));
            }
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
