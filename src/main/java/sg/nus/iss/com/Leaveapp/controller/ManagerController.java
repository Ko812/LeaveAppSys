package sg.nus.iss.com.Leaveapp.controller;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

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
		return "dashboard";
	}

	@RequestMapping("/approve/{id}")
	public String approveLeave(@PathVariable("id") Long id, Model model) {
		leaveApproveService.approveLeave(id);
//		if(approvedLeave == null) {
//			model.addAttribute("action", "error-message");
//			model.addAttribute("error", "Leave approval failed.");
//		} else {
//			model.addAttribute("action", "show-message");
//			model.addAttribute("message", "Leave approved successfully.");
//		}
		return "redirect:/manager/applications";
		//return Applications for Approval

	}

	@GetMapping("/reject/{id}")
	public String rejectLeave(@PathVariable("id") Long id, Model model) {
		leaveApproveService.rejectLeave(id);
//		if(rejectedLeave == null) {
//			model.addAttribute("action", "error-message");
//			model.addAttribute("error", "Leave approval failed.");
//		} else {
//			model.addAttribute("action", "show-message");
//			model.addAttribute("message", "Leave approved successfully.");
//		}
		return "index";
	}
	

	@GetMapping("/applications")
	public String viewApplicationsForApproval(Model model, HttpSession session) {
		Employee manager = (Employee) session.getAttribute("loggedInEmployee");
	    List<Leave> leaveApplications = managerService.getLeaveApplicationsForApproval(manager.getId());
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
	public String viewEmployeeLeaveHistory(Model model, HttpSession session) {
		Employee manager = (Employee) session.getAttribute("loggedInEmployee");
	    List<Employee> employees = managerService.findReporteeEmployeeByManagerId(manager.getId()); // Assuming you have a method to get all employees
	    model.addAttribute("employees", employees);
	    model.addAttribute("action", "employeeHistory");
	    return "index"; // Create a new HTML file for displaying the employees and their leave history
	}
	

	// @PostMapping("/searchEmployee")
	// public String searchEmployee(@RequestParam("employeeName") String employeeName, Model model) {
	//     Employee employee = managerService.findEmployeeByName(employeeName);
	//     if (employee != null) {
	//         model.addAttribute("employeeFound", true);
	//         model.addAttribute("employee", employee);
	//     } else {
	//         model.addAttribute("employeeFound", false);
	//     }
	//     model.addAttribute("action", "employee-leave-history");
	//     return "index";
	// }

	@PostMapping("/searchEmployee")
	public String searchEmployee(@RequestParam("employeeName") String employeeName, Model model, HttpSession session) {
	    Employee manager = (Employee) session.getAttribute("loggedInEmployee");
	    List<Employee> reporteeEmployee = managerService.findReporteeEmployeeByManagerId(manager.getId());

	    List<Employee> filteredEmployees = managerService.findEmployeesByName(employeeName).stream()
	            .filter(reporteeEmployee::contains)
	            .collect(Collectors.toList());

	    model.addAttribute("employeesFound", !filteredEmployees.isEmpty());
	    model.addAttribute("employees", filteredEmployees);
	    model.addAttribute("action", "search-employee-history");

	    return "index";
	}


	
	// @GetMapping("/history/{id}")
	// public String viewEmployeeLeaveHistory(@PathVariable("id") Long id, Model model) {
	// 	Employee employee = managerService.getEmployeeById(id);
	// 	List<Leave> leaveHistory = managerService.getEmployeeLeaveHistory(employee);
	// 	model.addAttribute("employee", employee);
	// 	model.addAttribute("employeeFound", employee != null);
	// 	model.addAttribute("leaveHistory", leaveHistory);
	// 	model.addAttribute("action", "employee-leave-history");
	// 	return "index"; // Create a new HTML file for displaying the leave history
	// }

	@GetMapping("/history/{id}")
	public String viewEmployeeLeaveHistory(@PathVariable("id") Long id, Model model) {
		Employee employee = managerService.getEmployeeById(id);
		List<Leave> leaveHistory = managerService.getEmployeeLeaveHistory(employee);
		model.addAttribute("employee", employee);
		model.addAttribute("leaveHistory", leaveHistory);
		model.addAttribute("action", "employee-leave-history");
		return "index";
	}

    @GetMapping("/leaveapprove/list")
    public String listLeaves(@RequestParam(value = "status", required = false) int status, Model model) {
        if (status != 0) {

        	model.addAttribute("leaves", leaveApproveService.findLeavesByStatusOrderByStartDesc(status));
        } 
//        } else {
//        	model.addAttribute("leaves", leaveApproveService.findAllByOrderByStartDesc());
//
//        }
        
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
            model.addAttribute("leaves", leaveApproveService.findLeavesByEmployeeIdOrderByIdDesc(leave.getEmployee().getId()));
            model.addAttribute("leave", leave);
            return "leave-details";
        } else {
            model.addAttribute("leaves", leaveApproveService.findAllByOrderByIdDesc());
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

