package com.mbd.auth.user.usecase;

import com.mbd.auth.user.domain.UserId;
import com.mbd.auth.user.usecase.dto.UserDto;
import com.mbd.core.model.UseCase;

public interface GetByIdUserUseCase extends UseCase<UserId, UserDto> {


}
