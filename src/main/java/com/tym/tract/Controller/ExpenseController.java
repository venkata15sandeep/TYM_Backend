package com.tym.tract.Controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tym.tract.Services.ExpenseService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

	private final ExpenseService expenseService;

	public ExpenseController(ExpenseService expenseService) {
		this.expenseService = expenseService;
	}

	// To split expenses for a group
	@PostMapping("/{groupId}/split-expenses")
	public void splitExpenses(@PathVariable Long groupId, @RequestBody Map<Long, Double> memberContributions) {
		log.info("Received request to split expenses for groupId: " + groupId);
		expenseService.splitExpenses(groupId, memberContributions);
		log.info("Split expenses completed for groupId: " + groupId);
	}

	// To fetch the expense summary of a group
	@GetMapping("/{groupId}/summary")
	public Map<String, Object> getExpenseSummary(@PathVariable Long groupId) {
		log.info("Received request to get expense summary for groupId: " + groupId);
		Map<String, Object> summary = expenseService.getExpenseSummary(groupId);
		log.info("Expense summary fetched for groupId: " + groupId);
		return summary;
	}
}
