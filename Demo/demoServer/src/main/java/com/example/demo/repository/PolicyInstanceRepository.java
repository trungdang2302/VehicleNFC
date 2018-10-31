package com.example.demo.repository;

import com.example.demo.entity.PolicyInstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyInstanceRepository extends JpaRepository<PolicyInstance, Long> {
}
