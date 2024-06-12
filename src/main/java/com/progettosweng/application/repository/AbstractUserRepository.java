package com.progettosweng.application.repository;

import com.progettosweng.application.entity.AbstractUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository per l'entità astratta AbstractUser.
 */
@Repository
public interface AbstractUserRepository extends JpaRepository<AbstractUser, Integer> {
}
