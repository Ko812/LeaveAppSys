package sg.nus.iss.service.workshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Course {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private int id;
	private String code;
	
	private String name;
	private String description;
	
	public Course() {}
	
	public Course(String code, String name,String description)
	{
		
		this.code = code;
		this.name = name;
		this.description = description;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	@Override 
	public String toString()
	{
		return "Course [id=" + id + ", code=" + code + ", name=" + name + ", description=" + description + "]";
	  }

	}
	
	
	
	
	
	

