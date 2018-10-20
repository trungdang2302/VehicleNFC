package com.example.demo.controller;

import com.example.demo.entities.Policy;
import com.example.demo.entities.VehicleType;
import com.example.demo.service.PolicyService;
import com.example.demo.view.PolicyView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/edit")
    public ModelAndView index(ModelAndView mav
                            , @RequestParam("policyId") Integer policyId
                            , @RequestParam("vehicleTypeId") Integer vehicleTypeId) {
        mav.setViewName("policy-edit");
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView createPage(ModelAndView mav
                                 , @RequestParam("locationId") Integer locationId) {
        mav.setViewName("policy-create");
        return mav;
    }

    @PostMapping(value = "/create")
    public ResponseEntity createPolicy(@RequestBody PolicyView policyView) {
        try {
            Integer locationId = policyView.getLocationId();
            Policy policy = policyView.getPolicy();
            List<VehicleType> vehicleTypeList = policyView.getVehicleTypes();

            return ResponseEntity.status(HttpStatus.OK).body(policyService.savePolicy(policy, vehicleTypeList, locationId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }
}
