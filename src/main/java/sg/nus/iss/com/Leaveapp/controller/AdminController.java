package sg.nus.iss.com.Leaveapp.controller;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import sg.nus.iss.com.Leaveapp.model.Action;
import sg.nus.iss.com.Leaveapp.model.Employee;
import sg.nus.iss.com.Leaveapp.model.LeaveEntitlement;
import sg.nus.iss.com.Leaveapp.model.LeaveType;
import sg.nus.iss.com.Leaveapp.service.AdminService;
import sg.nus.iss.com.Leaveapp.validator.EmployeeValidator;
import sg.nus.iss.com.Leaveapp.validator.LeaveEntitlementValidator;
import sg.nus.iss.com.Leaveapp.validator.LeaveValidator;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    
//    @Autowired
//	private LeaveEntitlementValidator leaveEntitlementValidator;
//    
//    @Autowired
//	private EmployeeValidator employeeValidator;
//	
//	@InitBinder
//	private void initCourseBinder(WebDataBinder binder) {
//		binder.addValidators(leaveEntitlementValidator);
//		binder.addValidators(employeeValidator);
//	}
    
    // Dashboard
    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        // Add any additional data needed for the dashboard
        return "dashboard";
    }
    
    // Manage Employees
    
    @GetMapping("/employees")
    public String getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
    	Page<Employee> employeesPage = adminService.getAllEmployees(page, size);
        model.addAttribute("employees", employeesPage.getContent());
        model.addAttribute("currentPage", employeesPage.getNumber());
        model.addAttribute("totalPages", employeesPage.getTotalPages());
        model.addAttribute("totalItems", employeesPage.getTotalElements());
        model.addAttribute("size", size);
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
    public String getAllLeaveTypes(@RequestParam(value = "year", required = false) Integer year, Model model) {
    	List<LeaveEntitlement> leaveEntitlements;
        if (year != null) {
            leaveEntitlements = adminService.getLeaveEntitlementsByYear(year);
        } else {
            leaveEntitlements = adminService.getAllLeaveEntitlements();
        }
        model.addAttribute("leaveEntitlements", leaveEntitlements);
        model.addAttribute("action", "leavetypes");
        model.addAttribute("years", getYears());  // Populate years for the dropdown
        return "index";
    }
    
    @GetMapping("/leavetypes/add")
    public String addLeaveTypeForm(Model model) {
        model.addAttribute("leaveEntitlement", new LeaveEntitlement(0, 0));
        model.addAttribute("roles", adminService.getAllRoles());
        model.addAttribute("years", getYears());  // Populate years for the dropdown
        model.addAttribute("action", "add-leave-type");
        return "index";
    }
    
    @PostMapping("/leavetypes/save")
    public String saveLeaveType1(@Valid @ModelAttribute("leaveEntitlement") LeaveEntitlement entitlement, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "add-leave-type");
            model.addAttribute("roles", adminService.getAllRoles());
            model.addAttribute("years", getYears());
            return "index";
        }

        LeaveEntitlement existingAnnualLeaveEntitlement = adminService.findLeaveEntitlementByRoleTypeAndYear(entitlement.getRole(), "annual", entitlement.getYear());
        LeaveEntitlement existingSickLeaveEntitlement = adminService.findLeaveEntitlementByRoleTypeAndYear(entitlement.getRole(), "medical", entitlement.getYear());

        if (existingAnnualLeaveEntitlement != null && entitlement.getAnnualLeave() != 0 &&

        	    (existingAnnualLeaveEntitlement.getRole().getName().compareTo(entitlement.getRole().getName()) == 0 &&
        	      existingAnnualLeaveEntitlement.getYear() == entitlement.getYear())) {
        	model.addAttribute("action", "error-message");
        	model.addAttribute("error", "Annual leave entitlement for this role and year already exists.");
        	return "index";
//        	    bindingResult.rejectValue("annualLeave", "error.leaveEntitlement", "Annual leave entitlement for this role and year already exists.");

        	}

        if (existingSickLeaveEntitlement != null && entitlement.getSickLeave() != 0 &&
        	    (existingSickLeaveEntitlement.getRole().getName().compareTo(entitlement.getRole().getName()) == 0 &&
        	      existingSickLeaveEntitlement.getYear() == entitlement.getYear())) {

        	model.addAttribute("action", "error-message");
        	model.addAttribute("error", "Sick leave entitlement for this role and year already exists.");
        	return "index";
//        	bindingResult.rejectValue("sickLeave", "error.leaveEntitlement", "Sick leave entitlement for this role and year already exists.");

        	}

