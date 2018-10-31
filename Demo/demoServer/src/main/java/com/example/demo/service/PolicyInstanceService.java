package com.example.demo.service;

import com.example.demo.repository.PolicyInstanceRepository;
import org.springframework.stereotype.Service;

@Service
public class PolicyInstanceService {
    private PolicyInstanceRepository policyInstanceRepository;

    public PolicyInstanceService(PolicyInstanceRepository policyInstanceRepository) {
        this.policyInstanceRepository = policyInstanceRepository;
    }


}
