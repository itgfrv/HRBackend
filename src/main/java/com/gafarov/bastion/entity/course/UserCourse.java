package com.gafarov.bastion.entity.course;

import com.gafarov.bastion.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCourse {
    @EmbeddedId
    private UserCourseId id;
    @Enumerated(EnumType.STRING)
    private UserCourseStatus status;
}
