package sg.nus.iss.com.Leaveapp.service;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.com.Leaveapp.model.Employee;
import sg.nus.iss.com.Leaveapp.model.LeaveEntitlement;

import sg.nus.iss.com.Leaveapp.model.Role;
import sg.nus.iss.com.Leaveapp.repository.EmployeeRepository;
import sg.nus.iss.com.Leaveapp.repository.LeaveEntitlementRepository;

import sg.nus.iss.com.Leaveapp.repository.RoleRepository;

@Service
public class AdminService {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private LeaveEntitlementRepository leaveEntitlementRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    // Employee methods
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public void createOrUpdateEmployee(Employee employee) {
    	Role role = roleRepository.findRoleByName(employee.getRole().getName());
    	employee.setRole(role);
        employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
    
    public List<Employee> getManagers() {
        Role managerRole = roleRepository.findRoleByName("manager");
        return employeeRepository.findByRole(managerRole);
    }
    
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
    
    
    
    

 // LeaveType methods
//  public List<LeaveType> getAllLeaveTypes() {
//      return leaveTypeRepository.findAll();
//  }
//
//  public LeaveType getLeaveTypeById(Long id) {
//      return leaveTypeRepository.findById(id).orElse(null);
//  }

	public void createOrUpdateLeaveType(String type, Map<String, Integer> entitlements, int year) {

		entitlements.keySet().forEach(roleName -> {
			Role role = roleRepository.findRoleByName(roleName);
			leaveEntitlementRepository.save(new LeaveEntitlement(type, entitlements.get(roleName), role, year));
		});
    	
//    	LeaveEntitlement staffLeaveEntitlement = new LeaveEntitlement(StaffRole, leaveType, entitlement)
//    	leaveEntitlementRepository.s
//        leaveTypeRepository.save(leaveType);
    }

//    public void deleteLeaveType(Long id) {
//  leaveTypeRepository.deleteById(id);
//}
}
