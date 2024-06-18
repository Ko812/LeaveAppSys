package sg.nus.iss.com.Leaveapp.model;

import jakarta.persistence.*;

import java.time.*;
@Entity
@Table(name="leaves")
public class Leave {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade= {CascadeType.REFRESH})
	private Employee employee;
	
	LocalDate start;
	
	LocalDate end;
	
	private String reasons;
	
	private LeaveStatus status;
	
	@ManyToOne
	private LeaveEntitlement entitlement;
	
	public Leave() {}

	public Leave(Employee employee, LocalDate start, LocalDate end, LeaveEntitlement entitlement, String reasons, LeaveStatus status) {
		super();
		this.employee = employee;
		this.start = start;
		this.end = end;
		this.entitlement = entitlement;
		this.reasons = reasons;
		this.status = status;
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

	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public LocalDate getEnd() {
		return end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	public String getReasons() {
		return reasons;
	}

	public void setReasons(String reasons) {
		this.reasons = reasons;
	}

	public LeaveStatus getStatus() {
		return status;
	}

	public void setStatus(LeaveStatus status) {
		this.status = status;
	}

	public LeaveEntitlement getType() {
		return entitlement;
	}

	public void setType(LeaveEntitlement type) {
		this.entitlement = type;
	}
	
	
}
