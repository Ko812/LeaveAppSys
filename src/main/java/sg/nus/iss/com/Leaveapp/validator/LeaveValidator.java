package sg.nus.iss.com.Leaveapp.validator;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import sg.nus.iss.com.Leaveapp.model.Leave;

@Component
public class LeaveValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Leave.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Leave leave = (Leave) target;
		
//		List<String> errorMessage = new ArrayList<String>();
//		List<Leave> existingLeaves = leaveService.findLeavesFromEmployeeId(employee.getId());
//		List<Leave> consumedLeaves = existingLeaves
//				.stream()
//				.filter(l -> l.isConsumedOrConsuming())
//				.toList();
//		if(leave.getStart().compareTo(leave.getEnd()) > 0) {
//			errorMessage.add("Start date cannot be after end date.");
//		} 
//		if(leave.isOverlappedWith(consumedLeaves)) {
//			errorMessage.add("Overlapped with existing leave(s).");
//		}
//		int balance = leave.getEntitlement().getNumberOfDays() - Leave.consumedDaysOfLeave(consumedLeaves);
//		if(balance < leave.getNumberOfDays()) {
//			errorMessage.add("Exceed leave balance.");
//		}
//		
//		if(errorMessage.isEmpty()) {
//			leaveService.save(leave);
//			return "redirect:/leave/saveForm";
//		} else {
//			System.out.println("errors " + errorMessage.toString());
//			session.setAttribute("errors", errorMessage);
//			return "redirect:/leave/saveForm";
//		}
		
		
	}

}
