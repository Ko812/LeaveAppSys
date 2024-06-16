package sg.nus.iss.com.Leaveapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.nus.iss.com.Leaveapp.service.LeaveApproveService;

@Controller
@RequestMapping("leaveapprove")
public class ManagerController {
	@Autowired
	private LeaveApproveService leaveApproveService;
	@GetMapping("/list")
	public String listLeaves(Model model) {
		model.addAttribute("leaves", leaveApproveService.findAllLeaves());
		
		return "leave-list";
	}

}
