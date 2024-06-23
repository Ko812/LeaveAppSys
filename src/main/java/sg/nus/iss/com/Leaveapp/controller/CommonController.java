package sg.nus.iss.com.Leaveapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import sg.nus.iss.com.Leaveapp.model.Action;
import sg.nus.iss.com.Leaveapp.model.Employee;
import sg.nus.iss.com.Leaveapp.service.LoginService;

import java.util.*;

@Controller
public class CommonController {
	
	@Autowired
	LoginService loginService;

	@GetMapping("/")
	public String index(Model model, HttpSession session) {
		if(session.getAttribute("loggedInEmployee") == null) {
			Employee employee = new Employee();
			model.addAttribute("employee", employee);
			model.addAttribute("action", "login");
			return "login";
		} else {
			return "index";
		}
		
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute("employee") Employee employee, HttpSession session, Model model) {
		Employee employeeLogging = loginService.login(employee.getUsername(), employee.getPassword());
		if(employeeLogging != null) {
			session.setAttribute("loggedInEmployee", employeeLogging);
			return "redirect:/leave/viewleaveHistory";
		} 
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session, Model model) {
		session.invalidate();
		model.addAttribute("action", "login");
		model.addAttribute("employee", new Employee());
		return "login";
	}
}
