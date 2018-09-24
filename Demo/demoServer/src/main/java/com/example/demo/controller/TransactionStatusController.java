package com.example.demo.controller;

import com.example.demo.model.TransactionStatus;
import com.example.demo.service.TransactionStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/transaction/status")
public class TransactionStatusController {

    private final TransactionStatusService transactionStatusService;

    public TransactionStatusController(TransactionStatusService transactionStatusService) {
        this.transactionStatusService = transactionStatusService;
    }

    @GetMapping(value = {"/{id}"})
    public ResponseEntity<Optional<TransactionStatus>> getTransactionStatusById(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionStatusService.getTransasctionStatusById(id));
    }
}
