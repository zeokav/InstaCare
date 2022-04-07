package model;

import lombok.Data;

@Data
public class ConsultationClaimRequest {
    private Integer doctorId;
    private Long consultationId;
}
