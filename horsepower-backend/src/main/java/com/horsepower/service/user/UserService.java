package com.horsepower.service.user;

import com.horsepower.dto.user.UserSignupRequestDto;
import com.horsepower.dto.user.UserLoginRequestDto;
import com.horsepower.dto.user.UserResponseDto;
import com.horsepower.entity.user.User;
import com.horsepower.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ✅ 회원가입
    public UserResponseDto signup(UserSignupRequestDto dto) {
        log.info("Attempting to signup user with email: {}", dto.getEmail());

        if (userRepository.existsByEmail(dto.getEmail())) {
            log.warn("Signup failed - Email already exists: {}", dto.getEmail());
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User savedUser = userRepository.save(user);
        log.info("User successfully signed up with ID: {}", savedUser.getId());

        return UserResponseDto.of("회원가입이 완료되었습니다.", savedUser.getId());
    }

    // ✅ 로그인
    public UserResponseDto login(UserLoginRequestDto dto) {
        log.info("Attempting login for user: {}", dto.getEmail());

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> {
                    log.warn("Login failed - User not found: {}", dto.getEmail());
                    return new IllegalArgumentException("이메일 또는 비밀번호가 틀렸습니다.");
                });

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            log.warn("Login failed - Invalid password for user: {}", dto.getEmail());
            throw new IllegalArgumentException("이메일 또는 비밀번호가 틀렸습니다.");
        }

        log.info("Login successful for user: {}", dto.getEmail());
        return UserResponseDto.of("로그인 성공", user.getId());
    }
}

