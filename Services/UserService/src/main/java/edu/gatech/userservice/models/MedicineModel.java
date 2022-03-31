package edu.gatech.userservice.models;

import lombok.Data;

@Data
public class MedicineModel {
    String name;
    Integer numDays;
    String notes;
}
