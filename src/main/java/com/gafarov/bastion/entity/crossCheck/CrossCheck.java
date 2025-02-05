package com.gafarov.bastion.entity.crossCheck;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "cross_check")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrossCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cross_check_seq")
    @SequenceGenerator(name = "cross_check_seq", sequenceName = "cross_check_id_seq", allocationSize = 1)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "crossCheck", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CrossCheckSession> sessions;

    @OneToMany(mappedBy = "crossCheck", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CrossCheckQuestion> questions;

    @OneToMany(mappedBy = "crossCheck", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CrossCheckRule> rules;
}