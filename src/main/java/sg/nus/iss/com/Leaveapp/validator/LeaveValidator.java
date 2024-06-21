package sg.nus.iss.com.Leaveapp.validator;


import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import sg.nus.iss.com.Leaveapp.model.Leave;
import sg.nus.iss.com.Leaveapp.service.LeaveService;

@Component
public class LeaveValidator implements Validator{

	@Autowired
	private LeaveService leaveService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Leave.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Leave leave = (Leave) target;
		
		List<Leave> existingLeaves = leaveService.findLeavesFromEmployeeId(leave.getEmployee().getId());
		List<Leave> consumedLeaves = existingLeaves
				.stream()
				.filter(l -> l.isConsumedOrConsuming())
				.toList();
		if(leave.getStart().getDayOfWeek().compareTo(DayOfWeek.SATURDAY) == 0 || leave.getStart().getDayOfWeek().compareTo(DayOfWeek.SUNDAY) == 0) {
			errors.rejectValue("start", "error.start", "Start date must be working day.");
		}
		if(leave.getStart().compareTo(leave.getEnd()) > 0) {
			errors.rejectValue("start", "error.start", "Start date must be before end date.");
		}
		if(leave.getEnd().getDayOfWeek().compareTo(DayOfWeek.SATURDAY) == 0 || leave.getEnd().getDayOfWeek().compareTo(DayOfWeek.SUNDAY) == 0) {
			errors.rejectValue("end", "error.end", "End date must be working day.");
		}
		if(leave.isOverlappedWith(consumedLeaves)) {
			errors.rejectValue("end", "error.end", "Leave date overlapped with existing leaves.");
		}
		int balance = leave.getEntitlement().getNumberOfDays() - Leave.consumedDaysOfLeave(consumedLeaves, leave.getEntitlement().getLeaveType());
		System.out.println("Entitlement: " +leave.getEntitlement().getNumberOfDays() + ". Validating leave balance: " + balance);
		if(balance < leave.getNumberOfDays()) {
			errors.rejectValue("numberOfDays", "error.numberOfDays", "Number of days applying exceed leave balance.");
		}

		
		
	}

}
