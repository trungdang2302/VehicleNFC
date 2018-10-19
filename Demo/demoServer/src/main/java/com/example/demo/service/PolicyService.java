package com.example.demo.service;

import com.example.demo.entities.Location;
import com.example.demo.entities.Policy;
import com.example.demo.entities.PolicyHasTblVehicleType;
import com.example.demo.entities.VehicleType;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.PolicyHasVehicleTypeRepository;
import com.example.demo.repository.PolicyRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final PolicyHasVehicleTypeRepository policyHasVehicleTypeRepository;
    private final LocationRepository locationRepository;

    public PolicyService(PolicyRepository policyRepository, PolicyHasVehicleTypeRepository policyHasVehicleTypeRepository, LocationRepository locationRepository) {
        this.policyRepository = policyRepository;
        this.policyHasVehicleTypeRepository = policyHasVehicleTypeRepository;
        this.locationRepository = locationRepository;
    }

    public Optional<Policy> getPolicyById(Integer policyId) {
        return policyRepository.findById(policyId);
    }

    @Transactional
    public Policy savePolicy(Policy policy, List<VehicleType> vehicleTypeList, Integer locationId) {
        policyRepository.save(policy);
        Location location = locationRepository.findById(locationId).get();
        Policy policyDB = policyRepository.findById(policy.getId()).get();

        List<Policy> policyList = location.getPolicyList();
        policyList.add(policyDB);
        location.setPolicyList(policyList);
        locationRepository.save(location);

        for (int i = 0; i < vehicleTypeList.size(); i++) {
            Optional<PolicyHasTblVehicleType> policyHasTblVehicleType = policyHasVehicleTypeRepository.findByPolicyIdAndVehicleTypeId(policyDB, vehicleTypeList.get(i));
            if (!policyHasTblVehicleType.isPresent()) {
                PolicyHasTblVehicleType policyHasTblVehicleType1 = new PolicyHasTblVehicleType();
                policyHasTblVehicleType1.setPolicyId(policyDB);
                policyHasTblVehicleType1.setVehicleTypeId(vehicleTypeList.get(i));
                policyHasVehicleTypeRepository.save(policyHasTblVehicleType1);
            }
        }
    return policyDB;
    }
}
