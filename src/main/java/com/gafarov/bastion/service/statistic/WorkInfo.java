package com.gafarov.bastion.service.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class WorkInfo {
    private LocalDate date;
    private Double ordersP;
}
