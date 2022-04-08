package model;
import java.util.LinkedHashSet;
import java.util.Set;

import lombok.Data;

@Data
public class Doctor {
    public Integer id;
    public String fullName;
    public String email;
    public String passwordHash;
    public String verified;
    public String specialty;
    public Set<ConsultationQueue> consultationQueues = new LinkedHashSet<>();
    public Set<Prescription> prescriptions = new LinkedHashSet<>();
}
