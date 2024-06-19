package sg.nus.iss.com.Leaveapp.controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.*;

import sg.nus.iss.com.Leaveapp.model.Leave;
import sg.nus.iss.com.Leaveapp.model.LeaveStatus;
import sg.nus.iss.com.Leaveapp.service.LeaveApproveService;

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
  
  @Autowired
  private LeaveApproveService leaveApproveService;
	
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
	    model.addAttribute("action", "leaveApplications");
	    return "index"; // Create a new HTML file for displaying the applications
	}
	
	@GetMapping("/application-details/{id}")
	public String viewApplicationDetails(@PathVariable("id") Long id, Model model) {
	    Leave leaveApplication = managerService.getLeaveApplicationById(id);
	    model.addAttribute("leaveApplication", leaveApplication);
	    model.addAttribute("action", "application-details");
	    return "index";
	}
	
	@GetMapping("/employeeHistory")
	public String viewEmployeeLeaveHistory(Model model) {
	    List<Employee> employees = managerService.getAllEmployees(); // Assuming you have a method to get all employees
	    model.addAttribute("employees", employees);
	    model.addAttribute("action", "employeeHistory");
	    return "index"; // Create a new HTML file for displaying the employees and their leave history
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
	    model.addAttribute("action", "employee-leave-history");
	    return "index";
	}

	
	@GetMapping("/history/{employeeId}")
  public String viewEmployeeLeaveHistory(@PathVariable("employeeId") Long employeeId, Model model) {
        Employee employee = managerService.getEmployeeById(employeeId);
        List<Leave> leaveHistory = managerService.getEmployeeLeaveHistory(employee);
        model.addAttribute("employee", employee);
        model.addAttribute("leaveHistory", leaveHistory);
        return "employee-leave-history"; // Create a new HTML file for displaying the leave history
  }

    @GetMapping("/leaveapprove/list")
    public String listLeaves(@RequestParam(value = "status", required = false) String status, Model model) {
        if (status != null && !status.isEmpty()) {
            LeaveStatus value = LeaveStatus.valueOf(status);
            model.addAttribute("leaves", leaveApproveService.findLeavesByStatusOrderByStartDesc(value));
            
        } else {
            model.addAttribute("leaves", leaveApproveService.findAllByOrderByStartDesc());
        }
        return "leave-list";
    }

    @GetMapping("/leaveapprove/leave-applications")
    public String showLeaveApplications() {
        return "leave-applications";
    }

    @GetMapping("/leaveapprove/detail")
    public String getDetail(@RequestParam Long id, Model model) {
        Leave leave = leaveApproveService.getById(id);
        if (leave != null) {
            model.addAttribute("leaves", leaveApproveService.findLeavesByEmployeeIdOrderByStartDesc(leave.getEmployee().getId()));
            model.addAttribute("leave", leave);
            return "leave-details";
        } else {
            model.addAttribute("leaves", leaveApproveService.findAllByOrderByStartDesc());
            return "leave-list";
        }
    }

    @PostMapping("/leaveapprove/approve")
    public String approveLeave(@RequestParam Long id) {
        // Approve
        leaveApproveService.approveLeave(id);
        return "redirect:/leaveapprove/list";
    }

    @PostMapping("/leaveapprove/reject")
    public String rejectLeave(@RequestParam Long id, @RequestParam String comment) {
        // reject and comment
        leaveApproveService.rejectLeave(id, comment);
        return "redirect:/leaveapprove/list";
    }
}

