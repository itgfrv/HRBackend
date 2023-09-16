package com.gafarov.bastion.model;

import com.gafarov.bastion.entity.user.Activity;
import com.gafarov.bastion.entity.user.Role;
import com.gafarov.bastion.entity.user.UserStatus;

public record PersonalInfo(
        Integer id,
        String email,
        String firstname,
        String lastname,
        Role role,
        UserStatus userStatus,
        Activity activity
) {
}
