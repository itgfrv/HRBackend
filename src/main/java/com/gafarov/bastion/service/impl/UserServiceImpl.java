package com.gafarov.bastion.service.impl;

import com.gafarov.bastion.entity.user.Activity;
import com.gafarov.bastion.entity.user.Role;
import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.entity.user.UserStatus;
import com.gafarov.bastion.exception.BadRequestException;
import com.gafarov.bastion.exception.ConflictDataException;
import com.gafarov.bastion.repository.UserRepository;
import com.gafarov.bastion.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addNewUser(User user) {
        try {
            User savedUser = userRepository.save(user);
            return savedUser;
        } catch (DataIntegrityViolationException exception) {
            throw new ConflictDataException(
                    String.format("email %s уже используется", user.getEmail()),
                    exception
            );
        }
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return user;
    }
    @Override
    public User findUserById(Integer userId){
        return userRepository.findById(userId).orElseThrow();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void changeUserStatus(Integer userId, Activity userStatus) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) throw new BadRequestException("no user with this id");
        User u = user.get();
        u.setActivity(userStatus);
        userRepository.save(u);
    }
    public void changeUserRole(Integer userId, Role role) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) throw new BadRequestException("no user with this id");
        User u = user.get();
        u.setRole(role);
        userRepository.save(u);
    }

    public void updateActivity(User user, Activity activity) {
        user.setActivity(activity);
        userRepository.save(user);
    }

}
