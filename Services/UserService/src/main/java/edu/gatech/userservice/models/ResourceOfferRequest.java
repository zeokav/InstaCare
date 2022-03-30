package edu.gatech.userservice.models;

import lombok.Data;

@Data
public class ResourceOfferRequest {
    private String itemName;
    private Integer userId;
    private Integer quantity;
    private Double price;
    private Double latitude;
    private Double longitude;
    private String description;
}
