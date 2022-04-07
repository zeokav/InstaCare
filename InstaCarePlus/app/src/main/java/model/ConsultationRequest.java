package model;

import lombok.Data;

@Data
public class ConsultationRequest {
    private Integer patientId;
    private String specialty;
}
