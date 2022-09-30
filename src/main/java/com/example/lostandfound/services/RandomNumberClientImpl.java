package com.example.lostandfound.services;

import com.example.lostandfound.api.RandomNumberClient;
import com.example.lostandfound.utils.RandomUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class RandomNumberClientImpl implements RandomNumberClient {
    private final RestTemplate restTemplate;

    public RandomNumberClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public int getRandomNumber() {
        var result  = restTemplate.exchange("https://www.randomnumberapi.com/api/v1.0/random?min=1&max=1000&count=1",
                HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), new ParameterizedTypeReference<List<Integer>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        }).getBody();

        return result != null && result.size() > 0? result.get(0): RandomUtils.randomInteger();
    }
}
