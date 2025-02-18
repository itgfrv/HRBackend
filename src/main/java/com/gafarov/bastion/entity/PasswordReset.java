package com.gafarov.bastion.entity;

import com.gafarov.bastion.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "password_reset")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordReset {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "is_used", nullable = false)
    private Boolean isUsed;
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;
}