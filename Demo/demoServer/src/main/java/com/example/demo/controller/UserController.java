package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.VehicleType;
import com.example.demo.repository.VehicleTypeRepository;
import com.example.demo.service.UserService;
import com.example.demo.service.VehicleTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Base64;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;
    private final VehicleTypeService vehicleTypeService;

    public UserController(UserService userService, VehicleTypeService vehicleTypeService) {
        this.userService = userService;
        this.vehicleTypeService = vehicleTypeService;
    }

    @GetMapping(value = {"/{id}"})
    public ResponseEntity<Optional<User>> getUserById(@PathVariable("id") String userId){
        Integer id = Integer.parseInt(userId);
        System.out.println("getting user info...");
        return status(OK).body(userService.getUserById(id));
    }

    @GetMapping(value = "/getUser")
    public ModelAndView userPage1(ModelAndView mav) {
        mav.setViewName("create-user");
        return mav;
    }

    @PostMapping(value = "/create-user")
    public String createUser(@ModelAttribute("user") User user) {
        ModelAndView mav = new ModelAndView("create-user");
        String hashedID = "";
        // Cần đoạn lấy thông tin xe
       Optional<VehicleType> vehicleTypeOptional = vehicleTypeService.getVehicleTypeById(1);

       if (vehicleTypeOptional.isPresent()) {
           VehicleType vehicleType =  vehicleTypeOptional.get();
           user.setVehicleTypeId(vehicleType);
           userService.createUser(user);
       }
       Optional<User> userOptional = userService.getUserByPhone(user.getPhoneNumber());
       if (userOptional.isPresent()) {
           User userDB = userOptional.get();
           int userId = userDB.getId();
          hashedID = userService.hashID(userId);
       }
        return hashedID;
    }
}
