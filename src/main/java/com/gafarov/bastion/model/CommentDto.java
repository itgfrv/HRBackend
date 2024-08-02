package com.gafarov.bastion.model;

import com.gafarov.bastion.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class CommentDto {
    private String content;
    private String adminName;
    private String adminLastname;
    private String adminEmail;
}
