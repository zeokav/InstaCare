package edu.gatech.userservice.persistence.repositories;

import edu.gatech.userservice.persistence.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    List<Doctor> findDoctorByEmail(String email);
}
