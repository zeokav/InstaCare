package edu.gatech.communicationservice.persistence.repositories;

import edu.gatech.communicationservice.persistence.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {

}
