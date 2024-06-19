package sg.nus.iss.com.Leaveapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import sg.nus.iss.com.Leaveapp.model.Action;
import sg.nus.iss.com.Leaveapp.model.Employee;

import java.util.*;

@Controller
public class CommonController {

	@GetMapping("/")
	public String index(Model model) {
		Employee employee = new Employee();
		
		return "index";
	}
	
	
}
