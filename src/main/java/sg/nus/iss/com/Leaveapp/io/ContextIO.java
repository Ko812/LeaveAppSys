package sg.nus.iss.com.Leaveapp.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import sg.nus.iss.com.Leaveapp.model.Employee;
import sg.nus.iss.com.Leaveapp.model.Leave;
import sg.nus.iss.com.Leaveapp.model.LeaveEntitlement;
import sg.nus.iss.com.Leaveapp.model.LeaveStatus;
import sg.nus.iss.com.Leaveapp.model.LeaveType;
import sg.nus.iss.com.Leaveapp.repository.EmployeeRepository;
import sg.nus.iss.com.Leaveapp.repository.LeaveEntitlementRepository;
import sg.nus.iss.com.Leaveapp.repository.LeaveRepository;
import sg.nus.iss.com.Leaveapp.repository.LeaveTypeRepository;
import sg.nus.iss.com.Leaveapp.model.Action;


public class ContextIO {

	private String path;

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
	

	public void LoadCsv(EmployeeRepository empRepo) {
		try {
			BufferedReader br = PrepareToRead();
			String x;
			br.readLine();
			while((x = br.readLine()) != null) {
				List<String> dat = List.of(x.split(","));
				Employee e = new Employee(dat.get(1), dat.get(2), dat.get(3) + " " + dat.get(4), dat.get(5));
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
	
	public void LoadLeaveTypes(LeaveTypeRepository ltr) {
		ltr.save(LeaveType.annual);
		ltr.save(LeaveType.medical);
		ltr.save(LeaveType.compensation);
	}
	
	public void LoadLeaves(LeaveRepository lr, EmployeeRepository er) {
		try {
			BufferedReader br = PrepareToRead();
			String x;
			br.readLine();
			while((x = br.readLine()) != null) {
				List<String> dat = List.of(x.split(","));
				String username = dat.get(1);
				String[] startStringArray = dat.get(4).split("/");
				LocalDate start = LocalDate.of(Integer.parseInt(startStringArray[2]), Integer.parseInt(startStringArray[1]), Integer.parseInt(startStringArray[0]));
				String[] endStringArray = dat.get(6).split("/");
				LocalDate end = LocalDate.of(Integer.parseInt(endStringArray[2]), Integer.parseInt(endStringArray[1]), Integer.parseInt(endStringArray[0]));
				LeaveType type = LeaveType.of(dat.get(7));
				String reasons = dat.get(8);
				LeaveStatus status = LeaveStatus.valueOf(dat.get(9));
				Employee employee = er.findEmployeeByUsername(username);
				Leave el = new Leave(employee, start, end, type, reasons, status);
				lr.save(el);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void LoadEntitlements(LeaveEntitlementRepository ler, EmployeeRepository er) {
		try {
			BufferedReader br = PrepareToRead();
			String x;
			br.readLine();
			while((x = br.readLine()) != null) {
				List<String> dat = List.of(x.split(","));
				String username = dat.get(1);
				Employee employee = er.findEmployeeByUsername(username);
				Integer annualLeave = Integer.parseInt(dat.get(3));
				Integer medicalLeave = Integer.parseInt(dat.get(4));
				Integer compensation = Integer.parseInt(dat.get(5));
				LeaveEntitlement le2024 = new LeaveEntitlement(employee, annualLeave, medicalLeave, compensation, 2024);
				LeaveEntitlement le2023 = new LeaveEntitlement(employee, annualLeave, medicalLeave, compensation, 2023);
				ler.save(le2023);
				ler.save(le2024);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
