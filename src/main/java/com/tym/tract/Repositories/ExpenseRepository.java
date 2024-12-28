package com.tym.tract.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tym.tract.Models.Expense;
import com.tym.tract.Models.GroupMember;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	// To find expenses by group member
	List<Expense> findByGroupMember(GroupMember groupMember);
}
