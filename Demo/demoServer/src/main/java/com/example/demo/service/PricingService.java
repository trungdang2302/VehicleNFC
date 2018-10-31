package com.example.demo.service;

import com.example.demo.entities.Policy;
import com.example.demo.entities.PolicyHasTblVehicleType;
import com.example.demo.entity.Pricing;
import com.example.demo.repository.PricingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PricingService {
    private final PricingRepository pricingRepository;

    public PricingService(PricingRepository pricingRepository) {
        this.pricingRepository = pricingRepository;
    }

    //Todo
//    public Pricing findByPolicyHasVehicleTypeId(Integer policyHasVehicleTypeId) {
//        Optional<PolicyHasTblVehicleType> policyHasTblVehicleType = policyHasVehicleTypeRepository.findById(policyHasVehicleTypeId);
//        if (policyHasTblVehicleType.isPresent()) {
//            PolicyHasTblVehicleType policyHasTblVehicleTypeDB = policyHasTblVehicleType.get();
//            Pricing pricing =  pricingRepository.findByPolicyHasTblVehicleTypeId(policyHasTblVehicleTypeDB);
//            return pricing;
//        }
//       return null;
//    }

    //Todo
//    public List<Pricing> findAllByPolicyHasTblVehicleTypeId(Integer policyHasTblVehicleTypeId){
//        Optional<PolicyHasTblVehicleType> policyHasTblVehicleType = policyHasVehicleTypeRepository.findById(policyHasTblVehicleTypeId);
//        if (policyHasTblVehicleType.isPresent()) {
//            PolicyHasTblVehicleType policyHasTblVehicleTypeDB = policyHasTblVehicleType.get();
//            List<Pricing> pricings = pricingRepository.findAllByPolicyHasTblVehicleTypeId(policyHasTblVehicleTypeDB.getId());
//            return pricings;
//        }
//        return null;
//    }

    public Pricing save(Pricing pricing) {
        return pricingRepository.save(pricing);
    }

    public void deletePricing(Integer id) {
        pricingRepository.deleteById(id);
    }

    public void deleteByPolicyHasTblVehicleTypeId(Integer policyHasVehicleTypeId) {
        pricingRepository.deleteByPolicyHasTblVehicleTypeId(policyHasVehicleTypeId);
    }

    public Pricing findById(Integer pricingId) {
        return pricingRepository.findById(pricingId).get();
    }
}
