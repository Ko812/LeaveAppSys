package sg.nus.iss.com.Leaveapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.com.Leaveapp.model.Leave;
import sg.nus.iss.com.Leaveapp.model.LeaveStatus;
import sg.nus.iss.com.Leaveapp.repository.LeaveApproveListRepository;

@Service
@Transactional(readOnly = true)
public class LeaveApproveServiceImpl implements LeaveApproveService{
	@Autowired
	private LeaveApproveListRepository leaveApproveListRepository;
	
	@Override
	public List<Leave> findAllLeaves(){
		return leaveApproveListRepository.findAll();
	}
	
    @Override
    public void approveLeave(Long id) {
        Leave leave = leaveApproveListRepository.findById(id).get();
                
        leave.setStatus(LeaveStatus.Approved);
        leaveApproveListRepository.save(leave);
    }
    
    @Override
    public void rejectLeave(Long id) {
        Leave leave = leaveApproveListRepository.findById(id).get();
                
        leave.setStatus(LeaveStatus.Rejected);
        leaveApproveListRepository.save(leave);
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
