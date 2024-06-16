
package sg.nus.iss.com.Leaveapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.nus.iss.com.Leaveapp.model.LeaveEntitlement;


@Repository
public interface LeaveEntitlementRepository extends JpaRepository<LeaveEntitlement, Integer> {
	List<LeaveEntitlement> findByEmployeeId(int employeeId);
	Optional<LeaveEntitlement> findById(int id);
	void deleteById(int id);
}
