package com.example.demo.service;

import com.example.demo.entities.PolicyHasTblVehicleType;
import com.example.demo.entities.Pricing;
import com.example.demo.repository.PolicyHasVehicleTypeRepository;
import com.example.demo.repository.PricingRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PricingService {
    private final PricingRepository pricingRepository;
    private final PolicyHasVehicleTypeRepository policyHasVehicleTypeRepository;

    public PricingService(PricingRepository pricingRepository, PolicyHasVehicleTypeRepository policyHasVehicleTypeRepository) {
        this.pricingRepository = pricingRepository;
        this.policyHasVehicleTypeRepository = policyHasVehicleTypeRepository;
    }

    public Pricing findByPolicyHasVehicleTypeId(Integer policyHasVehicleTypeId) {
        Optional<PolicyHasTblVehicleType> policyHasTblVehicleType = policyHasVehicleTypeRepository.findById(policyHasVehicleTypeId);
        if (policyHasTblVehicleType.isPresent()) {
            PolicyHasTblVehicleType policyHasTblVehicleTypeDB = policyHasTblVehicleType.get();
            Pricing pricing =  pricingRepository.findByPolicyHasTblVehicleTypeId(policyHasTblVehicleTypeDB);
            return pricing;
        }
       return null;
    }
}
