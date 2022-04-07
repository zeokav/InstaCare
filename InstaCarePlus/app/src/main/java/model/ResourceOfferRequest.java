package model;

import lombok.Data;

@Data
public class ResourceOfferRequest {
    public String itemName;
    public Integer userId;
    public Integer quantity;
    public Double price;
    public Double latitude;
    public Double longitude;
    public String description;
}
