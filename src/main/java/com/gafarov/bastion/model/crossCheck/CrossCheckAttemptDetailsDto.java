package com.gafarov.bastion.model.crossCheck;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrossCheckAttemptDetailsDto {
    private Integer attemptId;
    private List<UserDto> users;
    private List<QuestionDto> questions;
    private String status;
}