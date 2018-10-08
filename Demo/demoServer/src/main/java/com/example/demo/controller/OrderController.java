package com.example.demo.controller;

import com.example.demo.entities.Location;
import com.example.demo.entities.Order;
import com.example.demo.entities.User;
import com.example.demo.service.OrderService;
import com.example.demo.service.PushNotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping(value = "/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = {"/{id}"})
    public ResponseEntity<Optional<Order>> getTransactionById(@PathVariable("id") Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderById(id));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Optional<Order>> create(@RequestBody Order order) {
        Optional<Order> transaction1 = orderService.createOrder(order.getUserId(),order.getLocationId());
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction1);
    }

    @GetMapping(value = "/abc")
    public void create() {
//        Optional<Order> transaction1 = orderService.createOrder(order.getUserId(),order.getLocationId());
//        return ResponseEntity.status(HttpStatus.CREATED).body(transaction1);
        PushNotificationService pushNotificationService = new PushNotificationService();
        pushNotificationService.sendNotification("ejYnxbGWX0M:APA91bEdp1CYUzUO4FpZdrgtOd9Dt9Tv7gOLpuFI7F9yQAHFInc7enlBRIeFZC0i-1u_rHUagtGu5Y5NIPO3iv9z0a4zE_DRQyJBOi84d6aE1NjxoOujWuQd_djSj8Es6oCyXXRM11Hq");
    }
}
