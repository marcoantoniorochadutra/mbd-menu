package com.mbd.auth.user.domain;

import com.mbd.auth.sk.domain.enums.UserRoles;
import com.mbd.auth.user.domain.enums.UserStatus;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.List;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", indexes = {
        @Index(name = "uk_users_email", columnList = "email", unique = true)
})
public class User {

    @Id
    @Column(columnDefinition = "uuid")
    private UserId id;

    @NotNull
    @Column(unique = true, name = "email")
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String password;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private UserStatus status;

    @NotNull
    @Column(name = "user_role")
    @Enumerated(EnumType.ORDINAL)
    @ElementCollection(targetClass = UserRoles.class)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    private List<UserRoles> userRoles;

    @CreatedDate
    private Instant createdAt;

    private Instant lastLoginAt;

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public User(UserBuilder userBuilder) {
        this.id = userBuilder.id;
        this.email = userBuilder.email;
        this.name = userBuilder.name;
        this.password = userBuilder.password;
        this.status = userBuilder.status;
        this.userRoles = userBuilder.userRoles;
    }
}
