package sg.nus.iss.com.Leaveapp.controller;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import sg.nus.iss.com.Leaveapp.model.Action;
import sg.nus.iss.com.Leaveapp.model.Employee;
import sg.nus.iss.com.Leaveapp.model.Leave;
import sg.nus.iss.com.Leaveapp.model.LeaveEntitlement;
import sg.nus.iss.com.Leaveapp.model.LeaveStatus;
import sg.nus.iss.com.Leaveapp.model.LeaveType;
import sg.nus.iss.com.Leaveapp.repository.EmployeeRepository;
import sg.nus.iss.com.Leaveapp.repository.LeaveEntitlementRepository;
import sg.nus.iss.com.Leaveapp.service.LeaveEntitlementServiceImpl;
import sg.nus.iss.com.Leaveapp.service.LeaveService;

@Controller
@RequestMapping("/leave")
public class LeaveController {
	
	@Autowired
	private LeaveService leaveService;
	
	//No service layer for employee?
	@Autowired
	private EmployeeRepository employeeService;
	
	//No service layer for leaveType?
	@Autowired
	private LeaveEntitlementServiceImpl leaveEntitlementService;
	
	
	@PostMapping("/submitForm")
	public String submitLeaveApplication(@ModelAttribute("leave") Leave leave, HttpSession session, 
            @RequestParam("leaveType") String type, Model model) {
		session.removeAttribute("errors");
		Employee e = (Employee)session.getAttribute("loggedInEmployee");
		LeaveEntitlement ent = leaveEntitlementService.findLeaveEntitlementByType(type, Long.parseLong(e.getRole().getId().toString()));
		
		leave.setEmployee(e);
		leave.setEntitlement(ent);
		
		return validateLeave(leave, e, model, session);
	}
	
	public String validateLeave(Leave leave, Employee employee, Model model, HttpSession session) {
		List<String> errorMessage = new ArrayList<String>();
		List<Leave> existingLeaves = leaveService.findLeavesFromEmployeeId(employee.getId());
		List<Leave> consumedLeaves = existingLeaves
				.stream()
				.filter(l -> l.isConsumedOrConsuming())
				.toList();
		if(leave.getStart().compareTo(leave.getEnd()) > 0) {
			errorMessage.add("Start date cannot be after end date.");
		} 
		if(leave.isOverlappedWith(consumedLeaves)) {
			errorMessage.add("Overlapped with existing leave(s).");
		}
		int balance = leave.getEntitlement().getNumberOfDays() - Leave.consumedDaysOfLeave(consumedLeaves);
		if(balance < leave.getNumberOfDays()) {
			errorMessage.add("Exceed leave balance.");
		}
		
		if(errorMessage.isEmpty()) {
			leaveService.save(leave);
			return "redirect:/leave/saveForm";
		} else {
			System.out.println("errors " + errorMessage.toString());
			session.setAttribute("errors", errorMessage);
			return "redirect:/leave/saveForm";
		}
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/saveForm")
	public String leaveForm(Model model, HttpSession session) {
		model.addAttribute("leave", new Leave());
		Employee e = (Employee)session.getAttribute("loggedInEmployee");
		List<String> leaveTypes = leaveEntitlementService.getLeaveTypesByRole(e.getRole().getName());
		model.addAttribute("leaveTypes", leaveTypes);
		model.addAttribute("action", "leaveSubmitForm");
		model.addAttribute("employeeName", e.getName());
		model.addAttribute("employeeId", e.getId());
		var errorList = session.getAttribute("errors");
		model.addAttribute("hasError", errorList != null && !((List<String>)errorList).isEmpty());
		model.addAttribute("errors", errorList);
		return "index"; // 
	}
	
	@GetMapping("/leaveForm")
	public String submitEmployeeIdToCheck()
	{
		return "submit-employee-tocheck";
	}
	
	
	@PostMapping("/submitHistory")
	public String submitHistoryView(@RequestParam("employeeId") Long employeeId, RedirectAttributes redirectAttributes)
	{
		redirectAttributes.addAttribute("id", employeeId);
		return "redirect:/leave/viewLeaveHistory";
	}
	
	
	@GetMapping("/viewleaveHistory")
	public String leaveHistoryChecker(@RequestParam("id") Long employeeId, Model model)
	{
		List<Leave> leaveList = leaveService.findLeavesFromEmployeeId(employeeId);
		model.addAttribute("leaveList", leaveList);
		List<Action> actions = Action.getAllActions();
		model.addAttribute("actions", actions);
		model.addAttribute("action", "viewleaveHistory");
		return "index";
	}
	

}

