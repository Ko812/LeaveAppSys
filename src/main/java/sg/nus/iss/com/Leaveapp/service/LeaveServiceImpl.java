package sg.nus.iss.com.Leaveapp.service;

import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import sg.nus.iss.com.Leaveapp.model.Employee;
import sg.nus.iss.com.Leaveapp.model.Leave;
import sg.nus.iss.com.Leaveapp.model.LeaveStatus;
import sg.nus.iss.com.Leaveapp.model.LeaveType;
import sg.nus.iss.com.Leaveapp.repository.LeaveRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    @Override
    public Employee findEmployee(Long id) {
        return leaveRepository.findEmployeeById(id);
    }

    @Override
    public LocalDate findStartDate(Long id) {
        return leaveRepository.findStartDateById(id);
    }

    @Override
    public LocalDate findEndDate(Long id) {
        return leaveRepository.findEndDateById(id);
    }

    @Override
    public List<Employee> findEmployeesBetweenStartAndEndDate(LocalDate startDate, LocalDate endDate) {
        return leaveRepository.findEmployeesBetweenStartAndEndDate(startDate, endDate);
    }

    @Override
    public String findReason(Long id) {
        return leaveRepository.findReasonById(id);
    }

    @Override
    public LeaveStatus findLeaveStatus(Long id) {
        return leaveRepository.findLeaveStatusById(id);
    }

    @Override
    public LeaveType findLeaveType(Long id) {
        return leaveRepository.findLeaveTypeById(id);
    }

    @Override
    public Long findIdByEmpId(Long empId) {
        return leaveRepository.findLeaveAppIdByEmpId(empId);
    }

    @Override
    public String findLeaveReasonsByEmpId(Long empId) {
        return leaveRepository.findLeaveAppReasonsByEmpId(empId);
    }

    @Override
    public LocalDate findLeaveAppStartDateByEmpId(Long empId) {
        return leaveRepository.findLeaveAppStartDateByEmpId(empId);
    }

    @Override
    public LocalDate findLeaveAppEndDateByEmpId(Long empId) {
        return leaveRepository.findLeaveAppEndDateByEmpId(empId);
    }

    @Override
    public LeaveStatus findLeaveappStatusByEmpId(Long empId) {
        return leaveRepository.findLeaveappStatusByEmpId(empId);
    }

    @Override
    public String findEmpNameByLeaveId(Long leaveId) {
        return leaveRepository.findEmpNameByLeaveId(leaveId);
    }

    @Override
    public String findEmpRoleByLeaveId(Long leaveId) {
        return leaveRepository.findEmpRoleByLeaveId(leaveId);
    }

    @Override
    public void save(Leave leave) {
        leaveRepository.save(leave);
    }

    @Override
    public List<Leave> findLeavesFromEmployeeId(Long id) {
        return leaveRepository.findLeavesFromEmployeeId(id);
    }

    @Override
    public void applyPay(Integer id) {
        Leave leave = leaveRepository.getReferenceById(id);
        leave.setClaimStatus(LeaveStatus.Applied.name());
        leaveRepository.save(leave);
    }

    @Override
    public void approvePay(Integer id) {
        Leave leave = leaveRepository.getReferenceById(id);
        leave.setClaimStatus(LeaveStatus.Approved.name());
        leaveRepository.save(leave);
    }

    @Override
    public void cancelPay(Integer id) {
        Leave leave = leaveRepository.getReferenceById(id);
        leave.setClaimStatus(LeaveStatus.Cancelled.name());
        leaveRepository.save(leave);
    }

    @Override
    public List<Leave> getList(Leave leave) {
        return leaveRepository.findAll(getSpecification(leave));
    }

    private Specification<Leave> getSpecification(Leave leave) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (leave.getId() != null) {
                predicates.add(cb.equal(root.get("id"), leave.getId()));
            }
            if (leave.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), leave.getStatus()));
            }
            if (leave.getClaimStatus() != null) {
                predicates.add(cb.equal(root.get("claimStatus"), leave.getClaimStatus()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
