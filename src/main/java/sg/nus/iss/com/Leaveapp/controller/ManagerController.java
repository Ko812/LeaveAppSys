package sg.nus.iss.com.Leaveapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PatchMapping;

import sg.nus.iss.com.Leaveapp.model.Employee;
import sg.nus.iss.com.Leaveapp.model.Leave;
import sg.nus.iss.com.Leaveapp.model.LeaveType;
import sg.nus.iss.com.Leaveapp.model.LeaveEntitlement;
import sg.nus.iss.com.Leaveapp.model.LeaveStatus;
import sg.nus.iss.com.Leaveapp.service.LeaveService;
import sg.nus.iss.com.Leaveapp.service.ManagerService;

@Controller
@RequestMapping("/manager")
public class ManagerController {
	//extends StaffController 
	
	@Autowired
	private ManagerService managerService;
	
	@GetMapping("/dashboard")
	public String managerDashboard(Model model) {
		// different from admin
		return "dashboard";
	}

	@PatchMapping("/approve/{id}")
	public String approveLeave(@PathVariable("id") int id) {
		//managerService.approveLeave(id);
		return "redirect:/manager/dashboard";
		//return Applications for Approval

	}

	@PatchMapping("/reject/{id}")
	public String rejectLeave(@PathVariable("id") int id) {
		//managerService.rejectLeave(id);
		return "redirect:/manager/dashboard";
		//return Applications for Approval
	}
	

	@GetMapping("/applications")
	public String viewApplicationsForApproval(Model model) {
	    List<Leave> leaveApplications = managerService.getLeaveApplicationsForApproval();
	    model.addAttribute("leaveApplications", leaveApplications);
	    return "applications-approval"; // Create a new HTML file for displaying the applications
	}
	
	@GetMapping("/application-details/{id}")
	public String viewApplicationDetails(@PathVariable("id") Long id, Model model) {
	    Leave leaveApplication = managerService.getLeaveApplicationById(id);
	    model.addAttribute("leaveApplication", leaveApplication);
	    return "application-details";
	}
	
	@GetMapping("/employeeHistory")
	public String viewEmployeeLeaveHistory(Model model) {
	    List<Employee> employees = managerService.getAllEmployees(); // Assuming you have a method to get all employees
	    model.addAttribute("employees", employees);
	    return "employee-leave-history-list"; // Create a new HTML file for displaying the employees and their leave history
	}

	@PostMapping("/searchEmployee")
	public String searchEmployee(@RequestParam("employeeName") String employeeName, Model model) {
	    Employee employee = managerService.findEmployeeByName(employeeName);
	    if (employee != null) {
	        model.addAttribute("employeeFound", true);
	        model.addAttribute("employee", employee);
	    } else {
	        model.addAttribute("employeeFound", false);
	    }
	    return "employee-leave-history";
	}

	
	@GetMapping("/history/{employeeId}")
    public String viewEmployeeLeaveHistory(@PathVariable("employeeId") Long employeeId, Model model) {
        Employee employee = managerService.getEmployeeById(employeeId);
        List<Leave> leaveHistory = managerService.getEmployeeLeaveHistory(employee);
        model.addAttribute("employee", employee);
        model.addAttribute("leaveHistory", leaveHistory);
        return "employee-leave-history"; // Create a new HTML file for displaying the leave history
    }
}
