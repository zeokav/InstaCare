package edu.gatech.communicationservice.persistence.repositories;

import edu.gatech.communicationservice.persistence.ConsultationQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultationQueueRepository extends JpaRepository<ConsultationQueue, Integer> {

}
