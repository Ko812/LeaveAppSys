package sg.nus.iss.com.Leaveapp.controller;


import java.util.HashMap;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.nus.iss.com.Leaveapp.model.Action;
import sg.nus.iss.com.Leaveapp.model.Employee;
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
        List<Action> actions = Action.getAllActions();
		model.addAttribute("actions", actions);
        return "index";
    }
    
    @GetMapping("/employees/add")
    public String addEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("action", "add-employee");
        return "index";
    }
    
    @GetMapping("/employees/edit/{id}")
    public String editEmployeeForm(@PathVariable Long id, Model model) {
        model.addAttribute("employee", adminService.getEmployeeById(id));
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
//        model.addAttribute("leaveTypes", adminService.getAllLeaveTypes());
        model.addAttribute("action", "leavetypes");
        return "index";
    }
    
    @GetMapping("/leavetypes/add")
    public String addLeaveTypeForm(Model model) {
        model.addAttribute("leaveType", new LeaveType());
        return "leave_type_form";
    }
    
    @GetMapping("/leavetypes/edit/{id}")
    public String editLeaveTypeForm(@PathVariable Long id, Model model) {
//        model.addAttribute("leaveType", adminService.getLeaveTypeById(id));
        return "leave_type_form";
    }
    
    @PostMapping("/leavetypes/save")
    public String saveLeaveType(@ModelAttribute String type, Integer staffEntitlement, Integer adminEntitlement, Integer managerEntitlement, int year) {
    	HashMap<String, Integer> entitlements = new HashMap<String, Integer>();
    	entitlements.put("employee", staffEntitlement);
    	entitlements.put("admin", adminEntitlement);
    	entitlements.put("manager", managerEntitlement);
    	adminService.createOrUpdateLeaveType(type, entitlements, year);
        return "redirect:/admin/leavetypes";
    }
    
    @GetMapping("/leavetypes/delete/{id}")
    public String deleteLeaveType(@PathVariable Long id) {
//        adminService.deleteLeaveType(id);
        return "redirect:/admin/leavetypes";
    }
    
    @GetMapping("/calendar")
    public String showCalendar() {
        return "calendar"; // Assuming "calendar.html" is the Thymeleaf template for the calendar view
    }
    
    
}