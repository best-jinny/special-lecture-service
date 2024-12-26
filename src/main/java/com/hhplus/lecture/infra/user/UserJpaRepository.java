package com.hhplus.lecture.infra.user;

import com.hhplus.lecture.domain.user.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserAccount, Long> {
}
