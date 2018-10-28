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

        Policy policyDB = new Policy();
        if (policy.getId() == 0) {
            // create
            policyDB = policyRepository.save(policy);
            Location location = locationRepository.findById(locationId).get();
            List<Policy> policyList = location.getPolicyList();
//        List<Policy> policyList = new ArrayList<>();
            policyList.add(policyDB);
            location.setPolicyList(policyList);
            locationRepository.save(location);

        } else {
            policyDB = policyRepository.findById(policy.getId()).get();
        }

//        Policy policyDB = policyRepository.findById(policy.getId()).get();


        List<PolicyHasTblVehicleType> policyHasTblVehicleTypeList = new ArrayList<>();
        List<PolicyHasTblVehicleType> policyHasTblVehicleTypes = policyHasVehicleTypeRepository.findByPolicyId(policyDB.getId());
        for (int i = 0; i < vehicleTypeList.size(); i++) {
            if (policyHasTblVehicleTypes.size() == 0) {
                PolicyHasTblVehicleType policyHasTblVehicleType1 = new PolicyHasTblVehicleType();
                policyHasTblVehicleType1.setPolicyId(policyDB.getId());
                policyHasTblVehicleType1.setVehicleTypeId(vehicleTypeList.get(i));
                policyHasVehicleTypeRepository.saveAndFlush(policyHasTblVehicleType1);
            } else {
                if (vehicleTypeList.get(i).getIsDelete().equalsIgnoreCase("true")) {
                    PolicyHasTblVehicleType instance = policyHasVehicleTypeRepository.findByPolicyIdAndVehicleTypeId(policyDB.getId(), vehicleTypeList.get(i)).get();
//                    policyHasTblVehicleType1.setPolicyId(policyDB.getId());
//                    policyHasTblVehicleType1.setVehicleTypeId(vehicleTypeList.get(i));
//                    policyHasVehicleTypeRepository.delete(policyHasTblVehicleType1);
//                    policyHasVehicleTypeRepository.deleteById(policyHasTblVehicleType1.getId());
                    policyHasVehicleTypeRepository.deletePolicyHasVehicleById(instance.getId());
                    break;
                } else {
                    Optional<PolicyHasTblVehicleType> policyHasTblVehicleType1 = policyHasVehicleTypeRepository.findByPolicyIdAndVehicleTypeId(policyDB.getId(), vehicleTypeList.get(i));
                    PolicyHasTblVehicleType dto = new PolicyHasTblVehicleType();
                    if (!policyHasTblVehicleType1.isPresent()) {
                        dto = new PolicyHasTblVehicleType();
                        dto.setPolicyId(policyDB.getId());
                        dto.setVehicleTypeId(vehicleTypeList.get(i));
                        policyHasVehicleTypeRepository.saveAndFlush(dto);
                    }

                }
            }
        }

//            Optional<PolicyHasTblVehicleType> policyHasTblVehicleType = policyHasVehicleTypeRepository.findByPolicyIdAndVehicleTypeId(policyDB.getId(), vehicleTypeList.get(i));
//            if (!policyHasTblVehicleType.isPresent()) {
//                PolicyHasTblVehicleType policyHasTblVehicleType1 = new PolicyHasTblVehicleType();
//                policyHasTblVehicleType1.setPolicyId(policyDB.getId());
//                policyHasTblVehicleType1.setVehicleTypeId(vehicleTypeList.get(i));
//                policyHasVehicleTypeRepository.save(policyHasTblVehicleType1);
//            }

//        if (policyHasTblVehicleTypes.size() != 0) {
//            policyDB.setPolicyHasTblVehicleTypeList(policyHasTblVehicleTypeList);
//            policyRepository.save(policyDB);
//            for (PolicyHasTblVehicleType policyHasTblVehicleType:policyHasTblVehicleTypeList) {
//                PolicyHasTblVehicleType policyHasTblVehicleType1 = new PolicyHasTblVehicleType();
//                policyHasTblVehicleType1.setPolicyId(policyDB.getId());
//                policyHasTblVehicleType1.setVehicleTypeId(policyHasTblVehicleType.getVehicleTypeId());
//                policyHasVehicleTypeRepository.save(policyHasTblVehicleType1);
//            }
//        }
        return policyDB;
    }

    @Transactional
    public void deletePolicy(Integer locationId, Policy policy, List<Integer> policyHasVehicleTypeId, List<VehicleType> vehicleTypeList) {
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
