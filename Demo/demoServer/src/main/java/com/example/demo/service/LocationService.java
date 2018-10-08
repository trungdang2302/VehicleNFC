package com.example.demo.service;

import com.example.demo.Config.ResponseObject;
import com.example.demo.entities.Location;

import com.example.demo.repository.LocationRepository;
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
public class LocationService {
    private final LocationRepository locationRepository;
    private final PolicyHasVehicleTypeService policyHasVehicleTypeService;

    @Autowired
    private EntityManager entityManager;

    public LocationService(LocationRepository locationRepository, PolicyHasVehicleTypeService policyHasVehicleTypeService) {
        this.locationRepository = locationRepository;
        this.policyHasVehicleTypeService = policyHasVehicleTypeService;
    }

    public Optional<Location> getMeterById(Integer id) {
        return locationRepository.findById(id);
    }

    public ResponseObject getAllLocations() {
        ResponseObject responseObject = new ResponseObject();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Location> criteriaQuery = builder.createQuery(Location.class);
        Root<Location> from = criteriaQuery.from(Location.class);
        CriteriaQuery<Location> select = criteriaQuery.select(from);
        TypedQuery<Location> typedQuery = entityManager.createQuery(select);
//        typedQuery.setFirstResult(pagNumber * pageSize);
//        typedQuery.setMaxResults(pageSize);
        List<Location> locationList = typedQuery.getResultList();
//        for (int i = 0; i <locationList.size()-1; i++) {
//            Location location = locationList.get(i);
//            List<Policy> policyList = location.getPolicyList();
//            for (Policy policy : policyList) {
//                List<PolicyHasTblVehicleType> policyHasTblVehicleTypes = policyHasVehicleTypeService.findByPolicyId(policy);
//                policy.setPolicyHasTblVehicleTypeList(policyHasTblVehicleTypes);
//            }
//            location.setPolicyList(policyList);
//            locationList.set(i, location);
//        }
        responseObject.setData(locationList);
        return responseObject;
    }
}
