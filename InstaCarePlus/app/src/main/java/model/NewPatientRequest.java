package model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NewPatientRequest {
    public String fullName;
    public LocalDate dateOfBirth;
    public String email;
    public String password;
}
