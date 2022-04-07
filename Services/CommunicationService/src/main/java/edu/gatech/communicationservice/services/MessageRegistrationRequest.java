package edu.gatech.communicationservice.services;

import lombok.Data;

@Data
public class MessageRegistrationRequest {
    Long consultationId;
    String message;
    boolean fromPatient;
}
