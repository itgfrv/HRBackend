package com.gafarov.bastion.service.impl;

import com.gafarov.bastion.entity.user.Activity;
import com.gafarov.bastion.entity.user.Role;
import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.model.FormDto;
import com.gafarov.bastion.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FormServiceImpl {
    private final UserRepository userRepository;

    public List<FormDto> getPaginationForm(Pageable pageable, Activity filterParam) {
        Page<User> page;
        if (filterParam == null) page = userRepository.findAllByRole(pageable, Role.USER);
        else page = userRepository.findAllByActivityAndRole(pageable, filterParam, Role.USER);
        List<User> users = page.getContent();
        return users.stream().map(this::mapUserToForm).toList();
    }
    private FormDto mapUserToForm(User user){
        return new FormDto(user.getId(), user.getFirstname(), user.getLastname(), user.getUserStatus(), user.getActivity());
    }

}
