package sg.nus.iss.com.Leaveapp.model;

import jakarta.persistence.*;


@Entity
@Table(name="claims")
public class Claim {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Employee employee;
	
	private double claimDays;
	
	private String reasonSupporting;
	
	@Enumerated(EnumType.STRING)
	private LeaveStatus status;

	public Claim(Employee employee, double claimDays, String reasonSupporting, LeaveStatus status) {
		super();
		this.employee = employee;
		this.claimDays = claimDays;
		this.reasonSupporting = reasonSupporting;
		this.status = status;
	}
	
	public Claim() {
		this.status = LeaveStatus.Applied;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public double getClaimDays() {
		return claimDays;
	}

	public void setClaimDays(double claimDays) {
		this.claimDays = claimDays;
	}

	public String getReasonSupporting() {
		return reasonSupporting;
	}

	public void setReasonSupporting(String reasonSupporting) {
		this.reasonSupporting = reasonSupporting;
	}

	public LeaveStatus getStatus() {
		return status;
	}

	public void setStatus(LeaveStatus status) {
		this.status = status;
	}
	
	
}
