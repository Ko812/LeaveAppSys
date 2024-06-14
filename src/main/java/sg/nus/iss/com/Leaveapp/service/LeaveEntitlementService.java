
package sg.nus.iss.com.Leaveapp.service;

import java.time.LocalDate;
import java.util.List;

import sg.nus.iss.com.Leaveapp.model.LeaveEntitlement;

public interface LeaveEntitlementService {

    LeaveEntitlement createLeaveEntitlement(LeaveEntitlement leaveEntitlement);

    List<LeaveEntitlement> getLeaveEntitlementByEmployeeID(int employeeId);

    LeaveEntitlement updateLeaveEntitlement(int id, LeaveEntitlement updatedEntitlement);

    void deleteLeaveEntitlement(int id);

    List<LeaveEntitlement> findAllLeaveEntitlements();

    void saveLeaveEntitlement(LeaveEntitlement leaveEntitlement);

	boolean isWithinAnnualLeaveLimit(int employeeId, LocalDate startDate, LocalDate endDate);

	boolean isWithinMedicalLeaveLimit(int employeeId, LocalDate startDate, LocalDate endDate);

	boolean isValidLeavePeriod(LocalDate startDate, LocalDate endDate);
}
