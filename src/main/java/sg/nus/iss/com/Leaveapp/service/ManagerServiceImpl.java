package sg.nus.iss.com.Leaveapp.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.com.Leaveapp.model.Employee;
import sg.nus.iss.com.Leaveapp.model.Leave;
import sg.nus.iss.com.Leaveapp.model.LeaveStatus;
import sg.nus.iss.com.Leaveapp.model.LeaveType;
import sg.nus.iss.com.Leaveapp.repository.EmployeeRepository;
import sg.nus.iss.com.Leaveapp.repository.LeaveRepository;
import sg.nus.iss.com.Leaveapp.repository.LeaveStatusRepository;
import sg.nus.iss.com.Leaveapp.repository.LeaveTypeRepository;

@Service
public class ManagerServiceImpl implements ManagerService{
	

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;
    
    @Autowired
    private LeaveStatusRepository leaveStatusRepository;
    
    @Autowired
    private LeaveRepository leaveRepository;
    
    // Employee methods
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }
    
    // LeaveType methods
    public List<LeaveType> getAllLeaveTypes() {
        return leaveTypeRepository.findAll();
    }

    public LeaveType getLeaveTypeById(Long id) {
        return leaveTypeRepository.findById(id).orElse(null);
    }

    public void createOrUpdateLeaveType(LeaveType leaveType) {
        leaveTypeRepository.save(leaveType);
    }

    public void deleteLeaveType(Long id) {
        leaveTypeRepository.deleteById(id);
    }

    //approve or reject Leave
    public List<LeaveStatus> getAllLeaveStatus(){
    	return leaveStatusRepository.findAll();
    }
    
    @Override
    public List<Leave> getLeaveApplicationsForApproval() {
        return leaveRepository.findByStatusIn(Arrays.asList(LeaveStatus.Applied));
    }
    
    
    @Override
    public List<Leave> getEmployeeLeaveHistory(Employee employee) {
        return leaveRepository.findByEmployeeOrderByStartDesc(employee);
    }


    //approve and reject leave
//    @Override
//    public void approveLeaveApplication(Long leaveApplicationId, String comment) {
//        Leave leaveApplication = LeaveRepository.findById(leaveApplicationId)
//                .orElseThrow(() -> new IllegalArgumentException("Leave application not found"));
//        
//        LeaveStatus leaveStatus = new LeaveStatus();
//        leaveStatus.setLeaveApplication(leaveApplication);
//        leaveStatus.setStatus("Approved");
//        leaveStatus.setComment(comment);
//        
//        leaveStatusRepository.save(leaveStatus);
//        
//        leaveApplication.setStatus("Approved");
//        leaveApplicationRepository.save(leaveApplication);
//    }
}
