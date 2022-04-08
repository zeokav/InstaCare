package edu.gatech.userservice.controllers;

import edu.gatech.userservice.AuthUtils;
import edu.gatech.userservice.models.LoginRequest;
import edu.gatech.userservice.models.NewDoctorRequest;
import edu.gatech.userservice.models.NewPatientRequest;
import edu.gatech.userservice.models.UserAuthResponse;
import edu.gatech.userservice.persistence.Doctor;
import edu.gatech.userservice.persistence.Patient;
import edu.gatech.userservice.persistence.repositories.DoctorRepository;
import edu.gatech.userservice.persistence.repositories.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AuthUtils authUtils;

    @Autowired
    public AuthController(PatientRepository patientRepository, DoctorRepository doctorRepository, AuthUtils authUtils) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.authUtils = authUtils;
    }

    @PostMapping("/new-patient")
    public ResponseEntity<UserAuthResponse> handleNewPatient(@RequestBody NewPatientRequest request) {
        Patient patient = new Patient();
        patient.setEmail(request.getEmail());
        patient.setFullName(request.getFullName());
        patient.setDateOfBirth(request.getDateOfBirth());
        String hashedPass = DigestUtils.md5DigestAsHex(request.getPassword().getBytes(StandardCharsets.UTF_8));
        patient.setPasswordHash(hashedPass);

        patient = this.patientRepository.save(patient);
        return ResponseEntity.ok(UserAuthResponse.builder()
                .authToken(this.authUtils.generateToken("patient", request.getEmail()))
                .scope("patient")
                .userId(patient.getId())
                .build());
    }

    @PostMapping("/new-doctor")
    public ResponseEntity<UserAuthResponse> handleNewDoctor(@RequestBody NewDoctorRequest request) {
        Doctor doctor = new Doctor();
        doctor.setEmail(request.getEmail());
        doctor.setFullName(request.getFullName());
        doctor.setVerified("pending");
        String hashedPass = DigestUtils.md5DigestAsHex(request.getPassword().getBytes(StandardCharsets.UTF_8));
        doctor.setPasswordHash(hashedPass);
        doctor.setSpecialty(request.getSpecialty());

        doctor = this.doctorRepository.save(doctor);
        return ResponseEntity.ok(UserAuthResponse.builder()
                .authToken(this.authUtils.generateToken("doctor", request.getEmail()))
                .scope("doctor")
                .userId(doctor.getId())
                .build());
    }


    @PostMapping("/login")
    public ResponseEntity<UserAuthResponse> handleLoginRequest(@RequestBody LoginRequest loginRequest) {
        String hashedPass = DigestUtils.md5DigestAsHex(loginRequest.getPassword().getBytes(StandardCharsets.UTF_8));
        List<Doctor> doctors = this.doctorRepository.findDoctorByEmail(loginRequest.getEmail());
        if (!doctors.isEmpty()) {
            if (doctors.get(0).getPasswordHash().startsWith(hashedPass)) {
                return ResponseEntity.ok(UserAuthResponse.builder()
                        .authToken(this.authUtils.generateToken("doctor", loginRequest.getEmail()))
                        .scope("doctor")
                        .userId(doctors.get(0).getId())
                        .build());
            } else {
                log.info("No doctor found, checking patients.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        }

        List<Patient> patients = this.patientRepository.findPatientByEmail(loginRequest.getEmail());
        if (!patients.isEmpty()) {
            if (patients.get(0).getPasswordHash().startsWith(hashedPass)) {
                return ResponseEntity.ok(UserAuthResponse.builder()
                        .authToken(this.authUtils.generateToken("patient", loginRequest.getEmail()))
                        .scope("patient")
                        .userId(patients.get(0).getId())
                        .build());
            } else {
                log.info("Wrong credentials.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}
