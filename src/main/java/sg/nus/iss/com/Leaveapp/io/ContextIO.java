package sg.nus.iss.com.Leaveapp.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import sg.nus.iss.com.Leaveapp.model.Employee;
import sg.nus.iss.com.Leaveapp.model.Leave;
import sg.nus.iss.com.Leaveapp.model.LeaveEntitlement;
import sg.nus.iss.com.Leaveapp.model.LeaveStatus;
import sg.nus.iss.com.Leaveapp.model.Role;
import sg.nus.iss.com.Leaveapp.repository.EmployeeRepository;
import sg.nus.iss.com.Leaveapp.repository.LeaveEntitlementRepository;
import sg.nus.iss.com.Leaveapp.repository.LeaveRepository;
import sg.nus.iss.com.Leaveapp.repository.RoleRepository;
import sg.nus.iss.com.Leaveapp.Exceptions.TypeNotFoundException;



public class ContextIO {

	private String path;
	
	private Scanner scan = new Scanner(System.in);

	public ContextIO(String path) {
		super();
		this.path = path;
	}
	
	private BufferedReader PrepareToRead() {
		try {
			FileReader fr = new FileReader(path);
			return new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public void ReadHead() {
		BufferedReader br = PrepareToRead();
		try {
			System.out.println(br.readLine());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	

	public void LoadCsv(EmployeeRepository empRepo, RoleRepository rr) {
		try {
			BufferedReader br = PrepareToRead();
			String x;
			br.readLine();
			Role employeeRole = rr.findRoleByName("employee");
			Role adminRole = rr.findRoleByName("admin");
			Role managerRole = rr.findRoleByName("manager");
			while((x = br.readLine()) != null) {
				List<String> dat = List.of(x.split(","));
				String roleString = dat.get(9);
				Role role;
				if(roleString.compareTo("Staff") == 0) {
					role = employeeRole;
				} else if (roleString.compareTo("Admin") == 0) {
					role = adminRole;
				} else if (roleString.compareTo("Manager") == 0) {
					role = managerRole;
				} else {
					throw new TypeNotFoundException(roleString + " role not found");
				}
				Employee e = new Employee(dat.get(1), dat.get(2), dat.get(3) + " " + dat.get(4), role);
				empRepo.save(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void AssignManagers(EmployeeRepository er) {
		try {
			BufferedReader br = PrepareToRead();
			String x;
			br.readLine();
			while((x = br.readLine()) != null) {
				List<String> dat = List.of(x.split(","));
				String username = dat.get(1);
				String managerUsername = dat.get(11);
				Employee employee = er.findEmployeeByUsername(username);
				Employee manager = er.findEmployeeByUsername(managerUsername);
				employee.setManager(manager);
				er.save(employee);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void LoadRoles(RoleRepository rr) {
		rr.save(Role.employeeRole);
		rr.save(Role.adminRole);
		rr.save(Role.managerRole);
	}
	
	public void LoadLeaveEntitlement(LeaveEntitlementRepository ler) {	
		LeaveEntitlement employeeAnnualLeaveEntitlement = new LeaveEntitlement("annual", 14, Role.employeeRole, 2024);
		LeaveEntitlement employeeSickLeaveEntitlement = new LeaveEntitlement("medical", 6, Role.employeeRole, 2024);
		LeaveEntitlement adminAnnualLeaveEntitlement = new LeaveEntitlement("annual", 15, Role.adminRole, 2024);
		LeaveEntitlement adminSickLeaveEntitlement = new LeaveEntitlement("medical", 7, Role.adminRole, 2024);
		LeaveEntitlement managerAnnualLeaveEntitlement = new LeaveEntitlement("annual", 16, Role.managerRole, 2024);
		LeaveEntitlement managerSickLeaveEntitlement = new LeaveEntitlement("medical", 8, Role.managerRole, 2024);
		ler.save(employeeAnnualLeaveEntitlement);
		ler.save(employeeSickLeaveEntitlement);
		ler.save(adminAnnualLeaveEntitlement);
		ler.save(adminSickLeaveEntitlement);
		ler.save(managerAnnualLeaveEntitlement);
		ler.save(managerSickLeaveEntitlement);
	}
	
	public void LoadLeaves(LeaveRepository lr, EmployeeRepository er, LeaveEntitlementRepository ler, RoleRepository rr) {
		try {
			BufferedReader br = PrepareToRead();
			String x;
			br.readLine();
			Role employee = rr.findRoleByName("employee");
			Role admin = rr.findRoleByName("admin");
			Role manager = rr.findRoleByName("manager");
//			System.out.println("Employee role id: " + employee.getId() +". Admin role id: " + admin.getId() + ". Manager role id: " + manager.getId());
			LeaveEntitlement employeeAnnualLeaveEntitlement = ler.findLeaveEntitlementByType("annual", employee.getId());
			LeaveEntitlement adminAnnualLeaveEntitlement = ler.findLeaveEntitlementByType("annual", admin.getId());
			LeaveEntitlement managerAnnualLeaveEntitlement = ler.findLeaveEntitlementByType("annual", manager.getId());
			while((x = br.readLine()) != null) {
				List<String> dat = List.of(x.split(","));
				String username = dat.get(1);
				String[] startStringArray = dat.get(4).split("/");
				LocalDate start = LocalDate.of(Integer.parseInt(startStringArray[2]), Integer.parseInt(startStringArray[1]), Integer.parseInt(startStringArray[0]));
				String[] endStringArray = dat.get(6).split("/");
				LocalDate end = LocalDate.of(Integer.parseInt(endStringArray[2]), Integer.parseInt(endStringArray[1]), Integer.parseInt(endStringArray[0]));
				String reasons = dat.get(8);
				String status = dat.get(9);
				Employee e = er.findEmployeeByUsername(username);
				LeaveEntitlement employeeEntitlement;
				if(e.getRole().getName().compareTo("employee") == 0) {
					employeeEntitlement = employeeAnnualLeaveEntitlement;
				} else if(e.getRole().getName().compareTo("admin") == 0){
					employeeEntitlement = adminAnnualLeaveEntitlement;
				} else if(e.getRole().getName().compareTo("manager") == 0){
					employeeEntitlement = managerAnnualLeaveEntitlement;
				} else {
					throw new TypeNotFoundException();
				}
				Leave el = new Leave(e, start, end, employeeEntitlement, reasons, status);
				el.setOverseas(Integer.parseInt(dat.get(10)) == 1);
				el.setNameOfSupportingCoworker(dat.get(11));
				if(dat.size() >=13) {
					el.setOverseasContact(dat.get(12));
				} else {
					el.setOverseasContact("");
				}
				
				lr.save(el);
			}
			System.out.println("Leaves loaded.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
