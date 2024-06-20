package sg.nus.iss.com.Leaveapp.service;

import java.util.List;

import sg.nus.iss.com.Leaveapp.model.Leave;
import sg.nus.iss.com.Leaveapp.model.LeaveStatus;
import sg.nus.iss.com.Leaveapp.model.LeaveType;

public interface LeaveApproveService {
//	List<Leave> findAllLeaves();
	
    void approveLeave(Long id);
    void rejectLeave(Long id);

    void rejectLeave(Long id, String comment);

    Leave getById(Long id);

    List<Leave> findLeavesByStatusOrderByIdDesc(LeaveStatus status);

    List<Leave> findLeavesByEmployeeIdOrderByIdDesc(Long id);
    
    List<Leave> findAllByOrderByIdDesc();
    
    List<Leave> findLeavesByEmployeeIdAndTypeAndStatusOrderByIdDesc(Long employeeId, LeaveType type, LeaveStatus status);
	
	

//	Leave approveLeave(Leave leave);
////	Leave rejectLeave(Leave leave);

}