package model;

import java.util.List;


public class PrescriptionRequest {
    public Integer patientId;
    public Integer doctorId;
    public String notes;
    public List<MedicineModel> medicineList;
}
