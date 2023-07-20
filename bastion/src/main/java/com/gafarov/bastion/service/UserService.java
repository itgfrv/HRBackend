package com.gafarov.bastion.service;

import com.gafarov.bastion.entity.User;

public interface UserService {

    void addNewUser(User user);
    User findUserByEmail(String email);
}