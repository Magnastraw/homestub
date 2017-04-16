package com.unc.home.smarthome;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unc.home.HomestubApplication;
import com.unc.home.requests.ScheduledTasks;
import com.unc.home.generator.Generator;
import com.unc.home.generator.HumidityGenerator;
import com.unc.home.generator.TemperatureGenerator;
import com.unc.home.generator.WaterGenerator;
import com.unc.home.smarthome.inventory.InventoryObject;
import com.unc.home.smarthome.metrics.Metric;
import com.unc.home.smarthome.metrics.MetricValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class Home {
    private static final Logger LOG = LoggerFactory.getLogger(HomestubApplication.class);
    private List<InventoryObject> inventoryObjects;
    private HomeParameters homeParameters;
    private List<Metric> metrics;
    private ObjectMapper objectMapper;
    private Map<Long, Generator> generatorMap;
   // private long secretKey;

    public Home() {
    }

    @PostConstruct
    public void init() throws IOException {
        this.inventoryObjects = new ArrayList<>();
        this.homeParameters = new HomeParameters();
        this.metrics = new ArrayList<>();
        this.objectMapper = new ObjectMapper();
        this.generatorMap = new HashMap<>();

        generatorMap.put(1L, new TemperatureGenerator());
        generatorMap.put(2L, new HumidityGenerator());
        generatorMap.put(3L, new WaterGenerator());
        try {
            getHomeParams(new File(this.getClass().getClassLoader().getResource("home").getFile()));
            getInventoryList(new File(this.getClass().getClassLoader().getResource("objects").getFile()));
            getMetricList(new File(this.getClass().getClassLoader().getResource("metrics").getFile()));
        } catch (NullPointerException ex) {
            LOG.error("File missing", ex);
        }
        Collections.sort(inventoryObjects);
    }

    private void getInventoryList(final File folder) throws IOException {
        try {
            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    getInventoryList(fileEntry);
                } else {
                    InventoryObject inventoryObject = objectMapper.readValue(fileEntry, InventoryObject.class);
                    inventoryObjects.add(inventoryObject);
                }
            }
        } catch (NullPointerException ex) {
            LOG.error("Empty folder", ex);
        }

    }

    private void getHomeParams(final File folder) throws IOException {
        try {
            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    getHomeParams(fileEntry);
                } else {
                    JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Map.class, String.class, AdditionalParameters.class);
                    Map<String, AdditionalParameters> map = objectMapper.readValue(fileEntry, javaType);
                    homeParameters = new HomeParameters(1, map);
                }
            }
        } catch (NullPointerException ex) {
            LOG.error("Empty folder", ex);
        }
    }

    private void getMetricList(final File folder) throws IOException {
        try {
            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    getMetricList(fileEntry);
                } else {
                    metrics = objectMapper.readValue(fileEntry, new TypeReference<List<Metric>>() {});
                }
            }
        } catch (NullPointerException ex) {
            LOG.error("Empty folder", ex);
        }
    }

    public List<InventoryObject> getInventoryObjects() {
        return inventoryObjects;
    }

    public void setInventoryObjects(List<InventoryObject> inventoryObjects) {
        this.inventoryObjects = inventoryObjects;
    }

    public HomeParameters getHomeParameters() {
        return homeParameters;
    }

    public void setHomeParameters(HomeParameters homeParameters) {
        this.homeParameters = homeParameters;
    }

    public List<Metric> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<Metric> metrics) {
        this.metrics = metrics;
    }


    @Scheduled(cron = "${cron.metric}")
    public void generateMetric() {
        if (inventoryObjects.size() != 0 && metrics.size() != 0) {
            for (Metric metric : metrics) {
                Double value = generatorMap.get(metric.getSpecId()).generate();
                String currentDate = LocalDateTime.now().toString();
                MetricValue metricValue = new MetricValue(value, currentDate, metric);
                ScheduledTasks.getMetricBuffer().add(metricValue);
            }
        }
    }
}
