package sg.nus.iss.com.Leaveapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sg.nus.iss.com.Leaveapp.io.ContextIO;
import sg.nus.iss.com.Leaveapp.repository.EmployeeRepository;

@SpringBootApplication
public class LeaveapplicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeaveapplicationApplication.class, args);
	}

	@Bean
	CommandLineRunner loadContext(EmployeeRepository er) {
		return args -> {
			String path = "C:\\Users\\user\\init-kopico\\Library\\java-spring-workspace\\LeaveAppSystem";
			String csv = "employee_dummy.csv";
			ContextIO conIO = new ContextIO(path+ "\\" + csv);
			conIO.LoadCsv(er);
			conIO.AssignManagers(er);
		};
	}
}
