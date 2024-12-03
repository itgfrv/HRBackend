package com.gafarov.bastion.controller;

import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.service.statistic.StatisticDataResponse;
import com.gafarov.bastion.service.statistic.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/statistic")
@AllArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;

    @PostMapping("/getStatistic")
    public StatisticDataResponse getStatistic(String statistic, String userId, LocalDate startDate,
                                              LocalDate endDate, @AuthenticationPrincipal User user) {
        return statisticService.getForEndpoint(statistic, userId, startDate, endDate);
    }

//    @PostMapping("/getUserOrdersInWorkHours")
//    public StatisticDataResponse getUserOrdersInWorkHours(String statistic, String userId, LocalDate startDate,
//                                                          LocalDate endDate, @AuthenticationPrincipal User user) {
//        return statisticService.getUserOrdersInWorkHours(statistic, userId, startDate, endDate);
//    }
}
