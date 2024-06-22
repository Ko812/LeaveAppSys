package sg.nus.iss.com.Leaveapp.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.nus.iss.com.Leaveapp.model.Claim;
import sg.nus.iss.com.Leaveapp.model.Employee;
import sg.nus.iss.com.Leaveapp.model.Leave;
import sg.nus.iss.com.Leaveapp.model.LeaveEntitlement;
import sg.nus.iss.com.Leaveapp.repository.LeaveEntitlementRepository;
import sg.nus.iss.com.Leaveapp.service.LeaveService;

@Controller
@RequestMapping("/claim")
public class ClaimController {

	
	@Autowired
	private LeaveService leaveService;
	
	@Autowired
	private LeaveEntitlementRepository leaveEntitlementRepository;

	
	@GetMapping("/make-claim")
    public String makeClaim(Model model) {
    	Claim claim = new Claim();
    	
    	model.addAttribute("claim", claim);
    	model.addAttribute("action", "make-claim");
    	return "index";
    }

	
	@PostMapping("/submitClaim")
	public String submitClaim(@ModelAttribute("claim") Claim claim, Model model, HttpSession session) {
//		if (bindingResult.hasErrors()) {
//			model.addAttribute("action", "make-claim");
//			return "index";
//		}
		claim.setEmployee((Employee)session.getAttribute("loggedInEmployee"));
		Claim submittedClaim = leaveService.saveClaim(claim);
		if (submittedClaim != null) {
			model.addAttribute("message", "Claim submitted successfully");
			model.addAttribute("action", "show-message");
		} else {
			model.addAttribute("error", "Claim submission failed");
			model.addAttribute("action", "error-message");
		}
		return "index";
	}
	
	@GetMapping("/consume")
    public String consumeClaim(Model model, HttpSession session) {
    	Leave leaveUsingClaim = new Leave();
    	Employee e = (Employee)session.getAttribute("loggedInEmployee");
    	
    	leaveUsingClaim.setEmployee(e);
    	model.addAttribute("leave", leaveUsingClaim);
    	model.addAttribute("action", "consume-claim");
    	return "index";
    }
	
	@PostMapping("/applyConsumption")
    public String applyConsumption(@Valid @ModelAttribute("leave") Leave leave, BindingResult bindingResult, Model model, HttpSession session) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("action", "consume-claim");
			return "index";
		}
		LeaveEntitlement compensationEntitlement = leaveEntitlementRepository.getCompensationEntitlement();
		Employee e = (Employee)session.getAttribute("loggedInEmployee");
		
		leave.setEmployee(e);
		leave.setEntitlement(compensationEntitlement);
    	
		Leave leaveSaved = leaveService.save(leave);
    	if(leaveSaved == null) {
			model.addAttribute("action", "error-message");
			model.addAttribute("error", "Leave submission failed.");
		} else {
			model.addAttribute("action", "show-message");
			model.addAttribute("message", "Leave saved successfully.");
		}
    	return "index";
    }
}


