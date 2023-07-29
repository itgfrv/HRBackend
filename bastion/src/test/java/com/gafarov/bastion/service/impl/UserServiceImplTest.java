package com.gafarov.bastion.service.impl;

import com.gafarov.bastion.entity.User;
import com.gafarov.bastion.exception.ConflictDataException;
import com.gafarov.bastion.repository.UserRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    private UserServiceImpl userService;

    @BeforeEach
    void init() {
        this.userService = new UserServiceImpl(userRepository);
    }

    @Test
    void addNewUser_whenCorrectUser_thenWithoutException() {
        User user = User.builder().id(1)
                .firstname("alexander")
                .lastname("pushkin")
                .email("pushkin@mail.ru")
                .password("123")
                .build();
        when(userRepository.save(any(User.class))).thenReturn(user);
        assertDoesNotThrow(() -> userService.addNewUser(user));
    }

    @Test
    void addNewUser_whenEmailIsTaken_thenConflictDataException() {
        User user = User.builder().id(1)
                .firstname("alexander")
                .lastname("pushkin")
                .email("pushkin@mail.ru")
                .password("123")
                .build();

        when(userRepository.save(any(User.class))).thenThrow(new DataIntegrityViolationException(""));

        assertThrows(ConflictDataException.class, () -> userService.addNewUser(user));
    }

    @Test
    void addNewUser_whenEmptyFirstname_thenConstraintViolationException() {
        User user = User.builder().id(1)
                .firstname(null)
                .lastname("pushkin")
                .email("pushkin@mail.ru")
                .password("123")
                .build();

        when(userRepository.save(any(User.class))).thenThrow(new ConstraintViolationException(null));

        assertThrows(ConstraintViolationException.class, () -> userService.addNewUser(user));
    }

    @Test
    void addNewUser_whenEmptyLastname_thenConstraintViolationException() {
        User user = User.builder().id(1)
                .firstname("alex")
                .lastname(null)
                .email("pushkin@mail.ru")
                .password("123")
                .build();

        when(userRepository.save(any(User.class))).thenThrow(new ConstraintViolationException(null));

        assertThrows(ConstraintViolationException.class, () -> userService.addNewUser(user));
    }

    @Test
    void addNewUser_whenEmptyEmail_thenConstraintViolationException() {
        User user = User.builder().id(1)
                .firstname("alex")
                .lastname("pushkin")
                .email(null)
                .password("123")
                .build();

        when(userRepository.save(any(User.class))).thenThrow(new ConstraintViolationException(null));

        assertThrows(ConstraintViolationException.class, () -> userService.addNewUser(user));
    }

    @Test
    void addNewUser_whenIncorrectEmail_thenConstraintViolationException() {
        User user = User.builder().id(1)
                .firstname("alex")
                .lastname("pushkin")
                .email("pushkinmail.ru")
                .password("123")
                .build();

        when(userRepository.save(any(User.class))).thenThrow(new ConstraintViolationException(null));

        assertThrows(ConstraintViolationException.class, () -> userService.addNewUser(user));

    }

    @Test
    void addUser_whenEmptyPassword_thenConstraintViolationException() {
        User user = User.builder().id(1)
                .firstname("alex")
                .lastname("pushkin")
                .email("pushkin@mail.ru")
                .password(null)
                .build();

        when(userRepository.save(any(User.class))).thenThrow(new ConstraintViolationException(null));

        assertThrows(ConstraintViolationException.class, () -> userService.addNewUser(user));
    }

    @Test
    void findUserByEmail() {
        User user = User.builder().id(1)
                .firstname("alex")
                .lastname("pushkin")
                .email("pushkin@mail.ru")
                .password("123")
                .build();
        Optional<User> optionalUser = Optional.of(user);
        User expectedUser = User.builder().id(1)
                .firstname("alex")
                .lastname("pushkin")
                .email("pushkin@mail.ru")
                .password("123")
                .build();

        when(userRepository.findByEmail(any(String.class))).thenReturn(optionalUser);

        assertEquals(expectedUser,userService.findUserByEmail("pushkin@mail.ru"));
    }
}