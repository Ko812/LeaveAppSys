package sg.nus.iss.com.Leaveapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "leave_entitlements") // Updated table name
public class LeaveEntitlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @NotNull(message = "Employee ID is required")
    private Employee employee;

    @OneToOne(mappedBy = "leaveEntitlement")
    private ApprovalHierarchy approvalHierarchy;
    
    
    @NotNull(message = "Leave type is required")
    @Size(min = 1, max = 50, message = "Leave type must be between 1 and 50 characters")
    @Column(name = "leave_type") // Updated column name
    private String leaveType;

    @Max(value = 20, message = "Annual leave cannot exceed 20 days")
    @Column(name = "annual_leave") // Updated column name
    private int annualLeave;

    @Max(value = 10, message = "Sick leave cannot exceed 10 days")
    @Column(name = "sick_leave") // Updated column name
    private int sickLeave;

    @Max(value = 5, message = "Compensation leave cannot exceed 5 days")
    @Column(name = "compensation_leave") // Updated column name
    private int compensationLeave;

    @PositiveOrZero(message = "Number of days must be zero or positive")
    @Column(name = "number_of_days") // Updated column name
    private int numberOfDays;

    @PositiveOrZero(message = "Brought forward days must be zero or positive")
    @Column(name = "brought_forward") // Updated column name
    private int broughtForward;

    @PositiveOrZero(message = "Total days must be zero or positive")
    @Column(name = "total_days") // Updated column name
    private int totalDays;

    @Min(value = 2020, message = "Year must be greater than or equal to 2020")
    private int year;

    @PositiveOrZero(message = "Used days must be zero or positive")
    @Column(name = "used_days") // Updated column name
    private int usedDays;

    @PositiveOrZero(message = "Balance must be zero or positive")
    private int balance;

    public LeaveEntitlement() {
    }

    public LeaveEntitlement(Employee employee, String leaveType, int numberOfDays, int broughtForward, int totalDays, int year, int usedDays, int balance) {
        this.employee = employee;
        this.leaveType = leaveType;
        this.numberOfDays = numberOfDays;
        this.broughtForward = broughtForward;
        this.totalDays = totalDays;
        this.year = year;
        this.usedDays = usedDays;
        this.balance = balance;
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

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public int getAnnualLeave() {
		return annualLeave;
	}

	public void setAnnualLeave(int annualLeave) {
		this.annualLeave = annualLeave;
	}

	public int getSickLeave() {
		return sickLeave;
	}

	public void setSickLeave(int sickLeave) {
		this.sickLeave = sickLeave;
	}

	public int getCompensationLeave() {
		return compensationLeave;
	}

	public void setCompensationLeave(int compensationLeave) {
		this.compensationLeave = compensationLeave;
	}

	public int getNumberOfDays() {
		return numberOfDays;
	}

	public void setNumberOfDays(int numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	public int getBroughtForward() {
		return broughtForward;
	}

	public void setBroughtForward(int broughtForward) {
		this.broughtForward = broughtForward;
	}

	public int getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(int totalDays) {
		this.totalDays = totalDays;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getUsedDays() {
		return usedDays;
	}

	public void setUsedDays(int usedDays) {
		this.usedDays = usedDays;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	 public ApprovalHierarchy getApprovalHierarchy() {
	        return approvalHierarchy;
	    }

	    public void setApprovalHierarchy(ApprovalHierarchy approvalHierarchy) {
	        this.approvalHierarchy = approvalHierarchy;
	    }

  
}
