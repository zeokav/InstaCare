package model;

import lombok.Data;

@Data
public class ResourceClaimRequest {
    private Integer userId;
    private Long resourceId;
}
