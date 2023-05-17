package com.stealth.startup.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "sat_results")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SATResult {
    @Id
    private Long id;
    @Column(nullable = false)
    private String name;
    private int score;
    private boolean passed;
    @Embedded
    private Address address;

    //** Calculate the 'passed' field based on SAT score **//
    public void calculatePassStatus() {
        passed = score > 0.30 * 100;
    }
}
