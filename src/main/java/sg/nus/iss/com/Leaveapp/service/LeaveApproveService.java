package sg.nus.iss.com.Leaveapp.service;

import java.util.List;

import sg.nus.iss.com.Leaveapp.model.Leave;
import sg.nus.iss.com.Leaveapp.model.LeaveStatus;
import sg.nus.iss.com.Leaveapp.model.LeaveType;

public interface LeaveApproveService {
//	List<Leave> findAllLeaves();
	
    void approveLeave(Leave leave);
    void rejectLeave(Leave leave);

    void reApplyLeave(Long id);

    Leave getById(Long id);


    List<Leave> findLeavesByStatusOrderByStartDesc(int status);


    List<Leave> findLeavesByEmployeeIdOrderByIdDesc(Long id);
    
    List<Leave> findAllByOrderByIdDesc();
    
//    List<Leave> findLeavesByEmployeeIdAndTypeAndStatusOrderByIdDesc(Long employeeId, LeaveType type, LeaveStatus status);
	
	

//	Leave approveLeave(Leave leave);
////	Leave rejectLeave(Leave leave);

}