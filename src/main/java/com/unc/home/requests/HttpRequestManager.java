package com.unc.home.requests;


import com.unc.home.HomestubApplication;
import com.unc.home.UriUtils;
import com.unc.home.smarthome.HomeTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class HttpRequestManager {
    private static final Logger LOG = LoggerFactory.getLogger(HomestubApplication.class);
    private static RestTemplate restTemplate = new RestTemplate();

    public static void postRequestObject(RequestObject object, String endpoint, String houseId){
        HttpEntity<RequestObject> request = new HttpEntity<>(object);
        ResponseEntity<String> response = restTemplate
                .exchange(UriUtils.uriTemplate(endpoint,houseId), HttpMethod.POST, request, String.class);
        LOG.info(response.getStatusCode().name());
    }

    public static void postRequestList(List<? extends RequestObject> object, String endpoint, String houseId){
        HttpEntity<List<? extends RequestObject>> request = new HttpEntity<>(object);
        ResponseEntity<String> response = restTemplate
                .exchange(UriUtils.uriTemplate(endpoint,houseId), HttpMethod.POST, request, String.class);
        LOG.info(response.getStatusCode().name());
    }

    public static void putRequestList(List<? extends RequestObject> object, String endpoint, String houseId){
        HttpEntity<List<? extends RequestObject>> request = new HttpEntity<>(object);
        ResponseEntity<String> response = restTemplate
                .exchange(UriUtils.uriTemplate(endpoint,houseId), HttpMethod.PUT, request, String.class);
        LOG.info(response.getStatusCode().name());
    }

    public static List<HomeTask> getHomeTasks(String endpoint,String houseId){
        ResponseEntity<List<HomeTask>> response = restTemplate
                .exchange(UriUtils.uriTemplate(endpoint,houseId), HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<HomeTask>>() {
                });

        LOG.info(response.getStatusCode().name());
        return  response.getBody();
    }



}
