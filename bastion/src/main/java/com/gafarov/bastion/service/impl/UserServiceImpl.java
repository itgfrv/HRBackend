package com.gafarov.bastion.service.impl;

import com.gafarov.bastion.entity.User;
import com.gafarov.bastion.exception.ConflictDataException;
import com.gafarov.bastion.exception.UserNotFoundException;
import com.gafarov.bastion.repository.UserRepository;
import com.gafarov.bastion.service.UserService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

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
}
