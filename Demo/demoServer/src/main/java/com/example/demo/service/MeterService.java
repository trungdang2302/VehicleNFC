package com.example.demo.service;

import com.example.demo.model.Meter;
import com.example.demo.repository.MeterRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MeterService {
    private final MeterRepository meterRepository;

    public MeterService(MeterRepository meterRepository) {
        this.meterRepository = meterRepository;
    }

    public Optional<Meter> getMeterById(Integer id) {
        return meterRepository.findById(id);
    }
}
