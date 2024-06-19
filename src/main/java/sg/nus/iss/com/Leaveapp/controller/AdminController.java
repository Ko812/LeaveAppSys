package sg.nus.iss.com.Leaveapp.controller;


import java.util.HashMap;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
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
    public String deleteEmployee(@PathVariable Long id) {
        adminService.deleteEmployee(id);
        return "redirect:/admin/employees";
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
        model.addAttribute("leaveEntitlement", new LeaveEntitlement());
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
    
    @PostMapping("/leavetypes/save")
    public String saveLeaveType(@Valid @ModelAttribute("leaveEntitlement") LeaveEntitlement entitlement, BindingResult bindingResult) {
    	if(bindingResult.hasErrors()) {
    		return "index";
    	}
    	HashMap<String, Integer> entitlements = new HashMap<String, Integer>();
    	entitlements.put("annual", entitlement.getAnnualLeave());
    	entitlements.put("medical", entitlement.getSickLeave());
    	entitlements.put("compensation", entitlement.getCompensationLeave());
    	adminService.createOrUpdateLeaveType(entitlement.getRole(), entitlements, entitlement.getYear());
        return "redirect:/admin/leavetypes";
    }
    
    @GetMapping("/leavetypes/delete/{id}")
    public String deleteLeaveType(@PathVariable Long id) {
        adminService.deleteLeaveEntitlement(id);
        return "redirect:/admin/leavetypes";
    }
    
    @GetMapping("/calendar")
    public String showCalendar() {
        return "calendar"; // Assuming "calendar.html" is the Thymeleaf template for the calendar view
    }
    
    
}