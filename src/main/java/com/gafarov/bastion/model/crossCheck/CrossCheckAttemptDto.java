package com.gafarov.bastion.model.crossCheck;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrossCheckAttemptDto {
    private Integer id;
    private Integer evaluatorId;
    private String status;
}