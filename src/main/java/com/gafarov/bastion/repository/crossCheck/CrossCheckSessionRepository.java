package com.gafarov.bastion.repository.crossCheck;

import com.gafarov.bastion.entity.crossCheck.CrossCheckSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrossCheckSessionRepository extends JpaRepository<CrossCheckSession, Integer> {
    List<CrossCheckSession> findByCrossCheckId(Integer crossCheckId);
}