//        if (bindingResult.hasErrors()) {
//            model.addAttribute("action", "add-leave-type");
//            model.addAttribute("roles", adminService.getAllRoles());
//            model.addAttribute("years", getYears());
//            return "index";
//        }

        LeaveEntitlement annualLeaveEntitlement = (existingAnnualLeaveEntitlement != null) ?
                existingAnnualLeaveEntitlement : new LeaveEntitlement("annual", entitlement.getAnnualLeave(), entitlement.getRole(), entitlement.getYear());
        LeaveEntitlement sickLeaveEntitlement = (existingSickLeaveEntitlement != null) ?
                existingSickLeaveEntitlement : new LeaveEntitlement("medical", entitlement.getSickLeave(), entitlement.getRole(), entitlement.getYear());

        adminService.createOrUpdateLeaveType(annualLeaveEntitlement);
        adminService.createOrUpdateLeaveType(sickLeaveEntitlement);

        return "redirect:/admin/leavetypes";
    }
    
    private List<Integer> getYears() {
        List<Integer> years = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i <= currentYear + 5; i++) {
            years.add(i);
        }
        return years;
    }



    
//    @GetMapping("/leavetypes/add")
//    public String addLeaveTypeForm(Model model) {
//        model.addAttribute("leaveEntitlement", new LeaveEntitlement(0,0));
//        model.addAttribute("roles", adminService.getAllRoles());
//        model.addAttribute("action", "add-leave-type");
//        return "index";
//    }
    
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
    
//    @PostMapping("/leavetypes/save")
//    public String saveLeaveType(@Valid @ModelAttribute("leaveEntitlement") LeaveEntitlement entitlement, BindingResult bindingResult, Model model) {
//    	if(bindingResult.hasErrors()) {
//    		model.addAttribute("action", "add-leave-type");
//    		model.addAttribute("roles", adminService.getAllRoles());
//    		return "index";
//    	}
//    	LeaveEntitlement annualLeaveEntitlement = adminService.findLeaveEntitlementByRoleTypeAndYear(entitlement.getRole(), "annual", entitlement.getYear());
//    	LeaveEntitlement sickLeaveEntitlement = adminService.findLeaveEntitlementByRoleTypeAndYear(entitlement.getRole(), "medical", entitlement.getYear());
//    	if(annualLeaveEntitlement == null) {
//    		annualLeaveEntitlement = new LeaveEntitlement("annual", entitlement.getAnnualLeave(), entitlement.getRole(), entitlement.getYear());
//    	} else {
//    		System.out.println("Updating annual leave entitlement");
//    		annualLeaveEntitlement.setNumberOfDays(entitlement.getAnnualLeave());
//    	}
//    	if(sickLeaveEntitlement == null) {
//    		sickLeaveEntitlement = new LeaveEntitlement("medical", entitlement.getSickLeave(), entitlement.getRole(), entitlement.getYear());
//    	} else {
//    		System.out.println("Updating sick leave entitlement");
//    		sickLeaveEntitlement.setNumberOfDays(entitlement.getSickLeave());
//    	}
//    	adminService.createOrUpdateLeaveType(annualLeaveEntitlement);
//    	adminService.createOrUpdateLeaveType(sickLeaveEntitlement);
//        return "redirect:/admin/leavetypes";
//    }
    
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