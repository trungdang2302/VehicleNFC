package com.example.demo.repository;

import com.example.demo.entities.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PolicyRepository extends JpaRepository<Policy, Integer> {

}
