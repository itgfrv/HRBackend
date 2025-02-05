package com.gafarov.bastion.entity.crossCheck;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cross_check_rule")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrossCheckRule {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cross_check_rule_seq")
    @SequenceGenerator(name = "cross_check_rule_seq", sequenceName = "cross_check_rule_id_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cross_check_id")
    private CrossCheck crossCheck;

    @Column(length = 255)
    private String evaluatorRole;

    @Column(length = 255)
    private String evaluatedRole;
}