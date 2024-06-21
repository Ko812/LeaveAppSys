package sg.nus.iss.com.Leaveapp.controller;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


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

	@PostMapping("/approve")
	public String approveLeave(@ModelAttribute("leave") Leave leave, Model model) {
		leaveApproveService.approveLeave(leave);
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

	@PostMapping("/reject")
	public String rejectLeave(@ModelAttribute("leave") Leave leave) {
		leaveApproveService.rejectLeave(leave);
//		if(rejectedLeave == null) {
//			model.addAttribute("action", "error-message");
//			model.addAttribute("error", "Leave approval failed.");
//		} else {
//			model.addAttribute("action", "show-message");
//			model.addAttribute("message", "Leave approved successfully.");
//		}
		return "redirect:/manager/applications";
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
	public String searchEmployee(@RequestParam("employeeName") String employeeName, Model model) {
		List<Employee> employees = managerService.findEmployeesByName(employeeName);
		if (!employees.isEmpty()) {
			model.addAttribute("employeesFound", true);
			model.addAttribute("employees", employees);
		} else {
			model.addAttribute("employeesFound", false);
		}
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

    @GetMapping("/leaveapprove/details/{id}")
    public String getDetail(@PathVariable("id") Long id, Model model) {
        Leave leave = leaveApproveService.getById(id);
        System.out.println("view details");
        if (leave != null) {
            model.addAttribute("leaves", leaveApproveService.findLeavesByEmployeeIdOrderByIdDesc(leave.getEmployee().getId()));
            model.addAttribute("leave", leave);
            model.addAttribute("action", "show-leave-details");
            
            return "index";
        } else {
            model.addAttribute("leaves", leaveApproveService.findAllByOrderByIdDesc());
            return "index";
        }
    }
}

