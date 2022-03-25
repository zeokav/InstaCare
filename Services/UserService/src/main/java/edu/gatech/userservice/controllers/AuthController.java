package edu.gatech.userservice.controllers;

import edu.gatech.userservice.models.LoginRequest;
import edu.gatech.userservice.models.NewDoctorRequest;
import edu.gatech.userservice.models.NewPatientRequest;
import edu.gatech.userservice.persistence.Doctor;
import edu.gatech.userservice.persistence.Patient;
import edu.gatech.userservice.persistence.repositories.DoctorRepository;
import edu.gatech.userservice.persistence.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController("/auth")
public class AuthController {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Autowired
    public AuthController(PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    @PostMapping("/new-patient")
    public ResponseEntity<String> handleNewPatient(@RequestBody NewPatientRequest request) {
        Patient patient = new Patient();
        patient.setEmail(request.getEmail());
        patient.setFullName(request.getFullName());
        patient.setDateOfBirth(request.getDateOfBirth());
        String hashedPass = DigestUtils.md5DigestAsHex(request.getPassword().getBytes(StandardCharsets.UTF_8));
        patient.setPasswordHash(hashedPass);

        this.patientRepository.save(patient);
        return ResponseEntity.ok("Patient profile created!");
    }

    @PostMapping("/new-doctor")
    public ResponseEntity<String> handleNewDoctor(@RequestBody NewDoctorRequest request) {
        Doctor doctor = new Doctor();
        doctor.setEmail(request.getEmail());
        doctor.setFullName(request.getFullName());
        doctor.setVerified("pending");
        String hashedPass = DigestUtils.md5DigestAsHex(request.getPassword().getBytes(StandardCharsets.UTF_8));
        doctor.setPasswordHash(hashedPass);
        doctor.setSpecialty(request.getSpecialty());

        this.doctorRepository.save(doctor);
        return ResponseEntity.ok("Doctor profile created!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> handleLoginRequest(@RequestBody LoginRequest loginRequest) {
        return null;
    }
}
