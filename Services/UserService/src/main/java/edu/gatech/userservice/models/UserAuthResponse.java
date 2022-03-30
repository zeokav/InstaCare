package edu.gatech.userservice.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAuthResponse {
    private String authToken;
    private String scope;
}
