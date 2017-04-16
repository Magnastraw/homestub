package com.unc.home;


import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.el.ELContext;

public class UriUtils {

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

}
