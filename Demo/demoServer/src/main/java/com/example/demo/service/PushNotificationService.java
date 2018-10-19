package com.example.demo.service;

import com.example.demo.Config.NotificationEnum;
import com.example.demo.entities.Order;
import com.example.demo.entities.OrderPricing;
import javassist.tools.web.BadHttpRequest;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.EOFException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public void sendNotificationToSendSms(String serverMachineToken, NotificationEnum notificationEnum, Order order) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", "key=" + FIREBASE_SERVER_KEY);
            httpHeaders.set("Content-Type", "application/json;charset=UTF-8");
            JSONObject msg = new JSONObject();
            JSONObject json = new JSONObject();


            msg.put("title", notificationEnum.getTitle());
            msg.put("body", composeOrderPrice(order, notificationEnum.getTitle()));
            msg.put("toPhoneNumber", order.getUserId().getPhoneNumber());

            json.put("data", msg);

            json.put("to", serverMachineToken);

            try {
                HttpEntity<String> httpEntity = new HttpEntity<String>(json.toString(), httpHeaders);
                String response = restTemplate.postForObject(FIREBASE_API_URL, httpEntity, String.class);
            } catch (Exception e) {
                System.err.println("Can not send notification");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendPhoneConfirmNotification(String appToken, String phoneNumber, String confirmKey) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", "key=" + FIREBASE_SERVER_KEY);
            httpHeaders.set("Content-Type", "application/json;charset=UTF-8");
            JSONObject msg = new JSONObject();
            JSONObject json = new JSONObject();

            msg.put("title", "Send confirm phone sms");
            msg.put("body", confirmKey);
            msg.put("phoneNumber", phoneNumber);

            json.put("data", msg);

            json.put("to", appToken);


            HttpEntity<String> httpEntity = new HttpEntity<String>(json.toString(), httpHeaders);
            String response = restTemplate.postForObject(FIREBASE_API_URL, httpEntity, String.class);
            System.out.println(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String composeOrderPrice(Order order, String title) {
        String body = "";
        if (order != null) {
            if (title.equals(NotificationEnum.CHECK_IN.getTitle())) {
                String pattern = "HH:mm dd-MM-yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                String date = simpleDateFormat.format(new Date(order.getCheckInDate()));

                body += "Bắt đầu đậu xe tại: " + order.getLocationId().getLocation() + "\n";
                body += "Vào lúc: " + date + "\n";
                body += "Bảng giá cho loại xe: " + order.getUserId().getVehicleTypeId().getName() + "\n";
                for (OrderPricing orderPricing : order.getOrderPricings()) {
                    body += (orderPricing.getFromHour() == 0) ? "Từ Giờ đầu: " + ((long) orderPricing.getPricePerHour() * 1000) + "đ/h\n"
                            : "Từ giờ thứ " + orderPricing.getFromHour() + ": " + ((long) orderPricing.getPricePerHour() * 1000) + "đ/h\n";
                }
            } else if (title.equals(NotificationEnum.CHECK_OUT.getTitle())) {
                String pattern = "HH:mm dd-MM-yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                String checkInDate = simpleDateFormat.format(new Date(order.getCheckInDate()));
                String checkOutDate = simpleDateFormat.format(new Date(order.getCheckOutDate()));

                body += "Kết thúc đậu xe tại: " + order.getLocationId().getLocation() + "\n";
                body += "Từ: " + checkInDate + " đến: " + checkOutDate + "\n";
                body += "Phí đậu xe: " + (long) (order.getTotal() * 1000) + "đ\n";
            }
        }
        return body;
    }


}
