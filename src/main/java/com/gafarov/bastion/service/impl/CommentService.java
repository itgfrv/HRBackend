package com.gafarov.bastion.service.impl;

import com.gafarov.bastion.entity.Comment;
import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.model.CommentDto;
import com.gafarov.bastion.repository.CommentRepository;
import com.gafarov.bastion.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentDto createComment(Integer userId, User admin, String content) {
        Comment comment = new Comment();
        User user = userRepository.findById(userId).orElseThrow();
        comment.setUser(user);
        comment.setAdmin(admin);
        comment.setContent(content);
        comment = commentRepository.save(comment);
        CommentDto commentDto = new CommentDto();
        commentDto.setContent(comment.getContent());
        commentDto.setAdminEmail(comment.getAdmin().getEmail());
        commentDto.setAdminName(comment.getAdmin().getFirstname());
        commentDto.setAdminLastname(comment.getAdmin().getLastname());
        return commentDto;
    }

    public List<CommentDto> getComments(Integer userId) {
        return commentRepository.findAllByUserId(userId).stream().map(comment -> {
            CommentDto commentDto = new CommentDto();
            commentDto.setContent(comment.getContent());
            commentDto.setAdminEmail(comment.getAdmin().getEmail());
            commentDto.setAdminName(comment.getAdmin().getFirstname());
            commentDto.setAdminLastname(comment.getAdmin().getLastname());
            return commentDto;
        }).toList();
    }

}
