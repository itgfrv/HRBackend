package com.gafarov.bastion.service.impl;

import com.gafarov.bastion.entity.User;
import com.gafarov.bastion.exception.ConflictDataException;
import com.gafarov.bastion.exception.UserNotFoundException;
import com.gafarov.bastion.repository.UserRepository;
import com.gafarov.bastion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void addNewUser(User user) {
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new ConflictDataException(
                    String.format("Email %s is already in use", user.getEmail()),
                    exception
            );
        }
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return user;
    }
}
