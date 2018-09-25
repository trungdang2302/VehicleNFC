package com.example.demo.controller;

import com.example.demo.model.Transaction;
import com.example.demo.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping(value = "/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping(value = {"/{id}"})
    public ResponseEntity<Optional<Transaction>> getTransactionById(@PathVariable("id") Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.getTransactionById(id));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Optional<Transaction>> create(@RequestBody Transaction transaction) {
        Optional<Transaction> transaction1 = transactionService.createTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction1);
    }
}
