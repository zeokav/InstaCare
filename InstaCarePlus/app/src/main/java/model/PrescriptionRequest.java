package model;

import lombok.Data;

import java.util.List;

@Data
public class PrescriptionRequest {
    Integer patientId;
    Integer doctorId;
    String notes;
    List<MedicineModel> medicineList;
}
