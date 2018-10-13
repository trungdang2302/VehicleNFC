package com.example.demo.service;

import com.example.demo.entities.Policy;
import com.example.demo.entities.PolicyHasTblVehicleType;
import com.example.demo.entities.Pricing;
import com.example.demo.entities.VehicleType;
import com.example.demo.repository.PolicyHasVehicleTypeRepository;
import com.example.demo.repository.PolicyRepository;
import com.example.demo.repository.PricingRepository;
import com.example.demo.repository.VehicleTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PolicyHasVehicleTypeService {
    private final PolicyHasVehicleTypeRepository policyHasVehicleTypeRepository;
    private final PolicyRepository policyRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final PricingRepository pricingRepository;

    public PolicyHasVehicleTypeService(PolicyHasVehicleTypeRepository policyHasVehicleTypeRepository, PolicyRepository policyRepository, VehicleTypeRepository vehicleTypeRepository, PricingRepository pricingRepository) {
        this.policyHasVehicleTypeRepository = policyHasVehicleTypeRepository;
        this.policyRepository = policyRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.pricingRepository = pricingRepository;
    }

    public List<PolicyHasTblVehicleType> findByPolicyId(Integer policyId) {
        Policy policy = policyRepository.findById(policyId).get();
        List<PolicyHasTblVehicleType> policyHasTblVehicleTypes = policyHasVehicleTypeRepository.findByPolicyId(policy);
//        for (PolicyHasTblVehicleType policyHasTblVehicleType : policyHasTblVehicleTypes) {
//            policyHasTblVehicleType.setPricings(pricingRepository.findAllByPolicyHasTblVehicleTypeId(policyHasTblVehicleType));
//        }
        for (int i = 0; i < policyHasTblVehicleTypes.size(); i++) {
            PolicyHasTblVehicleType policyHasTblVehicleType = policyHasTblVehicleTypes.get(i);
            policyHasTblVehicleType.setPricings(pricingRepository.findAllByPolicyHasTblVehicleTypeId(policyHasTblVehicleType.getId()));
            policyHasTblVehicleTypes.set(i,policyHasTblVehicleType);
        }
        return policyHasTblVehicleTypes;
    }

    public Optional<PolicyHasTblVehicleType> findByPolicyAndVehicleType(Integer policyId, Integer vehicleTypeId) {
        Policy policy = policyRepository.findById(policyId).get();
        VehicleType vehicleType = vehicleTypeRepository.findById(vehicleTypeId).get();

        PolicyHasTblVehicleType policyHasTblVehicleType=  policyHasVehicleTypeRepository.findByPolicyIdAndVehicleTypeId(policy, vehicleType).get();
        List<Pricing> pricing = pricingRepository.findAllByPolicyHasTblVehicleTypeId(policyHasTblVehicleType.getId());
        policyHasTblVehicleType.setPricings(pricing);
        return Optional.of(policyHasTblVehicleType);
    }

    public List<PolicyHasTblVehicleType> findByPolicyList(List<Policy> policyList) {
        return policyHasVehicleTypeRepository.findByPolicyIdIn(policyList);
    }
}
