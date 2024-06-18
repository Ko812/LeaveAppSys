package sg.nus.iss.com.Leaveapp.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Role {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@OneToMany(mappedBy="role")
	private List<Employee> employees;
	
	@OneToMany(mappedBy="role")
	private List<LeaveEntitlement> entitlements;

	
	
	public Role(String name, List<Employee> employees, List<LeaveEntitlement> entitlements) {
		super();
		this.name = name;
		this.employees = employees;
		this.entitlements = entitlements;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public List<LeaveEntitlement> getEntitlements() {
		return entitlements;
	}

	public void setEntitlements(List<LeaveEntitlement> entitlements) {
		this.entitlements = entitlements;
	}
	
	
	
}
