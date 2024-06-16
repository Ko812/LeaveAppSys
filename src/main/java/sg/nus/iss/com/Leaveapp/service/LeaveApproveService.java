package sg.nus.iss.com.Leaveapp.service;

import java.util.List;

import sg.nus.iss.com.Leaveapp.model.Leave;

public interface LeaveApproveService {
	List<Leave> findAllLeaves();
	
    void approveLeave(Long id);
    void rejectLeave(Long id);
	
//	Leave approveLeave(Leave leave);
////	Leave rejectLeave(Leave leave);

}
