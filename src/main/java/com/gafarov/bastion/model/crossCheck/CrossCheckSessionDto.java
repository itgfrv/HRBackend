package com.gafarov.bastion.model.crossCheck;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrossCheckSessionDto {
    private Integer id;
    private LocalDateTime date;
    private String description;
    private List<CrossCheckAttemptDto> attempts;
}