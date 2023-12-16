package com.gafarov.bastion.entity.course;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    private Integer id;
    private String name;
    private String description;
    private Boolean isOpened;
    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    private List<Task> tasks;
}
