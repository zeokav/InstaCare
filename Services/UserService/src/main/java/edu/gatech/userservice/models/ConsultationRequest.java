package edu.gatech.userservice.models;

import lombok.Data;

@Data
public class ConsultationRequest {
    private Integer patientId;
    private String specialty;
}
