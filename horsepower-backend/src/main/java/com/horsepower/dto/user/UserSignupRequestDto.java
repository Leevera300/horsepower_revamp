package com.horsepower.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserSignupRequestDto {

    @NotBlank(message = "{user.firstName.notblank}")
    private String firstName;

    @NotBlank(message = "{user.lastName.notblank}")
    private String lastName;

    @NotNull(message = "{user.dob.notnull}")
    @Past(message = "{user.dob.past}")
    private LocalDate dateOfBirth;

    @Email(message = "{user.email.invalid}")
    @NotBlank(message = "{user.email.notblank}")
    private String email;

    @NotBlank(message = "{user.password.notblank}")
    private String password;
}

