package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;
@RestController
public class ServerController {

    @GetMapping(value = {"/test"})
    public ResponseEntity<String> serverResponseTest(){
        System.out.println("Something calling me");
        return status(OK).body("\"server here\"");
    }
}
