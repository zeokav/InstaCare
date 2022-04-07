package model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAuthResponse {
    public String authToken;
    public String scope;
}
