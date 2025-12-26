package com.mbd.auth.user.application;

import com.mbd.auth.user.domain.UserDomainRepository;
import com.mbd.auth.user.domain.UserId;
import com.mbd.auth.user.exception.UserNotFoundException;
import com.mbd.auth.user.usecase.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.mbd.auth.user.UserTestFactory.createUserDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("UnitTest - GetByIdUserServiceTest")
class GetByIdUserServiceTest {

    @Mock
    private UserDomainRepository userRepository;

    @InjectMocks
    private GetByIdUserService getByIdUserService;

    @Test
    @DisplayName("Deve retornar usuário por ID com sucesso")
    void shouldReturnUserById() {
        var userDto = createUserDto();
        var userId = userDto.id();

        when(this.userRepository.findByIdOrThrowNotFound(userId)).thenReturn(userDto);

        UserDto result = this.getByIdUserService.execute(userId);

        assertEquals(userId, result.id());
        assertEquals(userDto.name(), result.name());
        assertEquals(userDto.email(), result.email());
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não for encontrado")
    void shouldThrowExceptionWhenUserNotFound() {
        var userId = UserId.generate();

        when(this.userRepository.findByIdOrThrowNotFound(userId)).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class,
                () -> this.getByIdUserService.execute(userId));
    }
}
