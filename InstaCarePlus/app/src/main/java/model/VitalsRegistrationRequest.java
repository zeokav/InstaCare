package model;

import lombok.Data;

import java.util.List;

@Data
public class VitalsRegistrationRequest {
    private Integer patientId;
    private List<VitalPoint> batchedVitals;
}
