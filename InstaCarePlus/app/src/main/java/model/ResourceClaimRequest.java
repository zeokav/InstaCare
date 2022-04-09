package model;

import lombok.Data;

@Data
public class ResourceClaimRequest {
    public Integer userId;
    public Long resourceId;
}
