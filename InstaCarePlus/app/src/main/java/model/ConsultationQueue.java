package model;

import java.time.Instant;

import lombok.Data;

@Data
public class ConsultationQueue {
    private Long consultationId;
    private Patient patientUid;
    private Doctor assignedDoctorUid;
    private Integer isAssigned;
    private String specialty;
    private Instant addedOn;
}
