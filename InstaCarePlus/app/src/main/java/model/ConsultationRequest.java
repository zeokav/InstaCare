package model;

import lombok.Data;

@Data
public class ConsultationRequest {
    public Integer patientId;
    public String specialty;
}
