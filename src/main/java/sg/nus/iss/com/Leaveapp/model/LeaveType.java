package sg.nus.iss.com.Leaveapp.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class LeaveType {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;

    @OneToMany(mappedBy="type", fetch=FetchType.LAZY)
    private List<Leave> leaves;
    // Getters and Setters
    
    public Long getId() { return id; }
    
    public void setId(Long id) { this.id = id; }
        
    public String getType() { return type; }
    
    public void setType(String type) { this.type = type; }
    
    
    
}
