package edu.gatech.userservice.persistence;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "prescriptions")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prescription_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "issuer_doctor_uid")
    @ToString.Exclude
    @JsonManagedReference
    private Doctor issuerDoctorUid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_uid")
    @ToString.Exclude
    @JsonManagedReference
    private Patient patientUid;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @OneToMany(mappedBy = "prescription")
    @ToString.Exclude
    @JsonBackReference
    private Set<Medicine> medicines = new LinkedHashSet<>();

    @Column(name = "notes", nullable = false)
    private String notes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Prescription that = (Prescription) o;
        return id != null && Objects.equals(id, that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}