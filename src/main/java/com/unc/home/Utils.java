package com.unc.home;


import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.el.ELContext;

public class Utils {
    public static boolean isAlarm=false;

    public static String uriTemplate(String endpoint, String houseId){
        UriComponents uriComponents =
                UriComponentsBuilder.newInstance()
                        .scheme("http")
                        .host("localhost")
                        .port(8083)
                        .path("/{endpoint}")
                        .queryParam("houseId",houseId)
                        .build()
                        .expand(endpoint)
                        .encode();

        return uriComponents.toUriString();
    }

    public static String uriDeleteTemplate(String endpoint, String houseId,String objectId){
        UriComponents uriComponents =
                UriComponentsBuilder.newInstance()
                        .scheme("http")
                        .host("localhost")
                        .port(8083)
                        .path("/{endpoint}/{objectId}")
                        .queryParam("houseId",houseId)
                        .build()
                        .expand(endpoint,objectId)
                        .encode();

        return uriComponents.toUriString();
    }

}
