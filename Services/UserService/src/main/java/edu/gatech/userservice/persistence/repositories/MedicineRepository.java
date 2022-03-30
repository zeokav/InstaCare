package edu.gatech.userservice.persistence.repositories;

import edu.gatech.userservice.persistence.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Integer> {

}
