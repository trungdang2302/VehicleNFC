package com.example.demo.service;

import com.example.demo.entities.*;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.PolicyHasVehicleTypeRepository;
import com.example.demo.repository.PolicyRepository;
import com.example.demo.repository.PricingRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final PolicyHasVehicleTypeRepository policyHasVehicleTypeRepository;
    private final LocationRepository locationRepository;
    private final PricingRepository pricingRepository;

    public PolicyService(PolicyRepository policyRepository, PolicyHasVehicleTypeRepository policyHasVehicleTypeRepository, LocationRepository locationRepository, PricingRepository pricingRepository) {
        this.policyRepository = policyRepository;
        this.policyHasVehicleTypeRepository = policyHasVehicleTypeRepository;
        this.locationRepository = locationRepository;
        this.pricingRepository = pricingRepository;
    }

    public Optional<Policy> getPolicyById(Integer policyId) {
        return policyRepository.findById(policyId);
    }

    @Transactional
    public Policy savePolicy(Policy policy, List<VehicleType> vehicleTypeList, Integer locationId) {
        Policy policyDB = policyRepository.save(policy);
        Location location = locationRepository.findById(locationId).get();
//        Policy policyDB = policyRepository.findById(policy.getId()).get();

//        List<Policy> policyList = location.getPolicyList();
        List<Policy> policyList = new ArrayList<>();
        policyList.add(policyDB);
        location.setPolicyList(policyList);
        locationRepository.save(location);

        for (int i = 0; i < vehicleTypeList.size(); i++) {
            PolicyHasTblVehicleType policyHasTblVehicleType = policyHasVehicleTypeRepository.findByPolicyIdAndVehicleTypeId(policyDB.getId(), vehicleTypeList.get(i)).get();
//            if (!policyHasTblVehicleType.isPresent()) {
                PolicyHasTblVehicleType policyHasTblVehicleType1 = new PolicyHasTblVehicleType();
                policyHasTblVehicleType1.setPolicyId(policyDB.getId());
                policyHasTblVehicleType1.setVehicleTypeId(vehicleTypeList.get(i));
                policyHasVehicleTypeRepository.save(policyHasTblVehicleType1);
//            }
        }
    return policyDB;
    }

    @Transactional
    public void deletePolicy(Integer locationId, Policy policy, List<Integer> policyHasVehicleTypeId, List<VehicleType> vehicleTypeList){
        pricingRepository.deleteByPolicyHasTblVehicleTypeIdIn(policyHasVehicleTypeId);

        for (int i = 0; i < policyHasVehicleTypeId.size(); i++) {
            Optional<PolicyHasTblVehicleType> policyHasTblVehicleType = policyHasVehicleTypeRepository.findById(policyHasVehicleTypeId.get(i));
            if (policyHasTblVehicleType.isPresent()) {
                policyHasVehicleTypeRepository.delete(policyHasTblVehicleType.get());
            }

        }

        Location location = locationRepository.findById(locationId).get();
        locationRepository.deleteLocationPolicyByPolicyId(policy.getId());
        policyRepository.delete(policy);
    }
}
