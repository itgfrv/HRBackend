package com.gafarov.bastion.repository;

import com.gafarov.bastion.entity.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, UUID> {
}
