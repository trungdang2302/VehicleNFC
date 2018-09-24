package com.example.demo.repository;

import com.example.demo.model.Meter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeterRepository extends JpaRepository<Meter, Integer> {

}
