package edu.gatech.userservice.persistence;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false)
    private Integer id;

    @Column(name = "full_name", nullable = false, length = 50)
    private String fullName;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 64)
    private String passwordHash;

    @Column(name = "verified", nullable = false, length = 50)
    private String verified;

    @Column(name = "specialty", length = 50)
    private String specialty;

    @OneToMany(mappedBy = "assignedDoctorUid")
    @ToString.Exclude
    @JsonBackReference
    private Set<ConsultationQueue> consultationQueues = new LinkedHashSet<>();

    @OneToMany(mappedBy = "issuerDoctorUid")
    @ToString.Exclude
    @JsonBackReference
    private Set<Prescription> prescriptions = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Doctor doctor = (Doctor) o;
        return id != null && Objects.equals(id, doctor.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}