package com.example.demo.controller;

import com.example.demo.model.Meter;
import com.example.demo.service.MeterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/meter")
public class MeterController {

    private final MeterService meterService;

    public MeterController(MeterService meterService) {
        this.meterService = meterService;
    }

    @GetMapping(value = {"/{id}"})
    public ResponseEntity<Optional<Meter>> getMeterById(@PathVariable("id") Integer id) {
        System.out.println("Getting meter info...");
        return ResponseEntity.status(HttpStatus.OK).body(meterService.getMeterById(id));
    }
}
