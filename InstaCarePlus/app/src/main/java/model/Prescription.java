package model;

import java.util.LinkedHashSet;
import java.time.LocalDate;
import java.util.Set;

import lombok.Data;

@Data
public class Prescription {
    private Integer id;
    private Doctor issuerDoctorUid;

    private Patient patientUid;

    private LocalDate issueDate;

    private Set<Medicine> medicines = new LinkedHashSet<>();

    private String notes;

}
