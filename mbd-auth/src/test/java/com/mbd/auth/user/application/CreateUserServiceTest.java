package com.mbd.auth.user.application;


import com.mbd.auth.password.usecase.PasswordEncoderUseCase;
import com.mbd.auth.user.domain.User;
import com.mbd.auth.user.domain.UserDomainRepository;
import com.mbd.auth.user.domain.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.mbd.auth.user.UserTestFactory.createUserCommand;
import static com.mbd.auth.user.UserTestFactory.interceptarSave;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("UnitTest - CreateUserServiceTest")
class CreateUserServiceTest {

    @Mock
    private UserDomainRepository userRepository;

    @Mock
    private PasswordEncoderUseCase passwordEncryptService;

    @InjectMocks
    private CreateUserService createUserService;


    @Test
    @DisplayName("Deve criar usuário com sucesso")
    void shouldCreateUserSuccessfully() {
        var command = createUserCommand();

        when(this.passwordEncryptService.encodePassword(command.password())).thenReturn("SaltedPassword");

        UserId result = this.createUserService.execute(command);

        User savedUser = interceptarSave(this.userRepository);

        assertEquals(result.id(), savedUser.getId().id());
        assertEquals(command.email(), savedUser.getEmail());
        assertEquals(command.name(), savedUser.getName());
    }

}
