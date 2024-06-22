package sg.nus.iss.com.Leaveapp.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import sg.nus.iss.com.Leaveapp.model.Claim;
import sg.nus.iss.com.Leaveapp.model.Leave;
import sg.nus.iss.com.Leaveapp.model.LeaveStatus;
import sg.nus.iss.com.Leaveapp.model.LeaveType;
import sg.nus.iss.com.Leaveapp.repository.ClaimRepository;

import sg.nus.iss.com.Leaveapp.repository.LeaveApproveListRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class LeaveApproveServiceImpl implements LeaveApproveService {
    @Autowired
    private LeaveApproveListRepository leaveApproveListRepository;

    @Autowired
    private ClaimRepository claimRepository;
    
    
    @Override
    public List<Leave> findAllByOrderByIdDesc() {
        return leaveApproveListRepository.findAllByOrderByIdDesc();
    }
    

    @Override
    public void approveLeave(Leave leave) {
    	Leave currentLeave = leaveApproveListRepository.findById(leave.getId()).get();
    	currentLeave.setStatus(LeaveStatus.Approved);
        leaveApproveListRepository.save(currentLeave);
    }

    @Override
    public void rejectLeave(Leave leave) {
    	Leave currentLeave = leaveApproveListRepository.findById(leave.getId()).get();
    	currentLeave.setStatus(LeaveStatus.Rejected);
    	currentLeave.setComment(leave.getComment());
        leaveApproveListRepository.save(currentLeave);
    }

    
//    @Override
//    public void approveCurrentClaim(Claim claim, Employee employee) {
//    	Claim currentClaim = leaveApproveListRepository.findById(claim.getId()).get();
//		Employee employee = leaveApproveListRepository.findByEmployeeId(employee.getId()).get();
    
//  	currentClaim.setCurrentClaimTime(LocalDateTime.now());
//  
//      //Get the current claimDays and currentClaim values//
//	    double claimDays = employee.getClaimDays();
//	    double currentClaimDays = claim.getCurrentClaimDays();
//	  
//      //Calculate the updated claimDays//
//      double updatedClaimDays = claimDays - currentClaim;
//  
//  	// update employee claimDays value//
//  	employee.setClaimDays(updatedClaimDays);
//  
//  	// save in database//
// 		currentClaim.setStatus(LeaveStatus.Approved);
//  	
//      leaveApproveListRepository.save(currenClaim);
//    	leaveApproveListRepository.save(employee);
//    }



    
    @Override
    public void approveClaim(Claim claim) {
    	Claim currentClaim = claimRepository.findById(claim.getId()).get();
    	currentClaim.setStatus(LeaveStatus.Approved);
    	claimRepository.save(currentClaim);
    }

    @Override
    public void rejectClaim(Claim claim) {
    	Claim currentClaim = claimRepository.findById(claim.getId()).get();
    	currentClaim.setStatus(LeaveStatus.Approved);
    	currentClaim.setComments(claim.getComments());
    	claimRepository.save(currentClaim);
    }

   
    @Override
    public Leave getById(Long id) {
        return leaveApproveListRepository.getReferenceById(id);
    }

    @Override
    public List<Leave> findLeavesByStatusOrderByStartDesc(int status) {
    	return leaveApproveListRepository.findLeavesByStatusOrderByStartDesc(status);
    }

    @Override
    public List<Leave> findLeavesByEmployeeIdOrderByIdDesc(Long id) {
        return leaveApproveListRepository.findLeavesByEmployee_IdOrderByIdDesc(id);
    }


//	@Override
//	public List<Leave> findLeavesByEmployeeIdAndTypeAndStatusOrderByIdDesc(Long employeeId, LeaveType type, LeaveStatus status) {
//  return leaveApproveListRepository.findByEmployeeIdAndTypeAndStatusOrderByIdDesc(employeeId, type, status);
//}


	@Override
	public void reApplyLeave(Long id) {
        Leave leave = leaveApproveListRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Leave not found for id: " + id));

        leave.setStatus(LeaveStatus.Applied);
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
