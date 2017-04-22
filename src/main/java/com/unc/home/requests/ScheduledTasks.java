package com.unc.home.requests;

import com.unc.home.smarthome.metrics.MetricValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private static List<MetricValue> metricBuffer = new ArrayList<>();

    @Value("${house.id}")
    private String houseId;

    public static List<MetricValue> getMetricBuffer() {
        return metricBuffer;
    }

    @Scheduled(cron = "${cron.hometasks}")
    public void getTasks() {
        HttpRequestManager.getRequest("house",houseId);
    }

    @Scheduled(cron = "${cron.metricbuffer}")
    public void sendMetrics(){
        HttpRequestManager.postRequestList(metricBuffer,"metrics",houseId);
        metricBuffer.clear();
    }


}