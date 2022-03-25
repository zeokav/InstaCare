package edu.gatech.communicationservice.persistence;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "consultation_queue")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ConsultationQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consultation_id", nullable = false)
    private Long consultationId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_uid", nullable = false)
    @ToString.Exclude
    private Patient patientUid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_doctor_uid")
    @ToString.Exclude
    private Doctor assignedDoctorUid;

    @Column(name = "is_assigned", nullable = false)
    private Integer isAssigned;

    @Column(name = "specialty", nullable = false, length = 50)
    private String specialty;

    @Column(name = "added_on", nullable = false)
    private Instant addedOn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ConsultationQueue that = (ConsultationQueue) o;
        return consultationId != null && Objects.equals(consultationId, that.getConsultationId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}