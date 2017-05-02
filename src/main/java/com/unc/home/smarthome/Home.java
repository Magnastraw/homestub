package com.unc.home.smarthome;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.unc.home.HomestubApplication;
import com.unc.home.generators.Generator;
import com.unc.home.generators.events.OnOffGenerator;
import com.unc.home.generators.events.OpenCloseGenerator;
import com.unc.home.generators.metrics.PowerGenerator;
import com.unc.home.requests.HttpRequestManager;
import com.unc.home.generators.metrics.HumidityGenerator;
import com.unc.home.generators.metrics.TemperatureGenerator;
import com.unc.home.generators.metrics.WaterFlowGenerator;
import com.unc.home.smarthome.events.Event;
import com.unc.home.smarthome.inventory.Inventory;
import com.unc.home.smarthome.metrics.Metric;
import com.unc.home.tasks.GetInventory;
import com.unc.home.tasks.GetPolicy;
import com.unc.home.tasks.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Component
@PropertySource("classpath:stub.properties")
public class Home {
    private static final Logger LOG = LoggerFactory.getLogger(HomestubApplication.class);
    private Inventory inventory;
    private HomeParameters homeParameters;
    private Metric metric;
    private Event event;
    private ObjectMapper objectMapper;
    private Map<String, Generator> generatorMap;
    private Map<String, Task> taskMap;
    private List<HomeTask> homeTaskList;
    private String houseId;

    @Autowired
    Environment env;

    public Home() {
    }

    @PostConstruct
    public void init() throws IOException {
        this.homeParameters = new HomeParameters();
        this.objectMapper = new ObjectMapper();
        this.homeTaskList = new ArrayList<>();
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.generatorMap = new HashMap<>();

        this.taskMap = new HashMap<>();

        generatorMap.put("Temperature", new TemperatureGenerator(env));
        generatorMap.put("Humidity", new HumidityGenerator(env));
        generatorMap.put("WaterFlow", new WaterFlowGenerator(env));
        generatorMap.put("Power", new PowerGenerator(env));
        generatorMap.put("On/off", new OnOffGenerator());
        generatorMap.put("Open/close", new OpenCloseGenerator());


        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter your key: ");
            houseId = scanner.next();
            scanner.close();

            this.inventory = new Inventory(objectMapper, houseId);

            getHomeParams(new File("src/main/resources/homes/home1/homeparams"));
            inventory.buildInventoryFromDirectory(new File("src/main/resources/homes/home1/objects"), 0);

            taskMap.put("GetPolicy", new GetPolicy(inventory, houseId));
            taskMap.put("GetInventory", new GetInventory(inventory, houseId));
            homeParameters.getParameters().put("Secret Key", new AdditionalParameters(houseId, "String"));

            homeTaskList=HttpRequestManager.postRequestHome(homeParameters, "house", houseId);
            if (homeTaskList != null) {
                for (HomeTask homeTask : homeTaskList) {
                    taskMap.get(homeTask.getAction()).action(homeTask.getParameters());
                }
            } else {
                LOG.info("No tasks");
            }

            this.metric = new Metric(inventory.getInventoryObjectList(), env, generatorMap);
            this.event = new Event(inventory.getInventoryObjectList(), generatorMap);
        } catch (NullPointerException ex) {
            LOG.error("File missing", ex);
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
                    homeParameters = new HomeParameters(houseId, map);
                }
            }
        } catch (NullPointerException ex) {
            LOG.error("Empty folder", ex);
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public HomeParameters getHomeParameters() {
        return homeParameters;
    }

    public void setHomeParameters(HomeParameters homeParameters) {
        this.homeParameters = homeParameters;
    }

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    @Scheduled(cron = "${cron.hometasks}")
    public void getTasks() {
        homeTaskList = HttpRequestManager.getHomeTasks("house", houseId);
        if (homeTaskList != null) {
            for (HomeTask homeTask : homeTaskList) {
                taskMap.get(homeTask.getAction()).action(homeTask.getParameters());
            }
        } else {
            LOG.info("No tasks");
        }
    }

    @Scheduled(cron = "${cron.event}")
    public void generateEvents() {
        if (HttpRequestManager.postRequestList(event.generateEvents(), "event", houseId) == HttpStatus.OK)
            event.getEventObjectList().clear();
    }

    @Scheduled(cron = "${cron.metric}")
    public void generateMetric() {
        if (HttpRequestManager.postRequestList(metric.generateMetrics(), "metrics", houseId) == HttpStatus.OK)
            metric.getMetricObjectList().clear();
    }
}
