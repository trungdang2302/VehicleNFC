package com.example.demo.repository;

import com.example.demo.entities.Location;
import com.example.demo.entities.Order;
import com.example.demo.entities.OrderStatus;
import com.example.demo.entities.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Optional<Order> findByUserIdAndLocationId(User user, Location location);

    Optional<Order> findByUserIdAndLocationIdAndOrderStatusId(User user, Location location, OrderStatus orderStatus);

    List<Order> findByUserIdOrderById(User user);
}
