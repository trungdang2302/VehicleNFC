package com.example.demo.controller;

import com.example.demo.model.MeterStatus;
import com.example.demo.service.MeterStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/meter/status")
public class MeterStatusController {
    private final MeterStatusService meterStatusService;

    public MeterStatusController(MeterStatusService meterStatusService) {
        this.meterStatusService = meterStatusService;
    }

    @GetMapping(value = {"/{id}"})
    public ResponseEntity<Optional<MeterStatus>> getMeterStatusById(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(meterStatusService.getMeterStatusById(id));
    }
}
