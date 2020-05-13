package com.cz.spring.common.utils;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


public class HttpUtil {

    public static String get(String url, MultiValueMap<String, String> params) {
        return get(url, params, null);
    }

  
    public static String get(String url, MultiValueMap<String, String> params, MultiValueMap<String, String> headers) {
        return request(url, params, headers, HttpMethod.GET);
    }

   
    public static String post(String url, MultiValueMap<String, String> params) {
        return post(url, params, null);
    }

  
    public static String post(String url, MultiValueMap<String, String> params, MultiValueMap<String, String> headers) {
        return request(url, params, headers, HttpMethod.POST);
    }

 
    public static String put(String url, MultiValueMap<String, String> params) {
        return put(url, params, null);
    }

 
    public static String put(String url, MultiValueMap<String, String> params, MultiValueMap<String, String> headers) {
        return request(url, params, headers, HttpMethod.PUT);
    }

 
    public static String delete(String url, MultiValueMap<String, String> params) {
        return delete(url, params, null);
    }

 
    public static String delete(String url, MultiValueMap<String, String> params, MultiValueMap<String, String> headers) {
        return request(url, params, headers, HttpMethod.DELETE);
    }

   
    public static String request(String url, MultiValueMap<String, String> params, MultiValueMap<String, String> headers, HttpMethod method) {
        if (params == null) {
            params = new LinkedMultiValueMap<>();
        }
        return request(url, params, headers, method, MediaType.APPLICATION_FORM_URLENCODED);
    }

   
    public static String request(String url, Object params, MultiValueMap<String, String> headers, HttpMethod method, MediaType mediaType) {
        if (url == null || url.trim().isEmpty()) {
            return null;
        }
        RestTemplate client = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null) {
            httpHeaders.addAll(headers);
        }
        httpHeaders.setContentType(mediaType);
        HttpEntity<Object> httpEntity = new HttpEntity(params, httpHeaders);
        ResponseEntity<String> response = client.exchange(url, method, httpEntity, String.class);
        return response.getBody();
    }
}