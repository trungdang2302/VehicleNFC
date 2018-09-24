package com.example.demo.service;

import com.example.demo.model.TransactionStatus;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.repository.TransactionStatusRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionStatusService {
    private final TransactionStatusRepository transactionRepository;

    public TransactionStatusService(TransactionStatusRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Optional<TransactionStatus> getTransasctionStatusById(Integer id){
        return transactionRepository.findById(id);
    }
}
