package sg.nus.iss.com.Leaveapp.service;


import sg.nus.iss.com.Leaveapp.model.Employee;

public interface EmployeeService {
	
	public Employee findEmployeeByUsername(String username);
	
	public Employee findEmployeeRoleById(Long id);
	
	public Employee findEmployeeById(Long id);

}
