package com.tym.tract.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tym.tract.Models.Expense;
import com.tym.tract.Models.Group;
import com.tym.tract.Models.GroupMember;
import com.tym.tract.Repositories.ExpenseRepository;
import com.tym.tract.Repositories.GroupRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExpenseService {

	private final GroupRepository groupRepository;
	private final ExpenseRepository expenseRepository;

	// Method to split expenses among group members
	@Transactional
	public void splitExpenses(Long groupId, Map<Long, Double> memberContributions) {
		log.info("Fetching group details for groupId: " + groupId);
		// Fetch the group or throw an error if it doesn't exist
		Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));

		log.info("Calculating total contribution amount...");
		// Calculate the total contribution amount
		double totalAmount = memberContributions.values().stream().mapToDouble(Double::doubleValue).sum();
		log.info("Total amount contributed: " + totalAmount);

		// Get the count of members in the group
		int memberCount = group.getMembers().size();
		log.info("Total members in the group: " + memberCount);

		// Ensure member count is not zero to avoid division by zero errors
		if (memberCount == 0) {
			throw new RuntimeException("No members in the group to split expenses");
		}

		// Calculate the share each member should contribute
		double perMemberShare = totalAmount / memberCount;
		log.info("Per member share: " + perMemberShare);

		// Iterate through the group members to calculate their balance
		for (GroupMember groupMember : group.getMembers()) {
			log.info("Processing member: " + groupMember.getMember().getName());
			// Get the amount contributed by the current member or default to 0.0
			double paidAmount = memberContributions.getOrDefault(groupMember.getMember().getMId(), 0.0);
			log.info("Amount paid by member: " + paidAmount);

			// Calculate the balance (positive if owed, negative if paid extra)
			double balance = paidAmount - perMemberShare;
			log.info("Calculated balance for member: " + balance);

			// Create a new expense record for the member
			Expense expense = new Expense();
			expense.setGroupMember(groupMember);
			expense.setDescription("Split Expense"); // Description of the expense
			expense.setAmount(balance); // Balance amount
			expenseRepository.save(expense); // Save the expense to the repository
			log.info("Expense record saved for member: " + groupMember.getMember().getName());
		}
	}

	// Method to get the expense summary for a group
	public Map<String, Object> getExpenseSummary(Long groupId) {
		log.info("Fetching group details for groupId: " + groupId);
		// Fetch the group or throw an error if it doesn't exist
		Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));

		log.info("Calculating total expenses for the group...");
		// Calculate the total expenses for the group
		double totalExpenses = group.getMembers().stream()
				.flatMap(member -> expenseRepository.findByGroupMember(member).stream())
				.mapToDouble(Expense::getAmount)
				.sum();
		log.info("Total expenses for the group: " + totalExpenses);

		// Prepare a list to store expense summaries for each member
		List<Map<String, Object>> perMemberExpenses = new ArrayList<>();
		for (GroupMember member : group.getMembers()) {
			log.info("Fetching expenses for member: " + member.getMember().getName());
			// Calculate the total amount paid by the current member
			double totalPaid = expenseRepository.findByGroupMember(member).stream()
					.mapToDouble(Expense::getAmount)
					.sum();
			log.info("Total amount paid by member: " + totalPaid);

			// Create a summary for the current member
			Map<String, Object> memberSummary = new HashMap<>();
			memberSummary.put("memberId", member.getMember().getMId()); // Member ID
			memberSummary.put("name", member.getMember().getName()); // Member Name
			memberSummary.put("paid", totalPaid); // Total amount paid
			memberSummary.put("owed", totalPaid - (totalExpenses / group.getMembers().size())); // Amount owed
			log.info("Summary for member: " + memberSummary);

			perMemberExpenses.add(memberSummary);
		}

		// Create the final summary map
		Map<String, Object> summary = new HashMap<>();
		summary.put("totalExpenses", totalExpenses); // Total expenses of the group
		summary.put("perMemberExpenses", perMemberExpenses); // List of member-specific expenses
		log.info("Expense summary prepared: " + summary);

		return summary; // Return the expense summary
	}
}
