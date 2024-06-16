package sg.nus.iss.com.Leaveapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.nus.iss.com.Leaveapp.model.Leave;
import sg.nus.iss.com.Leaveapp.service.LeaveApproveService;

public class RESTController {

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {
    @Autowired
    private LeaveApproveService leaveApproveService;

    @GetMapping
    public List<Leave> findAllLeaves() {
        return leaveApproveService.findAllLeaves();
    }

    @PutMapping("/{id}/approve")
    public void approveLeave(@PathVariable Long id) {
        leaveApproveService.approveLeave(id);
    }

    @PutMapping("/{id}/reject")
    public void rejectLeave(@PathVariable Long id) {
        leaveApproveService.rejectLeave(id);
    }
}

}
