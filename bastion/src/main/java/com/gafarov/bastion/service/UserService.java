package com.gafarov.bastion.service;

import com.gafarov.bastion.entity.User;

public interface UserService {

    User addNewUser(User user);
    User findUserByEmail(String email);
}