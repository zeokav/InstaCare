package edu.gatech.userservice.controllers;

import edu.gatech.userservice.models.MedicineModel;
import edu.gatech.userservice.models.PrescriptionRequest;
import edu.gatech.userservice.persistence.Doctor;
import edu.gatech.userservice.persistence.Medicine;
import edu.gatech.userservice.persistence.Patient;
import edu.gatech.userservice.persistence.Prescription;
import edu.gatech.userservice.persistence.repositories.DoctorRepository;
import edu.gatech.userservice.persistence.repositories.MedicineRepository;
import edu.gatech.userservice.persistence.repositories.PatientRepository;
import edu.gatech.userservice.persistence.repositories.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/prescription")
public class PrescriptionController {


    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final MedicineRepository medicineRepository;
    private final PrescriptionRepository prescriptionRepository;

    @Autowired
    public PrescriptionController(DoctorRepository doctorRepository, PatientRepository patientRepository,
                                  MedicineRepository medicineRepository,
                                  PrescriptionRepository prescriptionRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.medicineRepository = medicineRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPrescription(@RequestBody PrescriptionRequest prescriptionRequest) {
        Patient patient = this.patientRepository.findPatientById(prescriptionRequest.getPatientId());
        if (patient == null) {
            return ResponseEntity.badRequest().body("Invalid patient ID");
        }

        Doctor doctor = this.doctorRepository.findDoctorById(prescriptionRequest.getDoctorId());
        if (doctor == null) {
            return ResponseEntity.badRequest().body("Invalid patient ID");
        }

        Prescription prescription = new Prescription();
        prescription.setIssueDate(LocalDate.now());
        prescription.setIssuerDoctorUid(doctor);
        prescription.setPatientUid(patient);
        prescription.setNotes(prescriptionRequest.getNotes());

        prescription = this.prescriptionRepository.save(prescription);

        for (MedicineModel reqMedicine: prescriptionRequest.getMedicineList()) {
            Medicine medicine = new Medicine();
            medicine.setMedicineName(reqMedicine.getName());
            medicine.setNumDays(reqMedicine.getNumDays());
            medicine.setNotes(reqMedicine.getNotes());
            medicine.setPrescription(prescription);

            this.medicineRepository.save(medicine);
        }

        return ResponseEntity.ok("Registered prescription with the patient.");
    }

    @GetMapping("/history")
    public ResponseEntity<List<Prescription>> getPrescriptionList(@RequestParam("user") Integer userId) {
        Patient patient = this.patientRepository.findPatientById(userId);
        if (patient == null) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(this.prescriptionRepository.findByPatientUid(patient));
    }

}
