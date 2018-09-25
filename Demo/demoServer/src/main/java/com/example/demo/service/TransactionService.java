package com.example.demo.service;

import com.example.demo.Config.TransactionStatusEnum;
import com.example.demo.model.Transaction;
import com.example.demo.model.TransactionStatus;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.repository.TransactionStatusRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionStatusRepository transactionStatusRepository;

    public TransactionService(TransactionRepository transactionRepository, TransactionStatusRepository transactionStatusRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionStatusRepository = transactionStatusRepository;
    }

    public Optional<Transaction> getTransactionById(Integer id) {
        return transactionRepository.findById(id);
    }

    public Optional<Transaction> createTransaction(Transaction transaction) {
        TransactionStatus transactionStatus = transactionStatusRepository.findById(TransactionStatusEnum.Open.getId()).get();
        transaction.setTransactionStatusId(transactionStatus);
        transactionRepository.save(transaction);
//        return transactionRepository.findById(transaction.getId());
        return Optional.of(transaction);
    }
}
