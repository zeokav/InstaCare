package model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NewPatientRequest {
    private String fullName;
    private LocalDate dateOfBirth;
    private String email;
    private String password;
}
