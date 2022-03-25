package edu.gatech.userservice.persistence.repositories;

import edu.gatech.userservice.persistence.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

}
