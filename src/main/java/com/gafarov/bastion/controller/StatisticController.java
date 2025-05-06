package com.gafarov.bastion.controller;

import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.service.statistic.SalaryInfo;
import com.gafarov.bastion.service.statistic.StatisticData;
import com.gafarov.bastion.service.statistic.StatisticDataResponse;
import com.gafarov.bastion.service.statistic.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/statistic")
@AllArgsConstructor
public class StatisticController {
    @Autowired
    private final StatisticService statisticService;


    @PostMapping("/getStatistic")
    public StatisticDataResponse getStatistic(String statistic, String userId, LocalDate startDate,
                                              LocalDate endDate, @AuthenticationPrincipal User user) {
        if (statistic.equals("salaryMonthInfo")) {
            var salaryInfo = statisticService.getUserSalaryInfo(userId, startDate, endDate);
            var response = new StatisticDataResponse();
            response.setData(salaryInfo.stream().map(si->new StatisticData(si.getFirstMonthDay(), si.getMonthlySalary())).toList());
            response.setAvg(salaryInfo.stream().mapToDouble(SalaryInfo::getMonthlySalary).average().getAsDouble());
            return response;
        }
        if (statistic.equals("salaryHourInfo")) {
            var salaryInfo = statisticService.getUserSalaryInfo(userId, startDate, endDate);
            var response = new StatisticDataResponse();
            response.setData(salaryInfo.stream().map(si->new StatisticData(si.getFirstMonthDay(), si.getHourlySalary())).toList());
            response.setAvg(salaryInfo.stream().mapToDouble(SalaryInfo::getHourlySalary).average().getAsDouble());
            return response;
        }
        if (statistic.equals("finesHourInfo")) {
            var salaryInfo = statisticService.getUserSalaryInfo(userId, startDate, endDate);
            var response = new StatisticDataResponse();
            response.setData(salaryInfo.stream().map(si->new StatisticData(si.getFirstMonthDay(), si.getHourlyFines())).toList());
            response.setAvg(salaryInfo.stream().mapToDouble(SalaryInfo::getHourlyFines).average().getAsDouble());
            return response;
        }
        if (statistic.equals("finesMonthInfo")) {
            var salaryInfo = statisticService.getUserSalaryInfo(userId, startDate, endDate);
            var response = new StatisticDataResponse();
            response.setData(salaryInfo.stream().map(si->new StatisticData(si.getFirstMonthDay(), si.getMonthlyFines())).toList());
            response.setAvg(salaryInfo.stream().mapToDouble(SalaryInfo::getMonthlyFines).average().getAsDouble());
            return response;
        }

        if(statistic.equals("getUserOrdersInWorkHours")){
            var workInfo = statisticService.getUserOrdersInWorkHours(userId, startDate, endDate);
            var response = new StatisticDataResponse();
            response.setData(workInfo.stream().map(w->new StatisticData(w.getDate(), w.getOrdersP())).toList());
            return response;
        }

        if(statistic.equals("getUserWorkHours")){
            var workInfo = statisticService.getUserWorkHours(userId, startDate, endDate);
            var response = new StatisticDataResponse();
            response.setData(workInfo.stream().map(w->new StatisticData(w.getDate(), w.getHours())).toList());
            return response;
        }

        if(statistic.equals("getUserWorkHoursWithoutBreaks")){
            var workInfo = statisticService.getUserWorkHours(userId, startDate, endDate, Map.of("breaks_off","1"));
            var response = new StatisticDataResponse();
            response.setData(workInfo.stream().map(w->new StatisticData(w.getDate(), w.getHours())).toList());
            return response;
        }
        if(statistic.equals("getUserWorkHoursWithoutTrips")){
            var workInfo = statisticService.getUserWorkHours(userId, startDate, endDate, Map.of("trips_off","1"));
            var response = new StatisticDataResponse();
            response.setData(workInfo.stream().map(w->new StatisticData(w.getDate(), w.getHours())).toList());
            return response;
        }
        if(statistic.equals("getUserWorkHoursWithoutBrakesAndTrips")){
            var workInfo = statisticService.getUserWorkHours(userId, startDate, endDate, Map.of("breaks_off","1","trips_off","1"));
            var response = new StatisticDataResponse();
            response.setData(workInfo.stream().map(w->new StatisticData(w.getDate(), w.getHours())).toList());
            return response;
        }
        return statisticService.getForEndpoint(statistic, userId, startDate, endDate);
    }
//    @PostMapping("/getUserOrdersInWorkHours")
//    public StatisticDataResponse getUserOrdersInWorkHours(String statistic, String userId, LocalDate startDate,
//                                                          LocalDate endDate, @AuthenticationPrincipal User user) {
//        return statisticService.getUserOrdersInWorkHours(statistic, userId, startDate, endDate);
//    }
}
