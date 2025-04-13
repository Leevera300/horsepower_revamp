package com.horsepower.repository.user;

import com.horsepower.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일로 사용자 조회 (로그인용)
    Optional<User> findByEmail(String email);

    // 이메일 중복 확인 (회원가입용)
    boolean existsByEmail(String email);
}

