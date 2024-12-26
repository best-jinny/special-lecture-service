package com.hhplus.lecture.domain.user;

import java.util.Optional;

public interface UserRepository {
    Optional<UserAccount> findById(Long id);
    void save(UserAccount userAccount);
}
