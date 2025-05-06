package com.gafarov.bastion.service.statistic;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Data
public class SalaryInfo {
    private LocalDate firstMonthDay;
    private Double hourlySalary;
    private Double monthlySalary;
    private Double hourlyFines;
    private Double monthlyFines;

    public SalaryInfo(LocalDate firstMonthDay, Double hourlySalary, Double monthlySalary, Double hourlyFines, Double monthlyFines) {
        this.firstMonthDay = firstMonthDay;
        this.hourlySalary = hourlySalary;
        this.monthlySalary = monthlySalary;
        this.hourlyFines = hourlyFines;
        this.monthlyFines = monthlyFines;
    }

}
