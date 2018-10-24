package com.example.demo.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@RestController
public class ServerController {

    @GetMapping(value = {"/"})
    public ModelAndView theFront(ModelAndView mav) {
        mav.setViewName("admin");
        return mav;
    }

    @GetMapping(value = {"/test"})
    public String serverResponseTest() {
        System.out.println("Something calling me");
        return "Server here message:";
    }

    @PostMapping(value = {"/test"})
    public String serverResponsePostTest(@RequestBody String json) {
        System.out.println("Something calling me");
        return "Server here message:";
    }
}
