package com.gafarov.bastion.entity.casestudy;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class File {
    @Id
    private UUID id;
    private String fileName;
}
