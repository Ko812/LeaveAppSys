package sg.nus.iss.com.Leaveapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;



import sg.nus.iss.com.Leaveapp.model.Leave;
import sg.nus.iss.com.Leaveapp.model.LeaveStatus;
import sg.nus.iss.com.Leaveapp.model.LeaveType;
import sg.nus.iss.com.Leaveapp.service.LeaveApproveService;

@RestController
@RequestMapping("/api/leaves")
@CrossOrigin(origins = "http://localhost:3000")
public class RESTController {
	@Autowired
	private LeaveApproveService leaveApproveService;
	
	@GetMapping("/employee/{employeeId}")
	public List<Leave> getLeavesByEmployeeId(@PathVariable Long employeeId) {
        return leaveApproveService.findLeavesByEmployeeIdAndTypeAndStatusOrderByIdDesc(employeeId, LeaveType.compensation, LeaveStatus.Rejected);
    }
			
}







