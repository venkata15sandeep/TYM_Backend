package com.tym.tract.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tym.tract.Models.Registration;


@Repository
public interface RegistrationRepo extends JpaRepository<Registration, String> {
    public Registration findPasswordByPhNumber(String phNumber);
}