package com.example.demo.service;

import com.example.demo.Config.NotificationEnum;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;

@Service
public class PushNotificationService {

    private static final String FIREBASE_SERVER_KEY = "AAAALpE5CE0:APA91bHrRbW6K-dM9-ZdHvr2N2i7fGDNf0o-WDyXOY5NiJNNP0U7z5nusQ_2fmM-tGeMud-0AosXx9yIWPB_lEZIlVKvcFgvNSbaBIu3r4njl0CIhrgcsP9aBqhVKAce5O38j1MeQSkx";
    private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

    public void sendNotification(String appToken, NotificationEnum notificationEnum, int orderId) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", "key=" + FIREBASE_SERVER_KEY);
            httpHeaders.set("Content-Type", "application/json;charset=UTF-8");
            JSONObject msg = new JSONObject();
            JSONObject json = new JSONObject();


            msg.put("title", notificationEnum.getTitle());
            msg.put("body", notificationEnum.getBody());
            msg.put("orderId", orderId);

            json.put("data", msg);

            json.put("to", appToken);


            HttpEntity<String> httpEntity = new HttpEntity<String>(json.toString(), httpHeaders);
            String response = restTemplate.postForObject(FIREBASE_API_URL, httpEntity, String.class);
            System.out.println(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
