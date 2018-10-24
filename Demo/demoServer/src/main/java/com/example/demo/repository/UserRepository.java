package com.example.demo.repository;

import com.example.demo.entities.User;
import com.example.demo.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByPhoneNumberAndPassword(String phone, String password);

    Optional<User> findByVehicleNumber(String vehicleNumber);
}
