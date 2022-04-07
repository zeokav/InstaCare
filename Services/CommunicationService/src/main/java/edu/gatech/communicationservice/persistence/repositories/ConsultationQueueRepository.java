package edu.gatech.communicationservice.persistence.repositories;

import edu.gatech.communicationservice.persistence.ConsultationQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultationQueueRepository extends JpaRepository<ConsultationQueue, Long> {
    public List<ConsultationQueue> findAllBySpecialtyAndIsAssignedOrderByAddedOnAsc(String specialty, Integer isAssigned);
}
