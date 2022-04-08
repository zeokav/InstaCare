package model;

import java.util.LinkedHashSet;
import java.time.LocalDate;
import java.util.Set;

import lombok.Data;

@Data
public class Prescription {
    public Integer id;
    public Doctor issuerDoctorUid;

    public Patient patientUid;

    public String issueDate;

    public Set<Medicine> medicines = new LinkedHashSet<>();

    public String notes;

}
