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
    private Integer id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "military_duty")
    private String militaryDuty;
    @Column(name = "education")
    private String education;
    @Column(name = "metro_station")
    private String metroStation;
    @Column(name = "good_qualities")
    private String goodQualities;
    @Column(name = "bad_qualities")
    private String badQualities;
    @Column(name = "bad_habits")
    private String badHabits;
    @Column(name = "reasons_for_working")
    private String reasonsForWorking;
    @Column(name = "good_job_qualities")
    private String goodJobQualities;
    @Column(name = "busyness")
    private String busyness;
    @Column(name = "resume_src")
    private String resumeSrc;
}