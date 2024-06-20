package sg.nus.iss.com.Leaveapp.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.com.Leaveapp.model.Employee;
import sg.nus.iss.com.Leaveapp.model.Leave;
import sg.nus.iss.com.Leaveapp.model.LeaveEntitlement;
import sg.nus.iss.com.Leaveapp.model.LeaveStatus;

import sg.nus.iss.com.Leaveapp.repository.EmployeeRepository;
import sg.nus.iss.com.Leaveapp.repository.LeaveEntitlementRepository;
import sg.nus.iss.com.Leaveapp.repository.LeaveRepository;



@Service
public class ManagerServiceImpl implements ManagerService{
	

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveEntitlementRepository leaveEntitlementRepository;
        
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
    public List<LeaveEntitlement> getAllLeaveEntitlements() {
    	return leaveEntitlementRepository.findAll();
    }

    public LeaveEntitlement getLeaveEntitlementById(Long id) {
    	return leaveEntitlementRepository.findById(Integer.parseInt(id.toString())).orElse(null);
    }

   

    

    //approve or reject Leave
    @Override
    public List<Leave> getLeaveApplicationsForApproval(Long managerId) {
    	List<Employee> reportees = employeeRepository.findReporteeEmployeesByManagerId(managerId);
    	List<Long> reporteesId = reportees.stream().map(e -> e.getId()).toList();
    	System.out.println(reporteesId);
        List<Leave> allAppliedLeaves = leaveRepository.findByStatusIn(Arrays.asList(LeaveStatus.Applied));
        return allAppliedLeaves
        		.stream()
        		.filter(l -> reporteesId.contains(l.getEmployee().getId()))
        		.toList();
    }
    
    
    @Override
    public List<Leave> getEmployeeLeaveHistory(Employee employee) {
        return leaveRepository.findByEmployeeOrderByStartDesc(employee);
    }

	@Override
	public Leave getLeaveApplicationById(Long id) {
		return leaveRepository.findById(id);
	}

	@Override
	public Employee findEmployeeByName(String employeeName) {
		return leaveRepository.findEmployeeName(employeeName);
	}

	@Override
	public List<Employee> findReporteeEmployeeByManagerId(Long manager_id) {
		return employeeRepository.findReporteeEmployeesByManagerId(manager_id);
	}


    //approve and reject leave
    @Override
    public Leave approveLeaveApplication(Long leaveApplicationId, String comment) {
        Leave leaveApplication = leaveRepository.findById(leaveApplicationId);
        if(leaveApplication == null) {
        	return null;
        }
        leaveApplication.setStatus(LeaveStatus.Approved);
        return leaveRepository.save(leaveApplication);
    }
    
    @Override
    public Leave rejectLeaveApplication(Long leaveApplicationId, String comment) {
        Leave leaveApplication = leaveRepository.findById(leaveApplicationId);
        if(leaveApplication == null) {
        	return null;
        }
        leaveApplication.setStatus(LeaveStatus.Rejected);
        return leaveRepository.save(leaveApplication);
    }
}
