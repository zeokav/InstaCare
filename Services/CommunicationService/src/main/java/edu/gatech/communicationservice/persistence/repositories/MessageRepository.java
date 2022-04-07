package edu.gatech.communicationservice.persistence.repositories;

import edu.gatech.communicationservice.persistence.ConsultationQueue;
import edu.gatech.communicationservice.persistence.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByConsultationAndIdGreaterThan(ConsultationQueue consultation, Integer id);
}
