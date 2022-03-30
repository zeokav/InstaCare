package edu.gatech.communicationservice.persistence.repositories;

import edu.gatech.communicationservice.persistence.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
