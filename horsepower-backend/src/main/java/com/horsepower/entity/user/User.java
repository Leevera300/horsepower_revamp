package com.horsepower.entity.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class User {

    public enum Authority {
        USER, ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{user.firstName.notblank}")
    @Column(name = "first_name", length = 20)
    private String firstName;

    @NotBlank(message = "{user.lastName.notblank}")
    @Column(name = "last_name", length = 20)
    private String lastName;

    @NotNull(message = "{user.dob.notnull}")
    @Past(message = "{user.dob.past}")
    @Column(name = "dob")
    private LocalDate dateOfBirth;

    @Email(message = "{user.email.invalid}")
    @NotBlank(message = "{user.email.notblank}")
    @Column(length = 128, unique = true, nullable = false)
    private String email;

    @NotBlank(message = "{user.password.notblank}")
    @Column(length = 256, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 6)
    private Authority authority = Authority.USER;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

