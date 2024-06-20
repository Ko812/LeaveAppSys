package sg.nus.iss.leaveapp.manage;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LeaveApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String employeeId;
    private String status;
    private String reason;
    private String startDate;
    private String endDate;

    // Getters and setters
}
