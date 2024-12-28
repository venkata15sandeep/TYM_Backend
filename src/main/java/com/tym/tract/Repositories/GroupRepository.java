package com.tym.tract.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tym.tract.Models.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
