package com.progettosweng.application.repository;

import com.progettosweng.application.entity.AnonymousUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnonymousUserRepository extends JpaRepository<AnonymousUser, Integer> {
}
