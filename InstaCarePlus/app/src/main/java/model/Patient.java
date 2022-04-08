package model;


import java.util.LinkedHashSet;
import java.util.Set;

public class Patient {
    public Integer id;
    public String fullName;
    public String dateOfBirth;
    public String email;
    public String passwordHash;
    public Set<Resource> resources = new LinkedHashSet<>();
    public Set<ConsultationQueue> consultationQueues = new LinkedHashSet<>();
    public Set<Prescription> prescriptions = new LinkedHashSet<>();

}
