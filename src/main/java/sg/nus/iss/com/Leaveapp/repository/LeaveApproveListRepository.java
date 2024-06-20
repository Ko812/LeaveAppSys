package sg.nus.iss.com.Leaveapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.nus.iss.com.Leaveapp.model.Leave;
import sg.nus.iss.com.Leaveapp.model.LeaveStatus;

import java.util.List;
import java.util.Optional;


public interface LeaveApproveListRepository extends JpaRepository<Leave, Long> {

    Optional<Leave> findById(Long id);

    List<Leave> findLeavesByStatusOrderByStartDesc(LeaveStatus status);

    List<Leave> findLeavesByEmployee_IdOrderByStartDesc(Long id);
    
    List<Leave> findAllByOrderByStartDesc();
}