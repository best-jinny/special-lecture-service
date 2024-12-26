package com.hhplus.lecture.infra.user;

import com.hhplus.lecture.domain.user.UserAccount;
import com.hhplus.lecture.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<UserAccount> findById(Long id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public void save(UserAccount userAccount) {
        userJpaRepository.save(userAccount);
    }

}
