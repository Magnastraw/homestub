package com.unc.home.requests;


import com.unc.home.HomestubApplication;
import com.unc.home.Utils;
import com.unc.home.smarthome.HomeParameters;
import com.unc.home.smarthome.HomeTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class HttpRequestManager {
    private static final Logger LOG = LoggerFactory.getLogger(HomestubApplication.class);
    private static RestTemplate restTemplate = new RestTemplate();

    public static HttpStatus postRequestObject(RequestObject object, String endpoint, String houseId) {
        try {
            HttpEntity<RequestObject> request = new HttpEntity<>(object);
            ResponseEntity<String> response = restTemplate
                    .exchange(Utils.uriTemplate(endpoint, houseId), HttpMethod.POST, request, String.class);
            return response.getStatusCode();
        } catch (HttpClientErrorException ex) {
            throw new HttpClientErrorException(ex.getStatusCode(), "Error during post object request");
        }
    }

    public static HttpStatus postRequestList(List<? extends RequestObject> object, String endpoint, String houseId) {
        try {
            HttpEntity<List<? extends RequestObject>> request = new HttpEntity<>(object);
            ResponseEntity<String> response = restTemplate
                    .exchange(Utils.uriTemplate(endpoint, houseId), HttpMethod.POST, request, String.class);
            return response.getStatusCode();
        } catch (HttpClientErrorException ex) {
            throw new HttpClientErrorException(ex.getStatusCode(), "Error during post list request");
        }
    }

    public static void putRequestList(List<? extends RequestObject> object, String endpoint, String houseId) {
        try {
            HttpEntity<List<? extends RequestObject>> request = new HttpEntity<>(object);
            ResponseEntity<String> response = restTemplate
                    .exchange(Utils.uriTemplate(endpoint, houseId), HttpMethod.PUT, request, String.class);
        } catch (HttpClientErrorException ex) {
            throw new HttpClientErrorException(ex.getStatusCode(), "Error during put list request");
        }
    }

    public static List<HomeTask> getHomeTasks(String endpoint, String houseId) {
        try {
            ResponseEntity<List<HomeTask>> response = restTemplate
                    .exchange(Utils.uriTemplate(endpoint, houseId), HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<HomeTask>>() {
                    });
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            throw new HttpClientErrorException(ex.getStatusCode(), "Error during get tasks request");
        }
    }

    public static void deleteObject(String endpoint, String houseId, String objectId) {
        try {
            ResponseEntity<String> response = restTemplate
                    .exchange(Utils.uriDeleteTemplate(endpoint, houseId, objectId), HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
        } catch (HttpClientErrorException ex) {
            throw new HttpClientErrorException(ex.getStatusCode(), "Error during delete object");
        }
    }

    public static List<HomeTask> postRequestHome(RequestObject object, String endpoint, String houseId) {
        try {
            HttpEntity<RequestObject> request = new HttpEntity<>(object);
            ResponseEntity<List<HomeTask>> response = restTemplate
                    .exchange(Utils.uriTemplate(endpoint, houseId), HttpMethod.POST, request, new ParameterizedTypeReference<List<HomeTask>>() {
                    });
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            throw new HttpClientErrorException(ex.getStatusCode(), "Error during post request home");
        }
    }
}
