package com.gafarov.bastion.model;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

@Data
public class CommentRequestDto {
    private Integer userId;
    private String content;
}
