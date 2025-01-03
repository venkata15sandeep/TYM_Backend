package com.tym.tract.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tym.tract.Models.Registration;
import com.tym.tract.Repositories.RegistrationRepo;
import com.tym.tract.RequestResponse.GenericResponse;
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

    public GenericResponse getUserDetails(String phNumber){
        Registration registrationRow = null;
        try{
            registrationRow = registrationRepo.findByPhNumber(phNumber);
            GenericResponse genericResponse = null;
            if(registrationRow != null){
                genericResponse = GenericResponse.builder()
                                                    .message("Success")
                                                    .status("200")
                                                    .data(registrationRow)
                                                    .build();
            }else{
                genericResponse = GenericResponse.builder()
                                                    .message("No Data Found")
                                                    .status("404")
                                                    .data(null)
                                                    .build();
            }
            return genericResponse;
        }catch(Exception e){
            System.out.println(e);
            GenericResponse genericResponse = GenericResponse.builder()
                                                .message("No Data Found")
                                                .status("404")
                                                .data(null)
                                                .build();
            return genericResponse;
        }
    }
}
