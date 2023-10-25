package com.gafarov.bastion.entity.resume;

import com.gafarov.bastion.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resume_ans_seq")
    @SequenceGenerator(name = "resume_ans_seq", sequenceName = "resume_answer_id_seq", allocationSize = 1)
    private Integer id;
    private String answer;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private ResumeQuestion question;
}
