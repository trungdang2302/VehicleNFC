package com.example.demo.controller;

import com.example.demo.entities.Policy;
import com.example.demo.service.PolicyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/policy")
public class PolicyController {
    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }
    @GetMapping(value = {"/get/{id}"})
    public ResponseEntity<?> getPolicyById(@PathVariable("id") Integer id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(policyService.getPolicyById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found Policy");
        }
    }
}
