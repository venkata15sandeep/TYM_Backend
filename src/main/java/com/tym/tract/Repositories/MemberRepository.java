package com.tym.tract.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tym.tract.Models.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
