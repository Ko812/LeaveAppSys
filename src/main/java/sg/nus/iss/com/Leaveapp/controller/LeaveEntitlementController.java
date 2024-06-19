package sg.nus.iss.com.Leaveapp.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import sg.nus.iss.com.Leaveapp.model.Action;
import sg.nus.iss.com.Leaveapp.model.LeaveEntitlement;
import sg.nus.iss.com.Leaveapp.service.LeaveEntitlementService;


@Controller
@RequestMapping("/admin")
public class LeaveEntitlementController {
	
	@Autowired
	private LeaveEntitlementService leaveEntitlementService;
	
	
	
	
	@GetMapping ("/leave-entitlements")
	public String getAllLeaveEntitlements(Model model)
	{
		List<LeaveEntitlement> leaveEntitlements = leaveEntitlementService.findAllLeaveEntitlements();
		model.addAttribute("leaveEntitlements", leaveEntitlements);

		model.addAttribute("action", "view-leave-entitlements");

		model.addAttribute("actions", Action.getAllActions());
		return "leave-entitlement-form";
	
	}
	
	@GetMapping ("/leave-entitlements/{id}")
	public String getLeaveEntitlementById(@PathVariable("id") int id, Model model)
	{
		LeaveEntitlement leaveEntitlement = leaveEntitlementService.getLeaveEntitlementById(id); // Updated to return a single LeaveEntitlement
	    if (leaveEntitlement == null) {
	        // Handle the case where the leave entitlement with the given id is not found
	        return "redirect:/leave-entitlements";
	    }
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
	//  method for updating a leave entitlement
	@PostMapping("/leave-entitlements/update/{id}")
	public String updateLeaveEntitlement(@PathVariable("id") int id, @ModelAttribute("updatedEntitlement") LeaveEntitlement updatedEntitlement) {
	    LeaveEntitlement updated = leaveEntitlementService.updateLeaveEntitlement(id, updatedEntitlement);
	    if (updated == null) {
	   //  leave entitlement with the given id is not found
	        return "redirect:/leave-entitlements";
	    }
	    return "redirect:/leave-entitlements/" + id;
	}


}
