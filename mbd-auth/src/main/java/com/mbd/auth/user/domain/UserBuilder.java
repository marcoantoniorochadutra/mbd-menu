package com.mbd.auth.user.domain;

import com.mbd.auth.sk.domain.enums.UserRoles;
import com.mbd.auth.user.domain.enums.UserStatus;
import com.mbd.auth.user.usecase.CreateUserUseCase.CreateUserCommand;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
public class UserBuilder {

    protected UserId id;
    protected String email;
    protected String name;
    protected String password;
    protected UserStatus status;
    protected List<UserRoles> userRoles = new ArrayList<>();
    protected Instant createdAt;
    protected Instant lastLoginAt;

    public static UserBuilder from(CreateUserCommand command) {
        return new UserBuilder()
                .id(UserId.generate())
                .email(command.email())
                .name(command.name())
                .password(command.password())
                .status(UserStatus.ACTIVE);
    }

    public UserBuilder id(UserId id) {
        this.id = id;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder name(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder status(UserStatus status) {
        this.status = status;
        return this;
    }

    public User build() {
        this.userRoles.add(UserRoles.USER);
        return new User(this);
    }

}
