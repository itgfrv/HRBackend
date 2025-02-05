package com.gafarov.bastion.model.crossCheck;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttemptDTO {
    private int id;
    private List<UserInfo> evaluated;

}
