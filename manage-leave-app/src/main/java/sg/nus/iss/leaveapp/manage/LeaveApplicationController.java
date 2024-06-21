package sg.nus.iss.leaveapp.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class LeaveApplicationController {

    @Autowired
    private LeaveApplicationService leaveApplicationService;

    @GetMapping("/manage-leave")
    public String manageLeave(Model model, Principal principal) {
        List<LeaveApplication> leaveApplications = leaveApplicationService.findByEmployeeUsername(principal.getName());
        model.addAttribute("leaveApplications", leaveApplications);
        return "manage-leave";
    }

    @PostMapping("/update-leave")
    public String updateLeave(@RequestParam Long id, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        LeaveApplication leaveApplication = leaveApplicationService.findById(id);
        if (leaveApplication != null && "Pending".equals(leaveApplication.getStatus())) {
            leaveApplication.setStartDate(startDate);
            leaveApplication.setEndDate(endDate);
            leaveApplication.setStatus("Updated");
            leaveApplicationService.save(leaveApplication);
        }
        return "redirect:/manage-leave";
    }

    @PostMapping("/delete-leave")
    public String deleteLeave(@RequestParam Long id) {
        LeaveApplication leaveApplication = leaveApplicationService.findById(id);
        if (leaveApplication != null && "Pending".equals(leaveApplication.getStatus())) {
            leaveApplication.setStatus("Deleted");
            leaveApplicationService.save(leaveApplication);
        }
        return "redirect:/manage-leave";
    }

    @PostMapping("/cancel-leave")
    public String cancelLeave(@RequestParam Long id) {
        LeaveApplication leaveApplication = leaveApplicationService.findById(id);
        if (leaveApplication != null && "Approved".equals(leaveApplication.getStatus())) {
            leaveApplication.setStatus("Cancelled");
            leaveApplicationService.save(leaveApplication);
        }
        return "redirect:/manage-leave";
    }
}
