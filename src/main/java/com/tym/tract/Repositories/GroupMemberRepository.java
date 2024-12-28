package com.tym.tract.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tym.tract.Models.GroupMember;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
}
