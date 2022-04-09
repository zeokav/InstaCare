package model;

import lombok.Data;

@Data
public class ConsultationQueue {
    public Long consultationId;
    public Patient patientUid;
    public Doctor assignedDoctorUid;
    public Integer isAssigned;
    public String specialty;
    public String addedOn;
}
