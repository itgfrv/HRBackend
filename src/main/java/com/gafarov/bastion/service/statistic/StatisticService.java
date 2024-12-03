package com.gafarov.bastion.service.statistic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gafarov.bastion.service.UserService;
import com.gafarov.bastion.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StatisticService {
    private final HttpClient client;
    @Value("${statistic-service.url}")
    private String url;
    @Value("${statistic-service.auth.username}")
    private String username;
    @Value("${statistic-service.auth.password}")
    private String password;
    @Value("${statistic-service.request.login}")
    private String login;
    @Value("${statistic-service.request.password}")
    private String requestPassword;
    private final DateTimeFormatter formatter;
    private final UserServiceImpl userService;


    public StatisticService(UserServiceImpl userService) {
        this.userService = userService;
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                //.sslContext(SslContextFactory.createInsecureContext())
                .build();
    }

    private HttpRequest getRequest(String endpoint, String userId, String endDate, String startDate) {
        Map<String, String> formData = Map.of(
                "login", login,
                "password", requestPassword,
                "user_id", userId,
                "period", String.format("%s;%s", endDate, startDate)
        );
        String formBody = formData.entrySet().stream()
                .map(entry -> URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "=" +
                        URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                .reduce((a, b) -> a + "&" + b)
                .orElse("");
        return HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s/%s", url, endpoint)))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8)))
                .POST(HttpRequest.BodyPublishers.ofString(formBody))
                .build();
    }

    public StatisticDataResponse getForEndpoint(String endpoint, String userId, LocalDate startDate, LocalDate endDate) {
        var statisticId = userService.findUserById(Integer.valueOf(userId)).getStatisticId();
        HttpRequest request = getRequest(endpoint, String.valueOf(statisticId), startDate.format(formatter), endDate.format(formatter));
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return parseResponse(response.body());
            } else {
                return new StatisticDataResponse("error", new ArrayList<>());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

//    public String getUserOrdersInWorkHours(String userId, LocalDate startDate, LocalDate endDate) {
//        HttpRequest request = getRequest("getUserOrdersInWorkHours", userId, startDate.format(formatter), endDate.format(formatter));
//        try {
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//            if (response.statusCode() == 200) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                Response apiResponse = objectMapper.readValue(response.body(), Response.class);
//
//                if ("success".equals(apiResponse.getStatus())) {
//                    Map<String, Integer> resultMap = objectMapper.convertValue(apiResponse.getResult(), new TypeReference<Map<String, Integer>>() {
//                    });
//                    List<StatisticData> records = new ArrayList<>();
//
//                    for (Map.Entry<String, Integer> entry : resultMap.entrySet()) {
//                        LocalDate date = LocalDate.parse(entry.getKey(), formatter);
//                        int count = entry.getValue();
//                        records.add(new StatisticData(date, count));
//                    }
//                    return new StatisticDataResponse("success", records);
//                } else {
//                    String error = objectMapper.convertValue(apiResponse.getResult(), String.class);
//                    return new StatisticDataResponse(error, new ArrayList<>());
//                }
//            } else {
//                return new StatisticDataResponse("error", new ArrayList<>());
//            }
//        } catch (IOException | InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }

    private StatisticDataResponse parseResponse(String response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Response apiResponse = objectMapper.readValue(response, Response.class);

        if ("success".equals(apiResponse.getStatus())) {
            Map<String, Double> resultMap = objectMapper.convertValue(apiResponse.getResult(), new TypeReference<Map<String, Double>>() {
            });
            List<StatisticData> records = new ArrayList<>();

            for (Map.Entry<String, Double> entry : resultMap.entrySet()) {
                LocalDate date = LocalDate.parse(entry.getKey(), formatter);
                double count = entry.getValue();
                records.add(new StatisticData(date, count));
            }
            return new StatisticDataResponse("success", records);
        } else {
            String error = objectMapper.convertValue(apiResponse.getResult(), String.class);
            return new StatisticDataResponse(error, new ArrayList<>());
        }
    }
}
