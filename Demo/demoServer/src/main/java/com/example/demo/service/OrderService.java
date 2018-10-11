package com.example.demo.service;

import com.example.demo.Config.NotificationEnum;
import com.example.demo.Config.OrderStatusEnum;
import com.example.demo.entities.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final OrderPricingRepository orderPricingRepository;
    private final PolicyHasVehicleTypeRepository policyHasVehicleTypeRepository;
    private final PricingRepository pricingRepository;

    public OrderService(OrderRepository orderRepository, OrderStatusRepository orderStatusRepository, UserRepository userRepository, LocationRepository locationRepository, OrderPricingRepository orderPricingRepository, PolicyHasVehicleTypeRepository policyHasVehicleTypeRepository, PricingRepository pricingRepository) {
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.orderPricingRepository = orderPricingRepository;
        this.policyHasVehicleTypeRepository = policyHasVehicleTypeRepository;
        this.pricingRepository = pricingRepository;
    }

    public Optional<Order> getOrderById(Integer id) {
        Order order = orderRepository.findById(id).get();
        order.setOrderPricings(orderPricingRepository.findByOrderId(order.getId()));
        return Optional.of(order);
    }

    public Optional<Order> createOrder(User checkInUser, Location location) {
        String userToken = checkInUser.getDeviceToken();
        checkInUser = userRepository.findById(checkInUser.getId()).get();
        location = locationRepository.findById(location.getId()).get();
        OrderStatus orderStatus = orderStatusRepository.findByName(OrderStatusEnum.Open.getName()).get();
        //TODO kiểm tra thằng lồn đó có order nào open không để check out
        Order order = null;
        try {
            order = orderRepository.findByUserIdAndLocationIdAndOrderStatusId(checkInUser,
                    location, orderStatus).get();
        } catch (NoSuchElementException e) {

        }

        if (order != null) {
            checkOutOrder(order, userToken);
            return Optional.of(order);
        }

        //Code here

        order = new Order();
        order.setOrderStatusId(orderStatus);


        order.setUserId(checkInUser);
        order.setLocationId(locationRepository.findById(location.getId()).get());

        order.setCheckInDate(new Date().getTime());

        //TODO kiểm tra thời điểm hiện tại để chọn policy
        Policy policy = order.getLocationId().getPolicyList().get(0);

        order.setAllowedParkingFrom(policy.getAllowedParkingFrom());
        order.setAllowedParkingTo(policy.getAllowedParkingTo());


        PolicyHasTblVehicleType policyHasTblVehicleType = policyHasVehicleTypeRepository
                .findByPolicyIdAndVehicleTypeId(policy, checkInUser.getVehicleTypeId()).get();

        List<Pricing> pricings = pricingRepository.findAllByPolicyHasTblVehicleTypeId(policyHasTblVehicleType);

        orderRepository.save(order);

        List<OrderPricing> orderPricings = OrderPricing.convertListPricingToOrderPricing(pricings);
        for (OrderPricing orderPricing : orderPricings
                ) {
            orderPricing.setOrderId(order.getId());
            orderPricingRepository.save(orderPricing);
        }

        //TODO kiểm tra thằng lồn đó xài gì để gửi SMS hay Noti
        //Code here
        PushNotificationService pushNotificationService = new PushNotificationService();
        pushNotificationService.sendNotification(userToken, NotificationEnum.CHECK_IN, order.getId());

        return Optional.of(order);
    }

    public Optional<Order> checkOutOrder(Order order, String userToken) {
        order.setCheckOutDate(new Date().getTime());
        TimeDuration duration = TimeService.compareTwoDates(order.getCheckInDate(), order.getCheckOutDate());
        //TODO kiểm tra có bị lố giờ để phạt tiền thêm
        //code here


//
//        hourHasPrices = HourHasPrice.sort(hourHasPrices);

        double totalPrice = 0;
        int totalHour = duration.getHour();
        int totalMinute = duration.getMinute();

        List<HourHasPrice> hourHasPrices = new ArrayList<>();
        while (totalHour > 0) {
            hourHasPrices.add(new HourHasPrice(totalHour, null));
            totalHour--;
        }

        List<OrderPricing> orderPricings = orderPricingRepository.findByOrderId(order.getId());
        double lastPrice = 0;
        for (OrderPricing orderPricing : orderPricings
                ) {
            if (orderPricing.getPricePerHour() > lastPrice) {
                lastPrice = orderPricing.getPricePerHour();
            }
            for (HourHasPrice hourHasPrice : hourHasPrices) {
                if (orderPricing.getFromHour() < hourHasPrice.getHour()) {
                    hourHasPrice.setPrice(orderPricing.getPricePerHour());
                }
            }
        }

        for (
                HourHasPrice hourHasPrice : hourHasPrices
                ) {
            totalPrice += hourHasPrice.getPrice();
        }

        totalPrice += lastPrice * (totalMinute / 60);

        order.setDuration(duration.toMilisecond());
        order.setTotal(totalPrice);
        OrderStatus orderStatus = orderStatusRepository.findByName(OrderStatusEnum.Close.getName()).get();
        order.setOrderStatusId(orderStatus);

        orderRepository.save(order);

        PushNotificationService pushNotificationService = new PushNotificationService();
        pushNotificationService.sendNotification(userToken, NotificationEnum.CHECK_OUT, order.getId());

        return Optional.of(order);
    }
}

class HourHasPrice {
    int hour;
    Double price;

    public HourHasPrice(int hour, Double price) {
        this.hour = hour;
        this.price = price;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int compare(HourHasPrice hourHasPrice) {
        return (this.getHour() < hourHasPrice.getHour()) ? -1 : 1;
    }

    public static List<HourHasPrice> sort(List<HourHasPrice> hourHasPrices) {

        hourHasPrices.sort((o1, o2) -> o1.compare(o2));

        return hourHasPrices;
    }
}