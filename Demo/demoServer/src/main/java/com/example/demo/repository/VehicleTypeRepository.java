package com.example.demo.repository;

import com.example.demo.entities.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Integer> {
}
