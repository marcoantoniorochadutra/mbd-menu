package com.mbd.auth.user.application;

import com.mbd.auth.password.usecase.PasswordEncoderUseCase;
import com.mbd.auth.user.domain.User;
import com.mbd.auth.user.domain.UserBuilder;
import com.mbd.auth.user.domain.UserDomainRepository;
import com.mbd.auth.user.domain.UserId;
import com.mbd.auth.user.usecase.CreateUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateUserService implements CreateUserUseCase {

    private final UserDomainRepository userRepository;
    private final PasswordEncoderUseCase passwordEncryptService;

    @Override
    public UserId execute(CreateUserCommand cmd) {
        User user = UserBuilder.from(cmd)
                .passwordEncoder(this.passwordEncryptService)
                .build();

        this.userRepository.save(user);

        return user.getId();
    }
}
