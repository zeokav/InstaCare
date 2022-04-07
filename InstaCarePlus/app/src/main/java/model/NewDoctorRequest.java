package model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NewDoctorRequest {
    private String fullName;
    private LocalDate dateOfBirth;
    private String email;
    private String password;
    private String specialty;
    private String verified;
}
