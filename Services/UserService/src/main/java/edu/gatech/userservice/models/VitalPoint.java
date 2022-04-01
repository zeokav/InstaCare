package edu.gatech.userservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VitalPoint {
    private Long timeMillis;
    private Double spo2;
    private Double bp;
    private Double ecg;
}
