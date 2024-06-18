package sg.nus.iss.com.Leaveapp.model;

import jakarta.persistence.*;

import java.time.*;
import java.util.List;
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
	
	private String nameOfSupportingCoworker;
	
	private boolean overseas;
	
	private String overseasContact;
	
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

	public LeaveEntitlement getEntitlement() {
		return entitlement;
	}

	public void setEntitlement(LeaveEntitlement type) {
		this.entitlement = type;
	}

	public String getNameOfSupportingCoworker() {
		return nameOfSupportingCoworker;
	}

	public void setNameOfSupportingCoworker(String nameOfSupportingCoworker) {
		this.nameOfSupportingCoworker = nameOfSupportingCoworker;
	}

	public boolean isOverseas() {
		return overseas;
	}

	public void setOverseas(boolean overseas) {
		this.overseas = overseas;
	}

	public String getOverseasContact() {
		return overseasContact;
	}

	public void setOverseasContact(String overseasContact) {
		this.overseasContact = overseasContact;
	}
	
	public Integer getNumberOfDays() {
		return end.compareTo(start) + 1;
	}
	
	public Boolean isOverlappedWith(List<Leave> consumedLeave) {
		boolean hasOverlap = false;
		for(Leave l : consumedLeave) {
			if(isOverlappedWith(l)) {
				hasOverlap = true;
			}
		}
		return hasOverlap;
	}
	
	public Boolean isOverlappedWith(Leave other) {
		if(getStart().compareTo(other.getStart()) == 0) {
			return true;
		}
		if(getStart().compareTo(other.getStart()) < 0) {
			return getEnd().compareTo(other.getStart()) >= 0;
		} else {
			return other.getEnd().compareTo(getStart()) >= 0;
		}
	}
	
	public Boolean isConsumedOrConsuming() {
		return this.status.compareTo(LeaveStatus.Applied) == 0 || this.status.compareTo(LeaveStatus.Approved) == 0;
	}
	
	public static Integer consumedDaysOfLeave(List<Leave> consumedLeaves) {
		return consumedLeaves
		.stream()
		.map(l -> l.getNumberOfDays())
		.reduce((d1, d2) -> d1 + d2)
		.get();
	}
}
