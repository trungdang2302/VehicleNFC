package com.example.demo.service;

import com.example.demo.Config.ResponseObject;
import com.example.demo.entities.Location;

import com.example.demo.entities.Policy;
import com.example.demo.entities.PolicyHasTblVehicleType;
import com.example.demo.entities.VehicleType;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.PolicyHasVehicleTypeRepository;
import com.example.demo.repository.PolicyRepository;
import com.example.demo.repository.PricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    private final LocationRepository locationRepository;
    private final PolicyHasVehicleTypeRepository policyHasVehicleTypeRepository;
    private final PolicyRepository policyRepository;
    private final PricingRepository pricingRepository;

    @Autowired
    private EntityManager entityManager;

    public LocationService(LocationRepository locationRepository, PolicyHasVehicleTypeRepository policyHasVehicleTypeRepository, PolicyRepository policyRepository, PricingRepository pricingRepository) {
        this.locationRepository = locationRepository;
        this.policyHasVehicleTypeRepository = policyHasVehicleTypeRepository;
        this.policyRepository = policyRepository;
        this.pricingRepository = pricingRepository;
    }

    public Optional<Location> getMeterById(Integer id) {
        Optional<Location> location = locationRepository.findById(id);
        if (location.isPresent()) {
            for (Policy policy : location.get().getPolicyList()) {
                policy.setPolicyHasTblVehicleTypeList(policyHasVehicleTypeRepository.findByPolicyId(policy.getId()));
                for (PolicyHasTblVehicleType policyHasTblVehicleType : policy.getPolicyHasTblVehicleTypeList()) {
                    policyHasTblVehicleType.setPricings(pricingRepository.findAllByPolicyHasTblVehicleTypeId(policyHasTblVehicleType.getId()));
                }
            }
        }
        return location;
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

    public List<VehicleType> getLocationHasVehicleTypes(Integer locationId) {
        Optional<Location> location = locationRepository.findById(locationId);
        List<VehicleType> vehicleTypeList = new ArrayList<>();
        if (location.isPresent()) {
            List<Policy> policyList = location.get().getPolicyList();
            if (policyList != null) {
                for (Policy policy : policyList) {
                    List<PolicyHasTblVehicleType> policyHasTblVehicleTypes = policyHasVehicleTypeRepository.findByPolicyId(policy.getId());
                    if (policyHasTblVehicleTypes != null) {
                        for (PolicyHasTblVehicleType policyHasTblVehicleType : policyHasTblVehicleTypes) {
                            VehicleType vehicleType = policyHasTblVehicleType.getVehicleTypeId();
                            if (!vehicleTypeList.contains(vehicleType)) {
                                vehicleTypeList.add(vehicleType);
                            }
                        }
                    }

                }
            }

        }
        return vehicleTypeList;
    }
}
