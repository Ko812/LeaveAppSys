package sg.nus.iss.com.Leaveapp.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "leaves")
public class Leave {

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    LocalDate start;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
    LocalDate end;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
    private Employee employee;
    private String reasons;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status;

    @ManyToOne
    private LeaveType type;

    private String comment;

    private String claimStatus;

    public Leave() {
    }

    public Leave(Employee employee, LocalDate start, LocalDate end, LeaveType type, String reasons, LeaveStatus status) {
        super();
        this.employee = employee;
        this.start = start;
        this.end = end;
        this.setType(type);
        this.reasons = reasons;
        this.setStatus(status);
    }

    public String getClaimStatus() {
        return claimStatus;
    }

    public Leave setClaimStatus(String claimStatus) {
        this.claimStatus = claimStatus;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Leave setComment(String comment) {
        this.comment = comment;
        return this;
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

    public LeaveType getType() {
        return type;
    }

    public void setType(LeaveType type) {
        this.type = type;
    }


}
