package edu.gatech.userservice.controllers;

import edu.gatech.userservice.models.ConsultationClaimRequest;
import edu.gatech.userservice.models.ConsultationRequest;
import edu.gatech.userservice.persistence.ConsultationQueue;
import edu.gatech.userservice.persistence.Doctor;
import edu.gatech.userservice.persistence.Patient;
import edu.gatech.userservice.persistence.repositories.ConsultationQueueRepository;
import edu.gatech.userservice.persistence.repositories.DoctorRepository;
import edu.gatech.userservice.persistence.repositories.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/consultation")
@Slf4j
public class ConsultationController {

    private final ConsultationQueueRepository consultationQueueRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Autowired
    public ConsultationController(ConsultationQueueRepository consultationQueueRepository,
                                  PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.consultationQueueRepository = consultationQueueRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    @PostMapping("/request")
    public ResponseEntity<ConsultationQueue> raiseConsultationRequest(
            @RequestBody ConsultationRequest consultationRequest) {

        Patient patient = this.patientRepository.findPatientById(consultationRequest.getPatientId());
        if (patient == null) {
            log.warn("Invalid patient ID");
            return ResponseEntity.badRequest().body(null);
        }

        ConsultationQueue queueItem = new ConsultationQueue();
        queueItem.setAddedOn(Instant.now());
        queueItem.setSpecialty(consultationRequest.getSpecialty());
        queueItem.setIsAssigned(0);
        queueItem.setPatientUid(patient);

        return ResponseEntity.ok(consultationQueueRepository.save(queueItem));
    }

    @GetMapping("/list_available")
    public ResponseEntity<List<ConsultationQueue>> getQueue(@RequestParam("specialty") String specialty) {
        return ResponseEntity.ok(
                consultationQueueRepository.findAllBySpecialtyAndIsAssignedOrderByAddedOnAsc(specialty, 0));
    }

    @PostMapping("/claim")
    public ResponseEntity<ConsultationQueue> claimConsultation(@RequestBody ConsultationClaimRequest request) {
        Doctor doctor = this.doctorRepository.findDoctorById(request.getDoctorId());
        if (doctor == null) {
            log.warn("Invalid doctor ID");
            return ResponseEntity.badRequest().body(null);
        }

        Optional<ConsultationQueue> optionalQueueItem =
                consultationQueueRepository.findById(request.getConsultationId());
        if (optionalQueueItem.isEmpty() || optionalQueueItem.get().getIsAssigned() == 1) {
            log.warn("Consultation not found {} or already taken.", request.getConsultationId());
            return ResponseEntity.badRequest().body(null);
        }

        ConsultationQueue queueItem = optionalQueueItem.get();
        queueItem.setIsAssigned(1);
        queueItem.setAssignedDoctorUid(doctor);
        return ResponseEntity.ok(consultationQueueRepository.save(queueItem));
    }
}
