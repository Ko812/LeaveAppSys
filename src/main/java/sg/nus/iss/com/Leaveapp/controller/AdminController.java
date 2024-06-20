package sg.nus.iss.com.Leaveapp.controller;


import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import sg.nus.iss.com.Leaveapp.model.Action;
import sg.nus.iss.com.Leaveapp.model.Employee;
import sg.nus.iss.com.Leaveapp.model.LeaveEntitlement;
import sg.nus.iss.com.Leaveapp.model.LeaveType;
import sg.nus.iss.com.Leaveapp.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    // Dashboard
    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        // Add any additional data needed for the dashboard
        return "dashboard";
    }
    
    // Manage Employees
    @GetMapping("/employees")
    public String getAllEmployees(Model model) {
        model.addAttribute("employees", adminService.getAllEmployees());
        model.addAttribute("action", "employee");
        return "index";
    }
    
    @GetMapping("/employees/add")
    public String addEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("managers", adminService.getManagers());
        model.addAttribute("roles", adminService.getAllRoles()); // Fetching roles
        model.addAttribute("action", "add-employee");
        return "index";
    }
    
    @GetMapping("/employees/edit/{id}")
    public String editEmployeeForm(@PathVariable Long id, Model model) {
        model.addAttribute("employee", adminService.getEmployeeById(id));
        model.addAttribute("managers", adminService.getManagers());
        model.addAttribute("roles", adminService.getAllRoles()); // Fetching roles
        model.addAttribute("action", "edit-employee");
        return "index";
    }
    
    @PostMapping("/employees/save")
    public String saveEmployee(@ModelAttribute Employee employee) {
        adminService.createOrUpdateEmployee(employee);
        return "redirect:/admin/employees";
    }
    
    @GetMapping("/employees/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, Model model) {
    	try {
    		adminService.deleteEmployee(id);
            return "redirect:/admin/employees";
        } catch(DataIntegrityViolationException e) {
        	e.printStackTrace();
        	model.addAttribute("action", "error-message");
        	model.addAttribute("error", "Cannot delete employee due to employee's existing leave records.");
        	return "index";
        } catch(Exception e) {
        	e.printStackTrace();
        	model.addAttribute("action", "error-message");
        	model.addAttribute("error", "Cannot delete employee. " + e.getClass().toString());
        	return "index";
        }
    }
    
    // Manage Leave Types
    @GetMapping("/leavetypes")
    public String getAllLeaveTypes(Model model) {
        model.addAttribute("leaveEntitlements", adminService.getAllLeaveEntitlements());
        model.addAttribute("action", "leavetypes");
        return "index";
    }
    
    @GetMapping("/leavetypes/add")
    public String addLeaveTypeForm(Model model) {
        model.addAttribute("leaveEntitlement", new LeaveEntitlement(0,0));
        model.addAttribute("roles", adminService.getAllRoles());
        model.addAttribute("action", "add-leave-type");
        return "index";
    }
    
    @GetMapping("/leavetypes/edit/{id}")
    public String editLeaveTypeForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("leaveEntitlement", adminService.getLeaveEntitlementById(id));
        model.addAttribute("roles", adminService.getAllRoles());
        model.addAttribute("action", "edit-leave-type");
        return "index";
    }
    
    @PostMapping("/leavetypes/edit/save")
    public String editLeaveTypeForm(@Valid @ModelAttribute("leaveEntitlement") LeaveEntitlement leaveEntitlement, Model model, BindingResult bindingResult) {
    	if(bindingResult.hasErrors()) {
    		model.addAttribute("roles", adminService.getAllRoles());
            model.addAttribute("action", "edit-leave-type");
    		return "index";
    	}
    	adminService.createOrUpdateLeaveType(leaveEntitlement);
        model.addAttribute("action", "leavetypes");
        return "redirect:/admin/leavetypes";
    }
    
    @PostMapping("/leavetypes/save")
    public String saveLeaveType(@Valid @ModelAttribute("leaveEntitlement") LeaveEntitlement entitlement, BindingResult bindingResult, Model model) {
    	if(bindingResult.hasErrors()) {
    		model.addAttribute("action", "add-leave-type");
    		model.addAttribute("roles", adminService.getAllRoles());
    		return "index";
    	}
    	LeaveEntitlement annualLeaveEntitlement = adminService.findLeaveEntitlementByRoleTypeAndYear(entitlement.getRole(), "annual", entitlement.getYear());
    	LeaveEntitlement sickLeaveEntitlement = adminService.findLeaveEntitlementByRoleTypeAndYear(entitlement.getRole(), "medical", entitlement.getYear());
    	if(annualLeaveEntitlement == null) {
    		annualLeaveEntitlement = new LeaveEntitlement("annual", entitlement.getAnnualLeave(), entitlement.getRole(), entitlement.getYear());
    	} else {
    		System.out.println("Updating annual leave entitlement");
    		annualLeaveEntitlement.setNumberOfDays(entitlement.getAnnualLeave());
    	}
    	if(sickLeaveEntitlement == null) {
    		sickLeaveEntitlement = new LeaveEntitlement("medical", entitlement.getSickLeave(), entitlement.getRole(), entitlement.getYear());
    	} else {
    		System.out.println("Updating sick leave entitlement");
    		sickLeaveEntitlement.setNumberOfDays(entitlement.getSickLeave());
    	}
    	adminService.createOrUpdateLeaveType(annualLeaveEntitlement);
    	adminService.createOrUpdateLeaveType(sickLeaveEntitlement);
        return "redirect:/admin/leavetypes";
    }
    
    @GetMapping("/leavetypes/delete/{id}")
    public String deleteLeaveType(@PathVariable Long id, Model model) {
        try {
        	adminService.deleteLeaveEntitlement(id);
            return "redirect:/admin/leavetypes";
        } catch(DataIntegrityViolationException e) {
        	e.printStackTrace();
        	model.addAttribute("action", "error-message");
        	model.addAttribute("error", "Cannot delete entitlement due to existing leave of the given type.");
        	return "index";
        } catch(Exception e) {
        	e.printStackTrace();
        	model.addAttribute("action", "error-message");
        	model.addAttribute("error", "Cannot delete entitlement. " + e.getClass().toString());
        	return "index";
        }
    }
    
    @GetMapping("/calendar")
    public String showCalendar() {
        return "calendar"; // Assuming "calendar.html" is the Thymeleaf template for the calendar view
    }
    
    
}