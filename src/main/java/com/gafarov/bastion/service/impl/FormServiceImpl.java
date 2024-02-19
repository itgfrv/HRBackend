package com.gafarov.bastion.service.impl;

import com.gafarov.bastion.entity.user.Activity;
import com.gafarov.bastion.entity.user.Role;
import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.exception.BadRequestException;
import com.gafarov.bastion.model.FormDto;
import com.gafarov.bastion.model.FullFormDto;
import com.gafarov.bastion.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FormServiceImpl {
    private final UserRepository userRepository;
    private final ResumeServiceImpl resumeService;
    private final QuizServiceImpl quizService;

    public List<FormDto> getPaginationForm(Pageable pageable, Activity filterParam) {
        Page<User> page;
        if (filterParam == null) page = userRepository.findAllByRole(pageable, Role.USER);
        else page = userRepository.findAllByActivityAndRole(pageable, filterParam, Role.USER);
        List<User> users = page.getContent();
        return users.stream().map(this::mapUserToForm).toList();
    }

    private FormDto mapUserToForm(User user) {
        String activity = "";
        switch (user.getActivity()) {
            case REGISTERED -> activity += "Зарегистрировался";
            case RESUME -> activity += "Заполнил резюме/";
            case DEMO_TEST -> activity += "Заполнил резюме/Выполнил демо тест/";
            case WAITING_INTERVIEW -> activity += "Заполнил резюме/Выполнил демо тест/";
            case INTERVIEW -> activity += "Заполнил резюме/Выполнил демо тест/Выдан доступ к финальному тесту";
            case WAITING_RESULT -> activity += "Заполнил резюме/Выполнил демо тест/Выполнил финальный тест/";
        }
        return new FormDto(user.getId(), user.getFirstname(), user.getLastname(), user.getUserStatus(), activity);
    }

    public FullFormDto getUserInfo(Integer id) {
        FullFormDto fullForm = new FullFormDto();
        Optional<User> u = userRepository.findById(id);
        if (u.isEmpty()) throw new BadRequestException("no users with this id");
        fullForm.setUser(mapUserToForm(u.get()));
        fullForm.setResumeDto(resumeService.getResume(u.get()));
        fullForm.setQuizResult(quizService.getUserResult(id));
        return fullForm;
    }


}
