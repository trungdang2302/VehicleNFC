package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"/{id}"})
    public ResponseEntity<Optional<User>> getUserById(@PathVariable("id") String userId){
        Integer id = Integer.parseInt(userId);
        System.out.println("getting user info");
        return status(OK).body(userService.getUserById(id));
    }
}
