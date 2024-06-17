package sg.nus.iss.com.Leaveapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sg.nus.iss.com.Leaveapp.model.Leave;
import sg.nus.iss.com.Leaveapp.model.LeaveStatus;
import sg.nus.iss.com.Leaveapp.service.LeaveApproveService;

@Controller
@RequestMapping("leaveapprove")
public class ManagerController {
    @Autowired
    private LeaveApproveService leaveApproveService;

    //    @GetMapping("/list")
//    public String listLeaves(Model model) {
//        model.addAttribute("leaves", leaveApproveService.findAllLeaves());
//
//        return "leave-list";
//    }
    @GetMapping("/list")
    public String listLeaves(@RequestParam(value = "status", required = false) String status, Model model) {
        if (status != null && !status.isEmpty()) {
            LeaveStatus value = LeaveStatus.valueOf(status);
            model.addAttribute("leaves", leaveApproveService.findLeavesByStatus(value));
        } else {
            model.addAttribute("leaves", leaveApproveService.findAllLeaves());
        }
        return "leave-list";
    }

    @GetMapping("/leave-applications")
    public String showLeaveApplications() {
        return "leave-applications";
    }

    @GetMapping("/detail")
    public String getDetail(@RequestParam Long id, Model model) {
        Leave leave = leaveApproveService.getById(id);
        if (leave != null) {
            model.addAttribute("leaves", leaveApproveService.findLeavesByEmployeeId(leave.getEmployee().getId()));
            model.addAttribute("leave", leave);
            return "leave-detail";
        } else {
            model.addAttribute("leaves", leaveApproveService.findAllLeaves());
            return "leave-list";
        }
    }

    @PostMapping("/approve")
    public String approveLeave(@RequestParam Long id) {
        // Approve
        leaveApproveService.approveLeave(id);
        return "redirect:/leaveapprove/list";
    }

    @PostMapping("/reject")
    public String rejectLeave(@RequestParam Long id, @RequestParam String comment) {
        // reject and comment
        leaveApproveService.rejectLeave(id, comment);
        return "redirect:/leaveapprove/list";
    }
}