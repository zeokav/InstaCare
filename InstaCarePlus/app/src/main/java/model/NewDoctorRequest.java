package model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NewDoctorRequest {
    public String fullName;
    public String dateOfBirth;
    public String email;
    public String password;
    public String specialty;
    public String verified;
}
