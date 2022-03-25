package edu.gatech.communicationservice.persistence.repositories;

import edu.gatech.communicationservice.persistence.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Integer> {

}
