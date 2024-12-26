package com.tym.tract.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tym.tract.Models.Registration;
import com.tym.tract.Repositories.RegistrationRepo;
import com.tym.tract.RequestResponse.LoginRequest;
import com.tym.tract.RequestResponse.RegistrationRequest;

@Service
public class RegistrationService {

    @Autowired
    RegistrationRepo registrationRepo;

    public String registerUser(RegistrationRequest registrationRequest){
        try{
            Registration registrationRow = Registration.builder()
                                            .phNumber(registrationRequest.getPhNumber())    
                                            .name(registrationRequest.getName())  
                                            .email(registrationRequest.getEmail())
                                            .password(registrationRequest.getPassword())
                                            .amount(registrationRequest.getAmount())
                                            .build();
            registrationRepo.save(registrationRow);
            return "saved successful";
        }catch(Exception e){
            System.out.println(e);
            return "Unable to save Data";
        }
    }

    public String loginUser(LoginRequest loginRequest){
        try{
            String phNumber = loginRequest.getPhNumber();
            if(loginRequest.getPhNumber()!= null && !loginRequest.getPhNumber().isEmpty()){
                if(registrationRepo.findPasswordByPhNumber(phNumber) !=null && registrationRepo.findPasswordByPhNumber(phNumber).getPassword().equals(loginRequest.getPassword())){
                    return "Login Successful";
                }
            }
            return "Please Provide Valid Credentials";
        
        }catch(Exception e){
            System.out.println(e);
            return "Invalid user details";
        }
    }

    public Registration getUserDetails(String phNumber){
        Registration registrationRow = null;
        try{
            registrationRow = registrationRepo.findByPhNumber(phNumber);
            return registrationRow;
        }catch(Exception e){
            System.out.println(e);
            return registrationRow;
        }
    }
}
