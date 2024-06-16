package sg.nus.iss.com.Leaveapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

//@Entity
public class ApprovalHierarchy {

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

//	@ManyToOne
//	@JoinColumn(name = "employee_id")
	private Employee employee;

//	@ManyToOne
//	@JoinColumn(name = "leave_entitlement_id")
	private LeaveEntitlement leaveEntitlement;

//	@ManyToOne
//	@JoinColumn(name = "approver_id")
	private Employee approver;

	private boolean approved;
	
	public ApprovalHierarchy(Employee employee, Employee approver) {
		super();
		this.employee = employee;
		this.approver = approver;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public LeaveEntitlement getLeaveEntitlement() {
		return leaveEntitlement;
	}

	public void setLeaveEntitlement(LeaveEntitlement leaveEntitlement) {
		this.leaveEntitlement = leaveEntitlement;
	}

	public Employee getApprover() {
		return approver;
	}

	public void setApprover(Employee approver) {
		this.approver = approver;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

}
