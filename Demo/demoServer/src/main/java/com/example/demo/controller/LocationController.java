package com.example.demo.controller;

import com.example.demo.Config.ResponseObject;
import com.example.demo.entities.Location;
import com.example.demo.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/location")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping(value = {"get/{id}"})
    public ResponseEntity<Optional<Location>> getLocationBy(@PathVariable("id") Integer id) {
        System.out.println("Getting location info...");
        return ResponseEntity.status(HttpStatus.OK).body(locationService.getMeterById(id));
    }

    @GetMapping("/page")
    public ModelAndView getAllMeters(ModelAndView mav) {
        mav.setViewName("location-management");
        return mav;
    }
    @GetMapping("/get-locations")
    public ResponseEntity<ResponseObject> getLocations() {
        ResponseObject response = locationService.getAllLocations();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
