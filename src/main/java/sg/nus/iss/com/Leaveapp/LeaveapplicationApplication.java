package sg.nus.iss.com.Leaveapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sg.nus.iss.com.Leaveapp.io.ContextIO;
import sg.nus.iss.com.Leaveapp.repository.EmployeeRepository;
import sg.nus.iss.com.Leaveapp.repository.LeaveEntitlementRepository;
import sg.nus.iss.com.Leaveapp.repository.LeaveRepository;


@SpringBootApplication
public class LeaveapplicationApplication {

	@Value("${spring.jpa.hibernate.ddl-auto}") // Injecting the value of app.greeting from application.properties
    private String ddlauto;
	
	public static void main(String[] args) {
		SpringApplication.run(LeaveapplicationApplication.class, args);
	}

	@Bean
	CommandLineRunner loadContext(EmployeeRepository er, LeaveRepository lr, LeaveEntitlementRepository ler) {
		return args -> {

			if(false) {
//			if(ddlauto.compareTo("create") == 0) {
				String path = "C:\\Users\\user\\init-kopico\\Library\\java-spring-workspace\\LeaveAppSystem";
				String employeeCsv = "employee_dummy.csv";
				ContextIO empIO = new ContextIO(path+ "\\" + employeeCsv);
				empIO.LoadCsv(er);
				empIO.AssignManagers(er);
//				empIO.LoadLeaveTypes(ltr);
				String leaveCsv = "leave_dummy.csv";
				ContextIO leaveIO = new ContextIO(path + "\\" + leaveCsv);
				leaveIO.LoadLeaves(lr,er);
				String entitlementCsv = "leave_entitlement_dummy.csv";
				ContextIO entitlementIO = new ContextIO(path + "\\" + entitlementCsv);
				entitlementIO.LoadEntitlements(ler, er);
				return ;
			}
			System.out.println("Skipped context load. ddl-auto: " + ddlauto);
		};
	}
}
