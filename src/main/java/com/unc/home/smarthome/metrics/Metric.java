package com.unc.home.smarthome.metrics;


import com.unc.home.generators.Generator;
import com.unc.home.smarthome.inventory.InventoryObject;
import org.springframework.core.env.Environment;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Metric {
    private List<MetricObject> metricObjectList;
    private List<InventoryObject> inventoryObjectList;
    private Environment environment;
    private Map<String, Generator> generatorMap;
    private DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public Metric(List<InventoryObject> inventoryObjectList, Environment environment, Map<String, Generator> generatorMap) {
        this.metricObjectList = new ArrayList<>();
        this.inventoryObjectList = inventoryObjectList;
        this.environment = environment;
        this.generatorMap = generatorMap;
    }

    public List<MetricObject> generateMetrics() {
        for (InventoryObject inventoryObject : inventoryObjectList) {
            if (inventoryObject.getSupportedMetrics().size()!=0 && inventoryObject.getObjectType().equals("Sensor")) {
                for (String metricType : inventoryObject.getSupportedMetrics()) {
                    MetricObject metricObject = new MetricObject(
                            inventoryObject.getParentId(),
                            inventoryObject.getObjectId(),
                            (Double) generatorMap.get(metricType).generate(inventoryObject),
                            ZonedDateTime.now().format(FORMATTER),
                            Long.valueOf(environment.getProperty("metric." + metricType))
                    );
                    metricObjectList.add(metricObject);
                }
            }
        }
        return metricObjectList;
    }

    public List<MetricObject> getMetricObjectList() {
        return metricObjectList;
    }

    public void setMetricObjectList(List<MetricObject> metricObjectList) {
        this.metricObjectList = metricObjectList;
    }
}
