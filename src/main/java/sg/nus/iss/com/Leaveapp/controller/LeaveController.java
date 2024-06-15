package sg.nus.iss.com.Leaveapp.controller;

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

import sg.nus.iss.com.Leaveapp.model.Action;
import sg.nus.iss.com.Leaveapp.model.Employee;
import sg.nus.iss.com.Leaveapp.model.Leave;
import sg.nus.iss.com.Leaveapp.model.LeaveType;
import sg.nus.iss.com.Leaveapp.repository.EmployeeRepository;
import sg.nus.iss.com.Leaveapp.repository.LeaveTypeRepository;
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
	private LeaveTypeRepository leaveTypeService;
	
	
	@PostMapping("/submitForm")
	public String submitLeaveApplication(@ModelAttribute("leave") Leave leave,@RequestParam("employeeId") Long employeeId, 
            @RequestParam("leaveType") Long leaveTypeId) {
		
		Employee e = employeeService.findEmployeeRoleById(employeeId);
		LeaveType t = leaveTypeService.findLeaveTypeById(leaveTypeId);
		
		leave.setEmployee(e);
		leave.setType(t);
		
		leaveService.save(leave);

		return "redirect:/saveForm"; 
	}
	
	@GetMapping("/saveForm")
	public String leaveForm(Model model) {
		model.addAttribute("leave", new Leave());
		model.addAttribute("action", "leaveSubmitForm");
		List<Action> actions = Action.getAllActions();
		model.addAttribute("actions", actions);
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

