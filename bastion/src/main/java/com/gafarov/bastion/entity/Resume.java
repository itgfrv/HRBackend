package com.gafarov.bastion.entity;

import com.gafarov.bastion.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String phoneNumber;
    private String email;
    private String militaryDuty;
    private String education;
    private String metroStation;
    private String goodQualities;
    private String badQualities;
    private String badHabits;
    private String reasonsForWorking;
    private String goodJobQualities;
    private String busyness;
    private String resumeSrc;
}