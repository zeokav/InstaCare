package model;

import lombok.Data;

@Data
public class LoginRequest {
    public String email;
    public String password;
}
