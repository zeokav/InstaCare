package edu.gatech.userservice.models;

import lombok.Data;

@Data
public class ResourceClaimRequest {
    private Integer userId;
    private Long resourceId;
}
