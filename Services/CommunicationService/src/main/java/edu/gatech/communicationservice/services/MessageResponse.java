package edu.gatech.communicationservice.services;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageResponse {
    private String message;
    private Integer messageId;
    private boolean fromPatient;
}
