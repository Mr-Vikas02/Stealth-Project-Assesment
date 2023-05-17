package com.stealth.startup.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {
    @Id
    private Long id;
    private String city;
    private String country;
    private String pincode;
}
