
package sg.nus.iss.com.Leaveapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sg.nus.iss.com.Leaveapp.model.LeaveEntitlement;
import sg.nus.iss.com.Leaveapp.model.Role;


@Repository
public interface LeaveEntitlementRepository extends JpaRepository<LeaveEntitlement, Integer> {
	List<LeaveEntitlement> findByEmployeeId(int employeeId);
	Optional<LeaveEntitlement> findById(int id);
	void deleteById(int id);
	
	@Query("SELECT e FROM LeaveEntitlement e WHERE e.leaveType = :type AND e.role_id =:role_id")
	public LeaveEntitlement findLeaveEntitlementByType(@Param("type") String type, @Param("role_id") Integer role_id);
}

