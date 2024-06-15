package sg.nus.iss.com.Leaveapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
//import lombok.Getter;
//import lombok.Setter;

//@Entity
//@Getter@Setter
////@Table(name = "?")
//public class LeaveStatus {
//	//Applied, Cancelled, Approved, Rejected, Deleted
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//	
//	@ManyToOne
//	@JoinColumn(name = "LeaveApplication_id")
//
//	private Leave leaveApplication;
//
//	private String status;
//
//	private String comment;
//
//
//}
public enum LeaveStatus {
  Applied, Cancelled, Approved, Rejected, Deleted
}
