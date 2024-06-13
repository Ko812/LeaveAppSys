package sg.nus.iss.com.Leaveapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.com.Leaveapp.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
