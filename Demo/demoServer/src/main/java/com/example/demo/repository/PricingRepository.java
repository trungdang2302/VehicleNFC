package com.example.demo.repository;

import com.example.demo.entities.PolicyHasTblVehicleType;
import com.example.demo.entities.Pricing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PricingRepository extends JpaRepository<Pricing, Integer> {
    Pricing findByPolicyHasTblVehicleTypeId(PolicyHasTblVehicleType policyHasTblVehicleType);
}
