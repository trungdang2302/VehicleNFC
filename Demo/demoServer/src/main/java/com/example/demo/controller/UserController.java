package com.example.demo.controller;

import com.example.demo.Config.PaginationEnum;
import com.example.demo.Config.ResponseObject;
import com.example.demo.Config.SearchCriteria;
import com.example.demo.entities.User;
import com.example.demo.entities.VehicleType;
import com.example.demo.service.UserService;
import com.example.demo.service.VehicleTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
    public ResponseEntity<ResponseObject> getUsers(@RequestParam(defaultValue = "0") Integer page) {
//        return ResponseEntity.status(OK).body(userService.getAllUser(page, PaginationEnum.userPageSize.getNumberOfRows()));
        List<User> listUser = userService.getUsers(page, PaginationEnum.userPageSize.getNumberOfRows());
        ResponseObject response = new ResponseObject();
        response.setData(listUser);
        response.setPageNumber(page);
        response.setTotalPages(userService.getTotalUsers(PaginationEnum.userPageSize.getNumberOfRows()).intValue());
        return ResponseEntity.status(OK).body(response);
    }

    @GetMapping("/get-user")
    public ModelAndView home(ModelAndView mav) {
        mav.setViewName("index");
        return mav;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Optional<User>> create(@Param("phone") String phone, @Param("password") String password) {
        Optional<User> result = userService.login(phone, password);
        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/search-user")
    public ResponseEntity<ResponseObject> searchUser(@RequestBody SearchCriteria params
            , @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.status(OK).body(userService.searchUser
                (params,page, PaginationEnum.userPageSize.getNumberOfRows() ));
    }

    @GetMapping("/admin")
    public ModelAndView adminPage(ModelAndView mav) {
        mav.setViewName("admin");
        return mav;
    }
}
