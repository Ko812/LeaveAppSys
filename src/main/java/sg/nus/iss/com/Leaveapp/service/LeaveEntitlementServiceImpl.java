package sg.nus.iss.com.Leaveapp.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.com.Leaveapp.model.*;
import sg.nus.iss.com.Leaveapp.repository.LeaveEntitlementRepository;

@Service
public class LeaveEntitlementServiceImpl implements LeaveEntitlementService {

    @Autowired
    private LeaveEntitlementRepository leaveEntitlementRepository;

    @Override
    public LeaveEntitlement createLeaveEntitlement(LeaveEntitlement leaveEntitlement) {
        return leaveEntitlementRepository.save(leaveEntitlement);
    }

    @Override
    public List<LeaveEntitlement> findAllLeaveEntitlements() {
        return leaveEntitlementRepository.findAll();
    }

    @Override
    public List<LeaveEntitlement> getLeaveEntitlementByEmployeeID(int employeeId) {
        return leaveEntitlementRepository.findByEmployeeId(employeeId);
    }

    @Override
    public LeaveEntitlement updateLeaveEntitlement(int id, LeaveEntitlement updatedEntitlement) {
        LeaveEntitlement entitlement = leaveEntitlementRepository.findById(id);

        entitlement.setLeaveType(updatedEntitlement.getLeaveType());
        entitlement.setNumberOfDays(updatedEntitlement.getNumberOfDays());
        entitlement.setBroughtForward(updatedEntitlement.getBroughtForward());
        entitlement.setTotalDays(updatedEntitlement.getTotalDays());
        entitlement.setYear(updatedEntitlement.getYear());
        entitlement.setUsedDays(updatedEntitlement.getUsedDays());
        entitlement.setBalance(updatedEntitlement.getBalance());

        return leaveEntitlementRepository.save(entitlement);
    }

    @Override
    public void deleteLeaveEntitlement(int id) {
        leaveEntitlementRepository.deleteById(id);
    }

   

    @Override
    public void saveLeaveEntitlement(LeaveEntitlement leaveEntitlement) {
        leaveEntitlementRepository.save(leaveEntitlement);
    }

    @Override
    public boolean isValidLeavePeriod(LocalDate startDate, LocalDate endDate) {
        // Dates From and To should be in chronologically increasing order
        if (startDate.isAfter(endDate)) {
            return false;
        }

        // From and To dates must be working days
        if (startDate.getDayOfWeek() == DayOfWeek.SATURDAY || startDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return false;
        }

        if (endDate.getDayOfWeek() == DayOfWeek.SATURDAY || endDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return false;
        }

        // Additional validation logic for leave period computation can be added here

        return true;
    }

    @Override
    public boolean isWithinAnnualLeaveLimit(int employeeId, LocalDate startDate, LocalDate endDate) {
        // Additional logic to check if the leave period exceeds the entitled annual leave based on the employee's designation
        // This logic will depend on your business rules and how you calculate annual leave entitlement
        return true;
    }

    @Override
    public boolean isWithinMedicalLeaveLimit(int employeeId, LocalDate startDate, LocalDate endDate) {
        // Additional logic to check if the medical leave is limited to 60 days in a calendar year
        // This logic will depend on your business rules for medical leave
        return true;
    }
}
