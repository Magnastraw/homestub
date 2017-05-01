package com.unc.home.smarthome.events;


import com.unc.home.generators.Generator;
import com.unc.home.smarthome.inventory.InventoryObject;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Event {
    private List<EventObject> eventObjectList;
    private List<InventoryObject> inventoryObjectList;
    private Map<String, Generator> generatorMap;
    private DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");


    public Event(List<InventoryObject> inventoryObjectList, Map<String, Generator> generatorMap) {
        this.eventObjectList = new ArrayList<>();
        this.inventoryObjectList = inventoryObjectList;
        this.generatorMap = generatorMap;
    }

    public List<EventObject> generateEvents() {
        InventoryObject inventoryObject = getRandomObject();
        for (String eventType : inventoryObject.getSupportedEvents()) {
            String event = (String)generatorMap.get(eventType).generate(inventoryObject);
            EventObject eventObject = new EventObject(
                    inventoryObject.getParentId(),
                    inventoryObject.getObjectId(),
                    ZonedDateTime.now().format(FORMATTER),
                    event,
                    getSeverity(event)
            );
            eventObjectList.add(eventObject);
        }
        return eventObjectList;
    }

    private InventoryObject getRandomObject() {
        List<InventoryObject> eventObjects = new ArrayList<>();
        for (InventoryObject inventoryObject : inventoryObjectList) {
            if (inventoryObject.getSupportedEvents().size() != 0 && inventoryObject.getObjectType().equals("Sensor")) {
                eventObjects.add(inventoryObject);
            }
        }
        return eventObjects.get(new Random().nextInt(eventObjects.size() - 1));
    }


    private String getSeverity(String event){
        switch (event){
            case "On":
                return "WARN";
            case "Off":
                return "NORMAL";
            case "Open":
                return "WARN";
            case "Close":
                return "NORMAL";
            default:
                return "CLEAR";
        }
    }

    public List<EventObject> getEventObjectList() {
        return eventObjectList;
    }

    public void setEventObjectList(List<EventObject> eventObjectList) {
        this.eventObjectList = eventObjectList;
    }
}
