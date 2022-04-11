package edu.gatech.communicationservice.services;

import edu.gatech.communicationservice.persistence.ConsultationQueue;
import edu.gatech.communicationservice.persistence.Message;
import edu.gatech.communicationservice.persistence.repositories.ConsultationQueueRepository;
import edu.gatech.communicationservice.persistence.repositories.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/messages")
@Slf4j
public class MessageController {

    private final MessageRepository messageRepository;
    private final ConsultationQueueRepository consultationQueueRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository, ConsultationQueueRepository consultationQueueRepository) {
        this.messageRepository = messageRepository;
        this.consultationQueueRepository = consultationQueueRepository;
    }

    @PostMapping("/write")
    public ResponseEntity<String> writeMessage(@RequestBody MessageRegistrationRequest registrationRequest) {

        Optional<ConsultationQueue> consultationQueue =
                consultationQueueRepository.findById(registrationRequest.getConsultationId());

        if (consultationQueue.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid consultation ID");
        }

        Message message = new Message();
        message.setConsultation(consultationQueue.get());
        message.setFromPatient(registrationRequest.fromPatient ? 1 : 0);
        message.setMessageContent(registrationRequest.getMessage());
        this.messageRepository.save(message);

        return ResponseEntity.accepted().body("Accepted task");
    }

    @GetMapping("/read")
    public ResponseEntity<List<MessageResponse>> readMessages(@RequestParam("consultation_id") Long consultationId,
                                                              @RequestParam("last_message_id") Integer lastMessageId) {

        Optional<ConsultationQueue> consultationQueue = consultationQueueRepository.findById(consultationId);

        if (consultationQueue.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        List<Message> messages =
                this.messageRepository.findAllByConsultationAndIdGreaterThan(consultationQueue.get(), lastMessageId);

        return ResponseEntity.ok(messages
                .stream()
                .map(message -> new MessageResponse(
                        message.getMessageContent(),
                        message.getId(),
                        message.getFromPatient() == 1))
                .collect(Collectors.toList()));
    }
}
