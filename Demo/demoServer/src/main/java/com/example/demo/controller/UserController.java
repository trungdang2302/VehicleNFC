package com.example.demo.controller;

import com.example.demo.Config.PaginationEnum;
import com.example.demo.Config.SearchCriteria;
import com.example.demo.model.User;
import com.example.demo.model.VehicleType;
import com.example.demo.service.UserService;
import com.example.demo.service.VehicleTypeService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @GetMapping(value = {"/getUser/{id}"})
    public ResponseEntity<Optional<User>> getUserById(@PathVariable("id") Integer id) {
        System.out.println("getting user info...");
        return status(OK).body(userService.getUserById(id));
    }

    @PostMapping(value = "/create-user")
    public String createUser(User user) {
        String hashedID = "";
        // Cần đoạn lấy thông tin xe
        Optional<VehicleType> vehicleTypeOptional = vehicleTypeService.getVehicleTypeById(user.getVehicleTypeId().getId());

        if (vehicleTypeOptional.isPresent()) {
            VehicleType vehicleType = vehicleTypeOptional.get();
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

    @PostMapping("/save-user")
    public String updateUser(User user) {
        userService.updateUser(user);
        return "Success";
    }

    @PostMapping("/delete-user/{id}")
    public String deleteUser(Integer id) {
        userService.deleteUser(id);
        return "Success";
    }
    //    JsonPagination
    @GetMapping("/get-users-json")
    public ResponseEntity<Page<User>> getUsers(@RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.status(OK).body(userService.getAllUser(page, PaginationEnum.userPageSize.getNumberOfRows()));
    }

    @GetMapping("/get-user")
    public ModelAndView home(ModelAndView mav) {
        mav.setViewName("index");
        return mav;
    }

    @GetMapping("/search-user")
    public ResponseEntity<List<User>> searchUser(@RequestParam(value = "search", required = false) String search) {
        List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                params.add(new SearchCriteria(matcher.group(1),
                        matcher.group(2), matcher.group(3)));
            }
            System.out.println("PArams: "+search);
            for (SearchCriteria criteria: params) {
                System.out.println("Key: "+criteria.getKey());
                System.out.println("Operation: "+criteria.getOperation());
                System.out.println("Value: "+criteria.getValue());
            }
        }

        return ResponseEntity.status(OK).body(userService.searchUser(params));
    }
}
