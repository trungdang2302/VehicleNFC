package com.example.demo.service;

import com.example.demo.Config.ResponseObject;
import com.example.demo.entities.Location;
import com.example.demo.entities.Policy;
import com.example.demo.entities.User;
import com.example.demo.entities.Vehicle;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    public VehicleService(UserRepository userRepository, VehicleRepository vehicleRepository) {
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Autowired
    private EntityManager entityManager;

    public ResponseObject getAllVehicle() {
        ResponseObject responseObject = new ResponseObject();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Vehicle> criteriaQuery = builder.createQuery(Vehicle.class);
        Root<Vehicle> from = criteriaQuery.from(Vehicle.class);
        CriteriaQuery<Vehicle> select = criteriaQuery.select(from);
        TypedQuery<Vehicle> typedQuery = entityManager.createQuery(select);
//        typedQuery.setFirstResult(pagNumber * pageSize);
//        typedQuery.setMaxResults(pageSize);
        List<Vehicle> vehicleList = typedQuery.getResultList();
        for (Vehicle vehicle : vehicleList) {
            Optional<User> owner = userRepository.findByVehicleNumber(vehicle.getVehicleNumber());
            if (owner.isPresent()) {
                vehicle.setOwner(owner.get());
            }
        }
        responseObject.setData(vehicleList);
        return responseObject;
    }

    public Optional<Vehicle> getVehicle(String vehicleNumber) {
        return vehicleRepository.findByVehicleNumber(vehicleNumber);
    }

    public Optional<Vehicle> verifyVehicle(Vehicle vehicle) {
        Optional<Vehicle> vehicleDB = vehicleRepository.findByVehicleNumber(vehicle.getVehicleNumber());
        if (vehicleDB.isPresent()) {
            vehicleDB.get().setBrand(vehicle.getBrand());
            vehicleDB.get().setSize(vehicle.getSize());
            vehicleDB.get().setExpireDate(vehicle.getExpireDate());
            vehicleDB.get().setVehicleTypeId(vehicle.getVehicleTypeId());
            vehicleDB.get().setVerified(true);
            vehicleRepository.save(vehicleDB.get());
        }
        return vehicleDB;
    }

    public Long getTotalVehicles(int pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder
                .createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(
                countQuery.from(Vehicle.class)));
        Long count = entityManager.createQuery(countQuery)
                .getSingleResult();
        return (long) (count / pageSize) + 1;
    }

}
