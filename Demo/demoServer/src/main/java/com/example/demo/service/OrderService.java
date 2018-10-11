package com.example.demo.service;

import com.example.demo.Config.NotificationEnum;
import com.example.demo.Config.OrderStatusEnum;
import com.example.demo.Config.ResponseObject;
import com.example.demo.Config.SearchCriteria;
import com.example.demo.entities.*;
import com.example.demo.entities.Order;
import com.example.demo.repository.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
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
        pushNotificationService.sendNotification(userToken, NotificationEnum.CHECK_IN);

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
            if (orderPricing.getPricePerHour() > lastPrice){
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

        order.setTotal(totalPrice);
        OrderStatus orderStatus = orderStatusRepository.findByName(OrderStatusEnum.Close.getName()).get();
        order.setOrderStatusId(orderStatus);

        orderRepository.save(order);

        PushNotificationService pushNotificationService = new PushNotificationService();
        pushNotificationService.sendNotification(userToken, NotificationEnum.CHECK_OUT);

        return Optional.of(order);
    }

    @Autowired
    private EntityManager entityManager;

    public ResponseObject getOrders(int pagNumber, int pageSize) {
            ResponseObject responseObject = new ResponseObject();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
            Root<Order> from = criteriaQuery.from(Order.class);
            CriteriaQuery<Order> select = criteriaQuery.select(from);
            select.orderBy(builder.desc(from.get("checkInDate")), builder.desc(from.get("checkOutDate")));
            TypedQuery<Order> typedQuery = entityManager.createQuery(select);

            typedQuery.setFirstResult(pagNumber * pageSize);
            typedQuery.setMaxResults(pageSize);

            List<Order> orders = typedQuery.getResultList();

            responseObject.setData(orders);
            responseObject.setPageNumber(pagNumber);
            responseObject.setTotalPages((orders.size()/pageSize)+1);
            return responseObject;
        }

    public ResponseObject  filterOrders(SearchCriteria param, int pagNumber, int pageSize) {
        ResponseObject responseObject = new ResponseObject();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = builder.createQuery(Order.class);
        Root r = query.from(Order.class);

        Predicate predicate = builder.conjunction();

        if (param.getOperation().equalsIgnoreCase(">")) {
            predicate = builder.and(predicate,
                    builder.greaterThanOrEqualTo(r.get(param.getKey()),
                            param.getValue().toString()));
        } else if (param.getOperation().equalsIgnoreCase("<")) {
            predicate = builder.and(predicate,
                    builder.lessThanOrEqualTo(r.get(param.getKey()),
                            param.getValue().toString()));
        } else if (param.getOperation().equalsIgnoreCase(":")) {
            Object type = r.get(param.getKey()).getJavaType();
            if (type == String.class) {
                predicate = builder.and(predicate,
                        builder.like(r.get(param.getKey()),
                                "%" + param.getValue() + "%"));
            } else if (type == Location.class) {
                Join<Order, Location> join = r.join("locationId");
                Predicate locationNamePredicate = builder.like(join.get("location"), "%" + param.getValue() + "%");
                predicate = builder.and(predicate,locationNamePredicate);
            } else if (type == OrderStatus.class){
                Join<Order, Location> join = r.join("orderStatusId");
                Predicate locationNamePredicate = builder.like(join.get("name"), param.getValue() + "%");
                predicate = builder.and(predicate,locationNamePredicate);
            } else {
                predicate = builder.and(predicate,
                        builder.equal(r.get(param.getKey()), param.getValue()));
            }
        }
        query.where(predicate);
        query.orderBy(builder.desc(r.get("checkInDate")), builder.desc(r.get("checkOutDate")));
        TypedQuery<Order> typedQuery = entityManager.createQuery(query);
        List<Order> orders = typedQuery.getResultList();
        int totalPages = orders.size() / pageSize;
        typedQuery.setFirstResult(pagNumber * pageSize);
        typedQuery.setMaxResults(pageSize);
        List<Order> orderList = typedQuery.getResultList();
        responseObject.setData(orderList);
        responseObject.setTotalPages(totalPages + 1);
        responseObject.setPageNumber(pagNumber);
        return  responseObject;
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