package edu.gatech.userservice.controllers;

import edu.gatech.userservice.models.ResourceClaimRequest;
import edu.gatech.userservice.models.ResourceOfferRequest;
import edu.gatech.userservice.persistence.Patient;
import edu.gatech.userservice.persistence.Resource;
import edu.gatech.userservice.persistence.repositories.PatientRepository;
import edu.gatech.userservice.persistence.repositories.ResourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/resources")
@Slf4j
public class ResourceController {

    private final ResourceRepository resourceRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public ResourceController(ResourceRepository resourceRepository, PatientRepository patientRepository) {
        this.resourceRepository = resourceRepository;
        this.patientRepository = patientRepository;
    }

    @PostMapping("/offer")
    public ResponseEntity<Resource> offerResource(@RequestBody ResourceOfferRequest offerRequest) {
        Patient patient = this.patientRepository.findPatientById(offerRequest.getUserId());
        if (patient == null) {
            log.warn("Invalid user ID {}", offerRequest.getUserId());
            return ResponseEntity.badRequest().body(null);
        }

        Resource resource = new Resource();
        resource.setResourceQty(offerRequest.getQuantity());
        resource.setResourceName(offerRequest.getItemName());
        resource.setLatitude(offerRequest.getLatitude());
        resource.setLongitude(offerRequest.getLongitude());
        resource.setPrice(offerRequest.getPrice());
        resource.setNotes(offerRequest.getDescription());
        resource.setOwnerUid(patient);
        resource.setAvailable(1);

        return ResponseEntity.ok(resourceRepository.save(resource));
    }

    @PostMapping("/claim")
    public ResponseEntity<Resource> claimResource(@RequestBody ResourceClaimRequest resourceClaimRequest) {
        Patient patient = this.patientRepository.findPatientById(resourceClaimRequest.getUserId());
        if (patient == null) {
            log.warn("Invalid user ID {}", resourceClaimRequest.getUserId());
            return ResponseEntity.badRequest().body(null);
        }

        Optional<Resource> optionalResource =  this.resourceRepository.findById(resourceClaimRequest.getResourceId());
        if (optionalResource.isEmpty() || optionalResource.get().getAvailable() == 0) {
            log.warn("Resource not found {} or already taken.", resourceClaimRequest.getResourceId());
            return ResponseEntity.badRequest().body(null);
        }

        Resource resource = optionalResource.get();
        resource.setAvailable(0);

        return ResponseEntity.ok(resourceRepository.save(resource));
    }

    @GetMapping("/find")
    public ResponseEntity<List<Resource>> findResources(@RequestParam("q") String query) {
        return ResponseEntity.ok(resourceRepository.findResourceByAvailableAndResourceNameContaining(1, query));
    }

}
