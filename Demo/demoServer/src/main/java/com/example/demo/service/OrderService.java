package com.example.demo.service;

import com.example.demo.Config.OrderStatusEnum;
import com.example.demo.entities.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final OrderPricingRepository orderPricingRepository;
    private final PolicyHasVehicleTypeRepository policyHasVehicleTypeRepository;

    public OrderService(OrderRepository orderRepository, OrderStatusRepository orderStatusRepository, UserRepository userRepository, LocationRepository locationRepository, OrderPricingRepository orderPricingRepository, PolicyHasVehicleTypeRepository policyHasVehicleTypeRepository) {
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.orderPricingRepository = orderPricingRepository;
        this.policyHasVehicleTypeRepository = policyHasVehicleTypeRepository;
    }

    public Optional<Order> getOrderById(Integer id) {
        Order order = orderRepository.findById(id).get();
        order.setOrderPricings(orderPricingRepository.findByOrderId(order.getId()));
        return  Optional.of(order);
    }

    public Optional<Order> createOrder(User checkInUser, Location location) {
        OrderStatus orderStatus  = orderStatusRepository.findByName(OrderStatusEnum.Open.getName()).get();
        Order order = new Order();
        order.setOrderStatusId(orderStatus);
        order.setUserId(userRepository.findById(checkInUser.getId()).get());
        order.setLocationId(locationRepository.findById(location.getId()).get());

        Policy policy = order.getLocationId().getPolicyList().get(0);
        order.setAllowedParkingFrom(policy.getAllowedParkingFrom());
        order.setAllowedParkingTo(policy.getAllowedParkingTo());

        List<Pricing> pricings = policyHasVehicleTypeRepository.findByPolicyId(policy).get(0).getPricings();


//        List<OrderPricing> orderPricings = OrderPricing.convertListPricingToOrderPricing(pricings);
//        order.setOrderPricings(orderPricings);

        orderRepository.save(order);

        List<OrderPricing> orderPricings = OrderPricing.convertListPricingToOrderPricing(pricings);
        for (OrderPricing orderPricing: orderPricings
             ) {
        orderPricing.setOrderId(order.getId());
        orderPricingRepository.save(orderPricing);
        }


        return Optional.of(order);
    }
}
