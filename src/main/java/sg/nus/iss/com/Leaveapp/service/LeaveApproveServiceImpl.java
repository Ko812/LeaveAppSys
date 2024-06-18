package sg.nus.iss.com.Leaveapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.nus.iss.com.Leaveapp.model.Leave;
import sg.nus.iss.com.Leaveapp.model.LeaveStatus;
import sg.nus.iss.com.Leaveapp.repository.LeaveApproveListRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class LeaveApproveServiceImpl implements LeaveApproveService {
    @Autowired
    private LeaveApproveListRepository leaveApproveListRepository;

    @Override
    public List<Leave> findAllLeaves() {
        return leaveApproveListRepository.findAll();
    }

    @Override
    public void approveLeave(Long id) {
        Leave leave = leaveApproveListRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Leave not found for id: " + id));

        leave.setStatus(LeaveStatus.Approved);
        leaveApproveListRepository.save(leave);
    }

    @Override
    public void rejectLeave(Long id) {
        Leave leave = leaveApproveListRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Leave not found for id: " + id));

        leave.setStatus(LeaveStatus.Rejected);
        leaveApproveListRepository.save(leave);
    }

    @Override
    public void rejectLeave(Long id, String comment) {
        Leave leave = leaveApproveListRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Leave not found for id: " + id));
        leave.setStatus(LeaveStatus.Rejected);
        leave.setComment(comment);
        leaveApproveListRepository.save(leave);
    }

    @Override
    public Leave getById(Long id) {
        return leaveApproveListRepository.getReferenceById(id);
    }

    @Override
    public List<Leave> findLeavesByStatus(LeaveStatus status) {
        return leaveApproveListRepository.findAllByStatus(status);
    }

    @Override
    public List<Leave> findLeavesByEmployeeId(Long id) {
        return leaveApproveListRepository.findAllByEmployee_Id(id);
    }

//@Transactional(readOnly = false)
//@Override
//public Leave approveLeave(Leave leave) {
//	return leaveApproveListRepository.save(leave);
//}
//
////@Transactional(readOnly = false)
////@Override
////public Leave rejectLeave(Leave leave) {
////	return leaveApproveListRepository.save(leave);
////}


}
