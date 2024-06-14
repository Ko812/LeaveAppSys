package sg.nus.iss.com.Leaveapp.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import sg.nus.iss.com.Leaveapp.model.LeaveEntitlement;
import sg.nus.iss.com.Leaveapp.service.LeaveEntitlementService;

@Controller
public class LeaveEntitlementController {
	
	@Autowired
	private LeaveEntitlementService leaveEntitlementService;
	
	
	
	
	@GetMapping ("/leave-entitlements")
	public String getAllLeaveEntitlements(Model model)
	{
		List<LeaveEntitlement> leaveEntitlements = leaveEntitlementService.findAllLeaveEntitlements();
		model.addAttribute("leaveEntitlements", leaveEntitlements);
		return "leave-entitlement-list";
	
	}
	
	@GetMapping ("/leave-entitlements/{id}")
	public String getLeaveEntitlementsById(@PathVariable("id") int id, Model model)
	{
		List<LeaveEntitlement> leaveEntitlement = leaveEntitlementService.getLeaveEntitlementByEmployeeID(id);
		model.addAttribute("leaveEntitlement", leaveEntitlement);
		return "leave-entitlement-detail";
	
	}
	
	
	@GetMapping("/leave-entitlements/new")
	  public String showLeaveEntitlementById(Model model) {
	    LeaveEntitlement leaveEntitlement = new LeaveEntitlement();
	    model.addAttribute("leaveEntitlement", leaveEntitlement);
	    return "leave-entitlement-form";
	}
	
	
	

    
	@PostMapping("/leave-entitlements/new")
	public String saveLeaveEntitlement(@Valid @ModelAttribute("leaveEntitlement")  LeaveEntitlement leaveEntitlement,BindingResult result)
	{
		if(result.hasErrors())
			{
			return "leave-entitlement-form";
			}
	
	    leaveEntitlementService.saveLeaveEntitlement(leaveEntitlement);
		return"redirect:/leave-entitlements";
	}
	
	@GetMapping("/leave-entitlements/delete/{id}")
	public String deleteLeaveEntitlement(@PathVariable("id") int id)
	{
		leaveEntitlementService.deleteLeaveEntitlement(id);
		return "redirect:/leave-entitlements";
		
	}
	

}
