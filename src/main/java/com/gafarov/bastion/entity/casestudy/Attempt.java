package com.gafarov.bastion.entity.casestudy;

import com.gafarov.bastion.entity.user.User;
import jakarta.persistence.*;

@Entity
public class Attempt {
    @Id
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    private CaseStudy caseStudy;
}
