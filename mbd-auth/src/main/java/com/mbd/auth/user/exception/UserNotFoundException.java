package com.mbd.auth.user.exception;

import com.mbd.core.annotation.NotFoundException;

import java.io.Serial;

@NotFoundException(exception = "UserNotFoundException")
public class UserNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 3911382655820290967L;

}
