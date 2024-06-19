package sg.nus.iss.com.Leaveapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.nus.iss.com.Leaveapp.model.LeaveStatus;

@Repository
public interface LeaveStatusRepository extends JpaRepository<LeaveStatus, Long> {
    List<LeaveStatus> findByLeaveId(Long leaveApplicationId);
}
