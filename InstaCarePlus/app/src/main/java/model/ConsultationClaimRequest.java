package model;

import lombok.Data;

@Data
public class ConsultationClaimRequest {
    public Integer doctorId;
    public Long consultationId;
}
