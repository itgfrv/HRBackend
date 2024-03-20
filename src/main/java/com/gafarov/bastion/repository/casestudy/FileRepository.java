package com.gafarov.bastion.repository.casestudy;

import com.gafarov.bastion.entity.casestudy.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Integer> {
}
