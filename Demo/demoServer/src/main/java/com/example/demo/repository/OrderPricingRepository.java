package com.example.demo.repository;

import com.example.demo.entities.Order;
import com.example.demo.entities.OrderPricing;
import com.example.demo.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderPricingRepository extends JpaRepository<OrderPricing, Integer> {

    @Query(
            value = "SELECT * FROM tbl_order_pricing u WHERE u.tbl_order_id = :order_id",
            nativeQuery = true)
    List<OrderPricing> findByOrderId(@Param("order_id") Integer orderId);
}
