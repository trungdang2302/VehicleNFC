package com.example.demo.controller;

import com.example.demo.Config.NotificationEnum;
import com.example.demo.entities.Location;
import com.example.demo.entities.Order;
import com.example.demo.entities.User;
import com.example.demo.service.OrderService;
import com.example.demo.service.PushNotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = {"get-order/{id}"})
    public ResponseEntity<Optional<Order>> getTransactionById(@PathVariable("id") Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderById(id));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Optional<Order>> create(@RequestBody Order order) {
        Optional<Order> transaction1 = orderService.createOrder(order.getUserId(),order.getLocationId());
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction1);
    }


    @GetMapping(value = {"get-order"})
    public ResponseEntity<Optional<List<Order>>> getAllOrder(){
//        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderById(id));
        return null;
    }

    @GetMapping(value = "/abc")
    public void create() {
//        Optional<Order> transaction1 = orderService.createOrder(order.getUserId(),order.getLocationId());
//        return ResponseEntity.status(HttpStatus.CREATED).body(transaction1);
        PushNotificationService pushNotificationService = new PushNotificationService();
        pushNotificationService.sendNotification("dSkDRMJFVLE:APA91bH3-UwERNYB6zI4rS-eBRs6PJEvgNKrg0vf6ztUOGeVEkea6doodFbCVs_fJ79t5OVvr_cyeaF-TPCryUP2GtxqNZLjjpcsrYQ0EeALxYJdB47s-lAKwSYB-_5qvspoFbtdGEvS", NotificationEnum.CHECK_IN,17);
    }
}
