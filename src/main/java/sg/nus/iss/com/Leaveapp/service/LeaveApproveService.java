package sg.nus.iss.com.Leaveapp.service;

import java.util.List;

import sg.nus.iss.com.Leaveapp.model.Leave;
import sg.nus.iss.com.Leaveapp.model.LeaveStatus;

public interface LeaveApproveService {
	List<Leave> findAllLeaves();
	
    void approveLeave(Long id);
    void rejectLeave(Long id);

    void rejectLeave(Long id, String comment);

    Leave getById(Long id);

    List<Leave> findLeavesByStatus(LeaveStatus status);

    List<Leave> findLeavesByEmployeeId(Long id);

//	Leave approveLeave(Leave leave);
////	Leave rejectLeave(Leave leave);

}
