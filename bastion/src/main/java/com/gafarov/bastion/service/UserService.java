package com.gafarov.bastion.service;

import com.gafarov.bastion.entity.user.User;

public interface UserService {

    User addNewUser(User user);
    User findUserByEmail(String email);
}