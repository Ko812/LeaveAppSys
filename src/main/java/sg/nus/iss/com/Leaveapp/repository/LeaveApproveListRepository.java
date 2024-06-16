package sg.nus.iss.com.Leaveapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.com.Leaveapp.model.Leave;


public interface LeaveApproveListRepository extends JpaRepository<Leave, Long> {
	
	Optional<Leave> findById(Long id);

}
