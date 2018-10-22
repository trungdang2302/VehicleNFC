package com.example.demo.controller;

import com.example.demo.Config.PaginationEnum;
import com.example.demo.Config.ResponseObject;
import com.example.demo.Config.SearchCriteria;
import com.example.demo.entities.User;
import com.example.demo.entities.VehicleType;
import com.example.demo.service.UserService;
import com.example.demo.service.VehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@CrossOrigin
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private ServletContext servletContext;

    private final UserService userService;
    private final VehicleTypeService vehicleTypeService;

    public UserController(UserService userService, VehicleTypeService vehicleTypeService) {
        this.userService = userService;
        this.vehicleTypeService = vehicleTypeService;
    }

    @GetMapping(value = {"/get-user/{id}"})
    public ResponseEntity<Optional<User>> getUserById(@PathVariable("id") Integer id) {
        System.out.println("getting user info...");
        return status(OK).body(userService.getUserById(id));
    }

    @PostMapping(value = "/create-user")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        String hashedID = "";
        // Cần đoạn lấy thông tin xe
//        Optional<VehicleType> vehicleTypeOptional = vehicleTypeService.getVehicleTypeById(user.getVehicleTypeId().getId());

//        if (vehicleTypeOptional.isPresent()) {
//            VehicleType vehicleType = vehicleTypeOptional.get();
//            user.setVehicleTypeId(vehicleType);
        String confirmCode = encodeGenerator();
        userService.createUser(user, confirmCode);
        Map<String, String> confirmSMSList = (Map<String, String>) servletContext.getAttribute("confirmSMSList");
        if (confirmSMSList == null) {
            confirmSMSList = new HashMap<>();
        }
        confirmSMSList.put(user.getPhoneNumber(), confirmCode);
        servletContext.setAttribute("confirmSMSList", confirmSMSList);
//        }
        Optional<User> userOptional = userService.getUserByPhone(user.getPhoneNumber());
        if (userOptional.isPresent()) {
            User userDB = userOptional.get();
            int userId = userDB.getId();
            hashedID = userId + "";
//            hashedID = userService.hashID(userId);
        }
        return status(OK).body(hashedID);
    }


    @PostMapping(value = "/request-new-confirm")
    public ResponseEntity<String> requestNewConfirm(@Param("phone") String phone) {
        // Cần đoạn lấy thông tin xe
        String confirmCode = encodeGenerator();
        userService.requestNewConfirmCode(phone, confirmCode);
        Map<String, String> confirmSMSList = (Map<String, String>) servletContext.getAttribute("confirmSMSList");
        if (confirmSMSList == null) {
            confirmSMSList = new HashMap<>();
        }
        confirmSMSList.put(phone, confirmCode);
        servletContext.setAttribute("confirmSMSList", confirmSMSList);
        return status(OK).body("success");
    }

    @PostMapping(value = "/confirm-user")
    public ResponseEntity<Boolean> confirmUser(@Param("phone") String phone, @Param("confirmCode") String confirmCode) {
        // Cần đoạn lấy thông tin xe
        Map<String, String> confirmSMSList = (Map<String, String>) servletContext.getAttribute("confirmSMSList");
//        String confirmCode = (String) session.getAttribute(user.getPhoneNumber());
        if (confirmSMSList != null) {
            String confirmCodeInSession = confirmSMSList.get(phone);
            if (confirmCodeInSession != null && confirmCodeInSession.equals(confirmCode)) {
                userService.activateUser(phone);
                return status(OK).body(true);
            }
        }
        return status(OK).body(false);
    }


    @PostMapping("/save-user")
    public String updateUser(User user) {
        userService.updateUser(user);
        return "Success";
    }

    @PostMapping("/update-user-sms")
    public String updateUserSms(@RequestBody User user) {
        userService.updateUserSmsNoti(user);
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
                (params, page, PaginationEnum.userPageSize.getNumberOfRows()));
    }

    @GetMapping("/admin")
    public ModelAndView adminPage(ModelAndView mav) {
        mav.setViewName("admin");
        return mav;
    }

    public String encodeGenerator() {
        String result = "";
        for (int i = 0; i < 6; i++) {
            result += (int) (Math.random() * (9)) + "";
        }
        return result;
    }


    @PostMapping(value = "/top-up")
    public ResponseEntity<Optional<User>> topUp(@Param("userId") String userId, @Param("amount") double amount) {
        return ResponseEntity.status(OK).body(userService.topUp(userId, amount));
    }

    @GetMapping(value = {"/get-user-by-phone"})
    public ResponseEntity<Optional<User>> getUserByPhoneNumber(@Param("phoneNumber") String phoneNumber) {
        System.out.println("getting user info...");
        return status(OK).body(userService.getUserByPhone(phoneNumber));
    }


}
