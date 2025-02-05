package com.gafarov.bastion.repository;

import com.gafarov.bastion.entity.user.Activity;
import com.gafarov.bastion.entity.user.Role;
import com.gafarov.bastion.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Page<User> findAllByActivityAndRole(Pageable pageable, Activity activity, Role role);

    Page<User> findAllByRole(Pageable pageable, Role role);
    List<User> findAllByRole(Role role);
}
