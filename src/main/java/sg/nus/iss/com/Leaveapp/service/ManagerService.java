package sg.nus.iss.com.Leaveapp.service;

import java.util.List;

import sg.nus.iss.com.Leaveapp.model.Employee;
import sg.nus.iss.com.Leaveapp.model.Leave;

public interface ManagerService {
    // Employee methods

    Employee getEmployeeById(Long id);
	Employee findEmployeeByName(String employeeName);
	
    List<Employee> getAllEmployees();
	List<Employee> findReporteeEmployeeByManagerId(Long manager_id);
	List<Employee> findEmployeesByName(String name);
	
    // Leave methods
	Leave getLeaveApplicationById(Long id);
    List<Leave> getLeaveApplicationsForApproval(Long managerId);
    List<Leave> getEmployeeLeaveHistory(Employee employee);
	
    
    //approve or reject Leave
//    void approveLeaveApplication(Long leavaApplicationId, String comment);
//    void rejectLeaveApplication(Long leavaApplicationId, String comment);

}
