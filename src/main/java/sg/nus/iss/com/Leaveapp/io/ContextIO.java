package sg.nus.iss.com.Leaveapp.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import sg.nus.iss.com.Leaveapp.model.Employee;
import sg.nus.iss.com.Leaveapp.repository.EmployeeRepository;



public class ContextIO {

private String path;
	
	private Connection con;

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
	
	public void Load(String table) {
		
		try {
			BufferedReader br = PrepareToRead();
			String x;
			while((x = br.readLine()) != null) {
				String sql = "INSERT INTO " + table + " (name) VALUE (?)";
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, x);
				pstmt.execute();
			}
			
		} catch (Exception e) {
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
				Employee e = new Employee(Long.parseLong(dat.get(0)), dat.get(1), dat.get(2), dat.get(3), dat.get(4), dat.get(5), Integer.parseInt(dat.get(6)), Integer.parseInt(dat.get(7)), Integer.parseInt(dat.get(8)), Role.fromString(dat.get(9)));
				empRepo.save(e);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	
	public void AssignManagers(EmployeeRepository empRepo) {
		try {
			BufferedReader br = PrepareToRead();
			String x;
			br.readLine();
			while((x = br.readLine()) != null) {
				List<String> dat = List.of(x.split(","));
				String username = dat.get(1);
				String managerUsername = dat.get(11);
				Employee employee = empRepo.findEmployeeByUsername(username);
				Employee manager = empRepo.findEmployeeByUsername(managerUsername);
				employee.setManager(manager);
				empRepo.save(employee);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
