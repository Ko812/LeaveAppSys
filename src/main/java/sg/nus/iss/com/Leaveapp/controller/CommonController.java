package sg.nus.iss.com.Leaveapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import sg.nus.iss.com.Leaveapp.model.Action;
import java.util.*;

@Controller
public class CommonController {

	@GetMapping("/")
	public String index(Model model) {
		List<Action> actions = Action.getAllActions();
		model.addAttribute("actions", actions);
		return "index";
	}
	
	@GetMapping("/leave/submit")
	public String submit(Model model) {
		List<Action> actions = Action.getAllActions();
		model.addAttribute("actions", actions);
		return "index";
	}
	
	@GetMapping("/leave/manage")
	public String manage(Model model) {
		List<Action> actions = Action.getAllActions();
		model.addAttribute("actions", actions);
		return "index";
	}
}
