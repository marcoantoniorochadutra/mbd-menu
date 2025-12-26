package com.mbd.auth.user.application;

import com.mbd.auth.user.domain.UserDomainRepository;
import com.mbd.auth.user.domain.UserId;
import com.mbd.auth.user.usecase.GetByIdUserUseCase;
import com.mbd.auth.user.usecase.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GetByIdUserService implements GetByIdUserUseCase {

    private final UserDomainRepository userRepository;

    @Override
    public UserDto execute(UserId cmd) {
        return this.userRepository.findByIdOrThrowNotFound(cmd);
    }
}
