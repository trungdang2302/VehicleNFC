package com.example.demo.controller;

import com.example.demo.entities.Policy;
import com.example.demo.entities.PolicyHasTblVehicleType;
import com.example.demo.service.PolicyHasVehicleTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/policy-vehicleType")
public class PolicyHasVehicleTypeController {
    private final PolicyHasVehicleTypeService policyHasVehicleTypeService;

    public PolicyHasVehicleTypeController(PolicyHasVehicleTypeService policyHasVehicleTypeService) {
        this.policyHasVehicleTypeService = policyHasVehicleTypeService;
    }

    @GetMapping("/get-by-policy-vehicleType")
    public ResponseEntity<Optional<PolicyHasTblVehicleType>> getByPolicyAndVehicleType
            (@RequestParam(value = "policyId") Integer policyID
            ,@RequestParam(value = "vehicleTypeId") Integer vehicleTypeId) {

        return ResponseEntity.status(HttpStatus.OK).body(policyHasVehicleTypeService.findByPolicyAndVehicleType(policyID,vehicleTypeId));
    }

    @GetMapping("/get-vehicleTypes/{id}")
    public ResponseEntity<List<PolicyHasTblVehicleType>> getByPolicyID(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(policyHasVehicleTypeService.findByPolicyId(id));
    }

    @PostMapping("/get-vehicleTypes-byPolicies")
    public ResponseEntity<List<PolicyHasTblVehicleType>> getByListPolicy(@RequestBody List<Policy> policyList) {
        return ResponseEntity.status(HttpStatus.OK).body(policyHasVehicleTypeService.findByPolicyList(policyList));
    }
}
