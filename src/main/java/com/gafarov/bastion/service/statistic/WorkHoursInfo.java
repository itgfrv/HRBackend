package com.gafarov.bastion.service.statistic;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WorkHoursInfo {
    private LocalDate date;
    private Double hours;
    private Double breaksP;

    public WorkHoursInfo(LocalDate date, Double hours, Double breaksP) {
        this.date = date;
        this.hours = hours;
        this.breaksP = breaksP;
    }
}
