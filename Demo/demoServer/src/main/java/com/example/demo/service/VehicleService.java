package com.example.demo.service;

import com.example.demo.Config.ResponseObject;
import com.example.demo.entities.Location;
import com.example.demo.entities.Policy;
import com.example.demo.entity.User;
import com.example.demo.entity.Vehicle;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.UserRepository;
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

    public VehicleService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
