package com.horsepower.controller.user;

import com.horsepower.dto.user.UserSignupRequestDto;
import com.horsepower.dto.user.UserLoginRequestDto;
import com.horsepower.dto.user.UserResponseDto;
import com.horsepower.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ✅ 회원가입
    @PostMapping("/signup")
    public UserResponseDto signup(@RequestBody @Valid UserSignupRequestDto dto) {
        log.info("Received signup request for email: {}", dto.getEmail());
        return userService.signup(dto);
    }

    // ✅ 로그인
    @PostMapping("/login")
    public UserResponseDto login(@RequestBody @Valid UserLoginRequestDto dto) {
        log.info("Received login request for email: {}", dto.getEmail());
        return userService.login(dto);
    }
}

