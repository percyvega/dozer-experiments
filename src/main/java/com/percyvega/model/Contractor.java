package com.percyvega.model;

import lombok.Data;

import java.time.Period;

@Data
public class Contractor {
    private int id;
    private double wages;
    private String agencyName;
    private Period contractPeriod;
}
