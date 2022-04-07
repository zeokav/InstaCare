package model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NewPatientRequest {
    public String fullName;
    public String dateOfBirth;
    public String email;
    public String password;
}
