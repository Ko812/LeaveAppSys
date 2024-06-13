package sg.nus.iss.com.Leaveapp.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;

@Entity
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


    public Employee(String name, String role) {
		super();
		this.name = name;
		this.role = role;
	}

    // Getters and Setters
    


	public Long getId() { return id; }
    
    public void setId(Long id) { this.id = id; }

    
    
    public String getName() { return name; }
    
    public void setName(String name) { this.name = name; }

    
    
    public String getRole() { return role; }
    
    public void setRole(String role) { this.role = role; }
    
    
    
    
}