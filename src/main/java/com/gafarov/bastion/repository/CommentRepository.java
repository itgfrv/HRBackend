package com.gafarov.bastion.repository;

import com.gafarov.bastion.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByUserId(int userId);
}
