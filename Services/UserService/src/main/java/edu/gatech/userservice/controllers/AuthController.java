package edu.gatech.userservice.controllers;

import edu.gatech.userservice.models.NewPatientRequest;
import edu.gatech.userservice.persistence.Patient;
import edu.gatech.userservice.persistence.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/auth")
public class AuthController {

    private PatientRepository patientRepository;

    @Autowired
    public AuthController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @PostMapping("/new-patient")
    public void handleNewUser(@RequestBody NewPatientRequest request) {
        Patient patient = new Patient();
//        this.patientRepository.save()
    }
}
