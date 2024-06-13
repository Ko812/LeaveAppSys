package sg.nus.iss.com.Leaveapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sg.nus.iss.com.Leaveapp.io.ContextIO;
import sg.nus.iss.com.Leaveapp.repository.EmployeeRepository;
import sg.nus.iss.com.Leaveapp.repository.LeaveRepository;
import sg.nus.iss.com.Leaveapp.repository.LeaveTypeRepository;

@SpringBootApplication
public class LeaveapplicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeaveapplicationApplication.class, args);
	}

	@Bean
	CommandLineRunner loadContext(EmployeeRepository er, LeaveRepository lr, LeaveTypeRepository ltr) {
		return args -> {
			String path = "C:\\Users\\user\\init-kopico\\Library\\java-spring-workspace\\LeaveAppSystem";
			String employeeCsv = "employee_dummy.csv";
			ContextIO empIO = new ContextIO(path+ "\\" + employeeCsv);
			empIO.LoadCsv(er);
			empIO.AssignManagers(er);
			empIO.LoadLeaveTypes(ltr);
			String leaveCsv = "leave_dummy.csv";
			ContextIO leaveIO = new ContextIO(path + "\\" + leaveCsv);
			leaveIO.LoadLeaves(lr,er);
		};
	}
}
