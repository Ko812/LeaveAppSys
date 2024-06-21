package sg.nus.iss.com.Leaveapp.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.com.Leaveapp.model.Claim;
import sg.nus.iss.com.Leaveapp.model.Employee;
import sg.nus.iss.com.Leaveapp.model.Leave;
import sg.nus.iss.com.Leaveapp.model.LeaveEntitlement;
import sg.nus.iss.com.Leaveapp.model.LeaveStatus;
import sg.nus.iss.com.Leaveapp.model.LeaveType;
import sg.nus.iss.com.Leaveapp.repository.ClaimRepository;
import sg.nus.iss.com.Leaveapp.repository.LeaveRepository;

@Service
public class LeaveServiceImpl implements LeaveService {

	@Autowired
	private ClaimRepository claimRepository;
	
    @Autowired
    private LeaveRepository leaveRepository;

    @Override
    public Employee findEmployee(Long id) {
        return leaveRepository.findEmployeeById(id);
    }

    @Override
    public LocalDate findStartDate(Long id) {
        return leaveRepository.findStartDateById(id);
    }

    @Override
    public LocalDate findEndDate(Long id) {
        return leaveRepository.findEndDateById(id);
    }

    @Override
    public List<Employee> findEmployeesBetweenStartAndEndDate(LocalDate startDate, LocalDate endDate) {
        return leaveRepository.findEmployeesBetweenStartAndEndDate(startDate, endDate);
    }

    @Override
    public String findReason(Long id) {
        return leaveRepository.findReasonById(id);
    }

    @Override
    public LeaveStatus findLeaveStatus(Long id) {
        return leaveRepository.findLeaveStatusById(id);
    }
    
    @Override
    public Leave findById(Long id) {
        return leaveRepository.findById(id);
    }

    @Override
    public LeaveEntitlement findLeaveEntitlement(Long id) {
        return leaveRepository.findLeaveEntitlementById(id);
    }

    @Override
    public Long findIdByEmpId(Long empId) {
        return leaveRepository.findLeaveAppIdByEmpId(empId);
    }

    @Override
    public String findLeaveReasonsByEmpId(Long empId) {
        return leaveRepository.findLeaveAppReasonsByEmpId(empId);
    }

    @Override
    public LocalDate findLeaveAppStartDateByEmpId(Long empId) {
        return leaveRepository.findLeaveAppStartDateByEmpId(empId);
    }

    @Override
    public LocalDate findLeaveAppEndDateByEmpId(Long empId) {
        return leaveRepository.findLeaveAppEndDateByEmpId(empId);
    }

    @Override
    public LeaveStatus findLeaveappStatusByEmpId(Long empId) {
        return leaveRepository.findLeaveappStatusByEmpId(empId);
    }

    @Override
    public String findEmpNameByLeaveId(Long leaveId) {
        return leaveRepository.findEmpNameByLeaveId(leaveId);
    }

    @Override
    public String findEmpRoleByLeaveId(Long leaveId) {
        return leaveRepository.findEmpRoleByLeaveId(leaveId);
    }
    
    @Override

    public Leave save(Leave leave)
    {
    	if(leave.getStatus() == 0) {
    		leave.setStatus(LeaveStatus.Applied);
    	}
    	return leaveRepository.save(leave);
    }
    
    @Override
    public List<Leave> findLeavesFromEmployeeId(Long id) {
    	return leaveRepository.findLeavesFromEmployeeId(id);
    }
    
    @Override
    public Claim saveClaim(Claim claim) {
    	
    	return claimRepository.save(claim);
    }

	@Override
	public List<Claim> findClaimsByEmployee(Employee employee) {
		return claimRepository.findClaimsByEmployee(employee);
	}
}
