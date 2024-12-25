package com.tym.tract.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tym.tract.RequestResponse.LoginRequest;
import com.tym.tract.RequestResponse.RegistrationRequest;
import com.tym.tract.Services.RegistrationService;

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
        return "Please enter required details";
    }
    
}
