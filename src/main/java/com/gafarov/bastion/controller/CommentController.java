package com.gafarov.bastion.controller;

import com.gafarov.bastion.entity.Comment;
import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.model.CommentDto;
import com.gafarov.bastion.model.CommentRequestDto;
import com.gafarov.bastion.service.impl.CommentService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/{userId}")
    public List<CommentDto> getUserComments(@PathVariable Integer userId, @AuthenticationPrincipal User admin) {
        return commentService.getComments(userId);
    }

    @PostMapping
    public CommentDto addComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal User admin) {
        System.out.println(admin);
        return commentService.createComment(requestDto.getUserId(), admin, requestDto.getContent());
    }
}
