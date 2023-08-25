package com.gafarov.bastion.controller;

import com.gafarov.bastion.entity.quiz.Quiz;
import com.gafarov.bastion.mapper.QuizMapper;
import com.gafarov.bastion.model.QuizDto;
import com.gafarov.bastion.repository.QuizRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class QuizController {
    private final QuizRepository quizRepository;
    @GetMapping("/{id}")
    public QuizDto getQuiz(@PathVariable Integer id){
        return QuizMapper.INSTANCE.mapQuizToQuizDto( quizRepository.findById(id).orElseThrow());
    }
}
