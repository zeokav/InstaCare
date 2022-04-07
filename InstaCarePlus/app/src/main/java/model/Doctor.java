package model;
import java.util.LinkedHashSet;
import java.util.Set;

import lombok.Data;

@Data
public class Doctor {
    private Integer id;
    private String fullName;
    private String email;
    private String passwordHash;
    private String verified;
    private String specialty;
    private Set<ConsultationQueue> consultationQueues = new LinkedHashSet<>();
    private Set<Prescription> prescriptions = new LinkedHashSet<>();
}
