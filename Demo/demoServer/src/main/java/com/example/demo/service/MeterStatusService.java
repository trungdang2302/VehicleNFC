package com.example.demo.service;

import com.example.demo.model.MeterStatus;
import com.example.demo.repository.MeterStatusRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MeterStatusService {
    private final MeterStatusRepository meterStatusRepository;

    public MeterStatusService(MeterStatusRepository meterStatusRepository) {
        this.meterStatusRepository = meterStatusRepository;
    }

    public Optional<MeterStatus> getMeterStatusById(Integer id) {
        return meterStatusRepository.findById(id);
    }
}
