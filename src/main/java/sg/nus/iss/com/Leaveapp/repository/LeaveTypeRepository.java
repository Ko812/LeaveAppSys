package sg.nus.iss.com.Leaveapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.com.Leaveapp.model.LeaveType;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {

}
