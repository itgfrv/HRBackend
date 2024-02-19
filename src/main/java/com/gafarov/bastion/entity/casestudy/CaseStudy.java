package com.gafarov.bastion.entity.casestudy;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CaseStudy {
    @Id
    private Integer id;
    private String name;
    private String description;
}

