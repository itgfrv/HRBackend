package com.gafarov.bastion.model;

import com.gafarov.bastion.entity.user.Activity;
import com.gafarov.bastion.entity.user.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormDto {
    private int id;
    private String firstname;
    private String lastname;
    private UserStatus status;
    private String activity;
    private LocalDateTime createdDate;
    private LocalDateTime lastActivityDate;
}
