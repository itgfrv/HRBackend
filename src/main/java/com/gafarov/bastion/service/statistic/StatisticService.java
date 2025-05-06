package com.gafarov.bastion.service.statistic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gafarov.bastion.entity.user.Role;
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
import java.util.*;

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
                .build();
    }

    private HttpRequest getRequest(String endpoint, String userId, String endDate, String startDate, Map<String, String> additionalParams) {
        Map<String, String> formData = new HashMap<>(Map.of(
                "login", login,
                "password", requestPassword,
                "user_id", userId,
                "period", String.format("%s;%s", endDate, startDate)
        ));

        if (additionalParams != null) {
            formData.putAll(additionalParams);
        }
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
        HttpRequest request = getRequest(endpoint, String.valueOf(statisticId), startDate.format(formatter), endDate.format(formatter), null);
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                List<StatisticData> statisticData;
                if (endpoint.equals("getUserDailyRating")){
                    statisticData = parseResponse(response.body(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                } else{
                    statisticData  = parseResponse(response.body(), formatter);
                }
                double avg = 0d;
                try{
                    avg  = calculateAvg(endpoint, startDate, endDate);
                } catch (Exception e) {
                }
                return new StatisticDataResponse("success", statisticData, calculateTrend(statisticData), avg);
            } else {
                return new StatisticDataResponse("error", Collections.emptyList(), Collections.emptyList(), 0d);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private List<StatisticData> parseResponse(String response, DateTimeFormatter formatter) throws JsonProcessingException {
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
            return records;
        } else {
            return Collections.emptyList();
        }
    }
    private List<Double> calculateTrend(List<StatisticData> records) {
        if (records.isEmpty()) return Collections.emptyList();

        int n = records.size();
        double sumX = 0, sumY = 0, sumXY = 0, sumXX = 0;
        for (int i = 0; i < n; i++) {
            sumX += i;
            sumY += records.get(i).getCount();
            sumXY += i * records.get(i).getCount();
            sumXX += i * i;
        }
        double slope = (n * sumXY - sumX * sumY) / (n * sumXX - sumX * sumX);
        double intercept = (sumY - slope * sumX) / n;
        List<Double> trendValues = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            trendValues.add(slope * i + intercept);
        }
        return trendValues;
    }
    private Double calculateAvg(String endpoint, LocalDate startDate, LocalDate endDate) throws IOException, InterruptedException {
        var employees = userService.findUsersByRole(Role.EMPLOYEE);
        var allEmployeeStatistic = new ArrayList<StatisticData>();
        for(var e: employees) {
            HttpRequest request = getRequest(endpoint, String.valueOf(e.getStatisticId()), startDate.format(formatter), endDate.format(formatter), null);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            allEmployeeStatistic.addAll(parseResponse(response.body(), formatter));
        }
        return allEmployeeStatistic.stream().mapToDouble(StatisticData::getCount).average().orElseThrow();
    }

    public List<SalaryInfo> getUserSalaryInfo(String userId, LocalDate startDate, LocalDate endDate) {
        var statisticId = userService.findUserById(Integer.valueOf(userId)).getStatisticId();
        HttpRequest request = getRequest("getUserSalaryInfo", String.valueOf(statisticId), startDate.format(formatter), endDate.format(formatter), null);
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return parseSalaryResponse(response.body());
            } else {
                return Collections.emptyList();
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private List<SalaryInfo> parseSalaryResponse(String responseBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseBody);
        if (!rootNode.has("status") || !rootNode.get("status").asText().equals("success")) {
            return Collections.emptyList();
        }
        JsonNode resultNode = rootNode.get("result");
        TypeReference<Map<String, Map<String, Map<String, Double>>>> typeRef = new TypeReference<>() {};
        Map<String, Map<String, Map<String, Double>>> resultMap = objectMapper.convertValue(resultNode, typeRef);

        List<SalaryInfo> salaryInfos = new ArrayList<>();
        for (String monthKey : resultMap.keySet()) {
            Map<String, Map<String, Double>> monthData = resultMap.get(monthKey);
            Map<String, Double> monthly = monthData.get("monthly");
            Map<String, Double> hourly = monthData.get("hourly");

            LocalDate firstDay = LocalDate.parse(monthKey + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Double monthlySalary = monthly.get("salary");
            Double monthlyFines = monthly.get("fines");
            Double hourlySalary = hourly.get("salary");
            Double hourlyFines = hourly.get("fines");

            salaryInfos.add(new SalaryInfo(
                    firstDay,
                    hourlySalary,
                    monthlySalary,
                    hourlyFines,
                    monthlyFines
            ));
        }
        return salaryInfos;
    }


    public List<WorkInfo> getUserOrdersInWorkHours(String userId, LocalDate startDate, LocalDate endDate) {
        var statisticId = userService.findUserById(Integer.valueOf(userId)).getStatisticId();
        HttpRequest request = getRequest("getUserOrdersInWorkHours",
                String.valueOf(statisticId),
                startDate.format(formatter),
                endDate.format(formatter),
                null);
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200
                    ? parseWorkResponse(response.body())
                    : Collections.emptyList();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to fetch orders data", e);
        }
    }

    private List<WorkInfo> parseWorkResponse(String responseBody) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(responseBody);

        if (!rootNode.has("status") || !rootNode.get("status").asText().equals("success")) {
            return Collections.emptyList();
        }

        JsonNode resultNode = rootNode.get("result");
        List<WorkInfo> result = new ArrayList<>();

        Iterator<Map.Entry<String, JsonNode>> dates = resultNode.fields();
        while (dates.hasNext()) {
            Map.Entry<String, JsonNode> entry = dates.next();
            String dateStr = entry.getKey();
            JsonNode dayData = entry.getValue();

            if (dayData.has("orders_p")) {
                LocalDate date = LocalDate.parse(dateStr, formatter);
                Double ordersP = dayData.get("orders_p").asDouble();
                result.add(new WorkInfo(date, ordersP));
            }
        }

        return result;
    }

    public List<WorkHoursInfo> getUserWorkHours(String userId, LocalDate startDate, LocalDate endDate) {
        return getUserWorkHours(userId,startDate,endDate,null);
    }

    public List<WorkHoursInfo> getUserWorkHours(String userId, LocalDate startDate, LocalDate endDate, Map<String, String> additionalParams) {
        var statisticId = userService.findUserById(Integer.valueOf(userId)).getStatisticId();
        HttpRequest request = getRequest("getUserWorkHours",
                String.valueOf(statisticId),
                startDate.format(formatter),
                endDate.format(formatter),
                additionalParams);
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200
                    ? parseWorkHoursResponse(response.body())
                    : Collections.emptyList();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to fetch work hours", e);
        }
    }

    private List<WorkHoursInfo> parseWorkHoursResponse(String responseBody) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(responseBody);

        if (!rootNode.has("status") || !rootNode.get("status").asText().equals("success")) {
            return Collections.emptyList();
        }

        JsonNode resultNode = rootNode.get("result");
        List<WorkHoursInfo> result = new ArrayList<>();

        resultNode.fields().forEachRemaining(entry -> {
            String dateStr = entry.getKey();
            JsonNode dayData = entry.getValue();

            LocalDate date = LocalDate.parse(dateStr, formatter);
            Double hours = dayData.get("hours").asDouble();
            Double breaksP = dayData.get("breaks_p").asDouble();

            result.add(new WorkHoursInfo(date, hours, breaksP));
        });

        return result;
    }
}
