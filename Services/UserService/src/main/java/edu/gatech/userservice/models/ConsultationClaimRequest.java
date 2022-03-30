package edu.gatech.userservice.models;

import lombok.Data;

@Data
public class ConsultationClaimRequest {
    private Integer doctorId;
    private Long consultationId;
}
