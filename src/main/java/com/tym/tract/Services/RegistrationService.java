package com.tym.tract.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tym.tract.Models.Registration;
import com.tym.tract.Repositories.RegistrationRepo;
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
}
