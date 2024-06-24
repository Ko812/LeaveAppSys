package sg.nus.iss.com.Leaveapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;



import sg.nus.iss.com.Leaveapp.model.Leave;
import sg.nus.iss.com.Leaveapp.model.LeaveEntitlement;
import sg.nus.iss.com.Leaveapp.model.LeaveStatus;
import sg.nus.iss.com.Leaveapp.model.LeaveType;
import sg.nus.iss.com.Leaveapp.service.LeaveApproveService;
import sg.nus.iss.com.Leaveapp.service.LeaveEntitlementService;

@RestController
@RequestMapping("/api/leaves")
@CrossOrigin(origins = "http://localhost:3000")
public class RESTController {
	@Autowired
	private LeaveApproveService leaveApproveService;
	
	@Autowired
	private LeaveEntitlementService leaveEntitlementService;
	
	@GetMapping("/employee/{employeeId}")
	public List<Leave> getLeavesByEmployeeId(@PathVariable Long employeeId) {
		LeaveEntitlement compensationEntitlement = leaveEntitlementService.getCompensationEntitlement();
        return leaveApproveService.findLeavesByEmployeeIdAndEntitlementAndStatusOrderByIdDesc(employeeId, compensationEntitlement, LeaveStatus.Rejected);
    }
	
	@PutMapping("/{id}/reapply")
	public ResponseEntity<Void> reapply(@PathVariable Long id){
		leaveApproveService.reApplyLeave(id);
		return ResponseEntity.ok().build();
	}
		
		
}
	
			







