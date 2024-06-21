package sg.nus.iss.com.Leaveapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.nus.iss.com.Leaveapp.model.Claim;
import sg.nus.iss.com.Leaveapp.model.Employee;

@Controller
@RequestMapping("/claim")
public class ClaimController {

	
	
	
	@GetMapping("/make-claim")
    public String makeClaim(Model model, HttpSession session) {
    	Claim claim = new Claim();
    	claim.setEmployee((Employee)session.getAttribute("loggedInEmployee"));
    	model.addAttribute("claim", claim);
    	model.addAttribute("action", "make-claim");
    	return "index";
    }
}
//  @PostMapping("/submitClaim")
//  public String submitClaim(@ModelAttribute("claim") Claim claim, Model model) {
//  	if(bindingResult.hasErrors()) {
//  		model.addAttribute("action", "make-claim");
//  		return "index";
//  	}
//  	Claim submittedClaim = leaveService.saveClaim(claim);
//  	if(submittedClaim != null) {
//  		model.addAttribute("message", "Claim submitted successfully");
//  		model.addAttribute("action", "show-message");
//  	} else {
//  		model.addAttribute("error", "Claim submission failed");
//  		model.addAttribute("action", "error-message");
//  	}
//  	return "index";
//  }
//}
