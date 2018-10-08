package com.example.demo.service;

import com.example.demo.entities.Policy;
import com.example.demo.repository.PolicyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PolicyService {
    private final PolicyRepository policyRepository;

    public PolicyService(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    public Optional<Policy> getPolicyById(Integer policyId) {
        return policyRepository.findById(policyId);
    }
//    public List<Policy>
}
