package com.gafarov.bastion.model;

import com.gafarov.bastion.entity.user.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormDto {
    private int id;
    private int firstname;
    private int lastname;
    private UserStatus status;
}
