package sg.nus.iss.leaveapp.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LeaveApplicationService {

    @Autowired
    private LeaveApplicationRepository leaveApplicationRepository;

    public List<LeaveApplication> findByEmployeeUsername(String username) {
        return leaveApplicationRepository.findByEmployeeUsername(username);
    }

    public LeaveApplication save(LeaveApplication leaveApplication) {
        return leaveApplicationRepository.save(leaveApplication);
    }

    public void delete(Long id) {
        leaveApplicationRepository.deleteById(id);
    }

    public LeaveApplication findById(Long id) {
        return leaveApplicationRepository.findById(id).orElse(null);
    }
}
