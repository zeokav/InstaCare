package edu.gatech.userservice.persistence;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "medicines")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "med_id", nullable = false)
    private Long medId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prescription_id")
    @ToString.Exclude
    @JsonManagedReference
    private Prescription prescription;

    @Column(name = "medicine_name", nullable = false, length = 100)
    private String medicineName;

    @Column(name = "num_days", nullable = false)
    private Integer numDays;

    @Column(name = "notes", length = 500)
    private String notes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Medicine medicine = (Medicine) o;
        return medId != null && Objects.equals(medId, medicine.getMedId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}