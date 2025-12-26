package com.mbd.auth.user.domain;

import com.mbd.auth.user.exception.UserNotFoundException;
import com.mbd.auth.user.usecase.dto.UserDto;


import static java.util.Objects.isNull;

public interface UserDomainRepository {

    User save(User user);

    UserDto findById(UserId id);

    default UserDto findByIdOrThrowNotFound(UserId id) {
        UserDto user = this.findById(id);

        if (isNull(user))
            throw new UserNotFoundException();

        return user;
    }
}
