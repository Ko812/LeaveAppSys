package sg.nus.iss.com.Leaveapp.model;

import java.util.List;

import jakarta.persistence.*;
import sg.nus.iss.com.Leaveapp.model.LeaveEntitlement;


@Entity
@Table(name="employees")
public class Employee {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String role;
    private String username;
    private String password;
    
    @ManyToOne(fetch=FetchType.EAGER, cascade= {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private Employee manager;
    
    @OneToMany(mappedBy="manager", fetch=FetchType.LAZY)
    private List<Employee> reportees;
    
    @OneToOne(mappedBy = "employee")
	private LeaveEntitlement leaveEntitlements;
    
    public Employee(String username, String password, String name, String role) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.role = role;
	}    

    public Employee() {}
    
    // Getters and Setters
    
	public Long getId() { return id; }
    
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    
    public void setRole(String role) { this.role = role; }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}
}