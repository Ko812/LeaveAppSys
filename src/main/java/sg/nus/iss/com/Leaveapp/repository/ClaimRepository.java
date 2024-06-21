package sg.nus.iss.com.Leaveapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.com.Leaveapp.model.Claim;
import sg.nus.iss.com.Leaveapp.model.Employee;

public interface ClaimRepository extends JpaRepository<Claim, Long>{
	
	List<Claim> findClaimsByEmployee(Employee employee);

}
