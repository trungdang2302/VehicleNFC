package com.example.demo.service;

import com.example.demo.entities.Policy;
import com.example.demo.entities.PolicyHasTblVehicleType;
import com.example.demo.entities.VehicleType;
import com.example.demo.repository.PolicyHasVehicleTypeRepository;
import com.example.demo.repository.PolicyRepository;
import com.example.demo.repository.VehicleTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PolicyHasVehicleTypeService {
    private final PolicyHasVehicleTypeRepository policyHasVehicleTypeRepository;
    private final PolicyRepository policyRepository;
    private final VehicleTypeRepository vehicleTypeRepository;

    public PolicyHasVehicleTypeService(PolicyHasVehicleTypeRepository policyHasVehicleTypeRepository, PolicyRepository policyRepository, VehicleTypeRepository vehicleTypeRepository) {
        this.policyHasVehicleTypeRepository = policyHasVehicleTypeRepository;
        this.policyRepository = policyRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
    }

    public List<PolicyHasTblVehicleType> findByPolicyId(Integer policyId) {
        Policy policy = policyRepository.findById(policyId).get();
        return policyHasVehicleTypeRepository.findByPolicyId(policy);
    }

    public Optional<PolicyHasTblVehicleType> findByPolicyAndVehicleType(Integer policyId, Integer vehicleTypeId) {
        Policy policy = policyRepository.findById(policyId).get();
        VehicleType vehicleType = vehicleTypeRepository.findById(vehicleTypeId).get();
        return policyHasVehicleTypeRepository.findByPolicyIdAndVehicleTypeId(policy, vehicleType);
    }
    public List<PolicyHasTblVehicleType> findByPolicyList(List<Policy> policyList) {
        return policyHasVehicleTypeRepository.findByPolicyIdIn(policyList);
    }
}
