package com.gafarov.bastion.entity.casestudy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "criteria")
public class Criteria {

    @Id
    private Integer id;

    @Column(name = "criteria", nullable = false)
    private String criteria;
}