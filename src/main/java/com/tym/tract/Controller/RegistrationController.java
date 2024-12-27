package com.tym.tract.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tym.tract.Models.Registration;
import com.tym.tract.RequestResponse.GenericResponse;
import com.tym.tract.RequestResponse.LoginRequest;
import com.tym.tract.RequestResponse.RegistrationRequest;
import com.tym.tract.Services.RegistrationService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;
    
    @PostMapping("/register")
public String register(@RequestBody RegistrationRequest registrationRequest) {
    // Adding null checks for the registrationRequest and its fields
    if (registrationRequest != null) {
        if (registrationRequest.getPhNumber() != null && !registrationRequest.getPhNumber().isEmpty() &&
            registrationRequest.getEmail() != null && !registrationRequest.getEmail().isEmpty() &&
            registrationRequest.getPassword() != null && !registrationRequest.getPassword().isEmpty()) {
                
            return registrationService.registerUser (registrationRequest);
        }
    }
    log.error("Please enter required details");
    return "Please enter required details";
}

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        // Adding null checks for loginRequest
        if (loginRequest != null) {
            // Assuming there are fields like username and password to check
            if (loginRequest.getPhNumber() != null && !loginRequest.getPhNumber().isEmpty() &&
                loginRequest.getPassword() != null && !loginRequest.getPassword().isEmpty()) {
                
                return registrationService.loginUser(loginRequest);
            }
        }
        log.error("Please enter required details");
        return "Please enter required details";
    }

    @GetMapping("/getUserDetails")
    public GenericResponse getUserDetails(@RequestParam String phoneNumber) {
        return registrationService.getUserDetails(phoneNumber);
    }
    
    
}
