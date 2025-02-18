package com.gafarov.bastion.service.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticDataResponse {
    private String status;
    private List<StatisticData> data;
    private List<Double> trend;
    private Double avg;
}
