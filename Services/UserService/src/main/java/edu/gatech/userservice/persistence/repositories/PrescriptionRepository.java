package edu.gatech.userservice.persistence.repositories;

import edu.gatech.userservice.persistence.Patient;
import edu.gatech.userservice.persistence.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {

    List<Prescription> findByPatientUid(Patient patient);
}
