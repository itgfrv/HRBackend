package com.gafarov.bastion.entity.crossCheck;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cross_check_session")
@Data()
@NoArgsConstructor
@AllArgsConstructor
public class CrossCheckSession {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cross_check_session_seq")
    @SequenceGenerator(name = "cross_check_session_seq", sequenceName = "cross_check_session_id_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cross_check_id")
    private CrossCheck crossCheck;

    private LocalDateTime date;

    private String description;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CrossCheckAttempt> attempts;
}