package com.example.demo.controller;

import com.example.demo.Config.ResponseObject;
import com.example.demo.entities.Pricing;
import com.example.demo.service.PricingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/pricing")
public class PricingController {
    private final PricingService pricingService;

    public PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @GetMapping(value = "/get-by-policyAndVehicleType/{id}")
    public ResponseObject getPricingByPolicyAndVehicleType(@PathVariable("id") Integer id) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setData(pricingService.findByPolicyHasVehicleTypeId(id));
        return responseObject;
    }
}
