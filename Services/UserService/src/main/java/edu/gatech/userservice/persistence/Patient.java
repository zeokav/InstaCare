package edu.gatech.userservice.persistence;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "patients")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false)
    private Integer id;

    @Column(name = "full_name", nullable = false, length = 50)
    private String fullName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 64)
    private String passwordHash;

    @OneToMany(mappedBy = "ownerUid")
    @ToString.Exclude
    private Set<Resource> resources = new LinkedHashSet<>();

    @OneToMany(mappedBy = "patientUid")
    @ToString.Exclude
    private Set<ConsultationQueue> consultationQueues = new LinkedHashSet<>();

    @OneToMany(mappedBy = "patientUid")
    @ToString.Exclude

    private Set<Prescription> prescriptions = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Patient patient = (Patient) o;
        return id != null && Objects.equals(id, patient.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}