package com.mbd.auth.user.domain;

import com.mbd.auth.user.exception.UserNotFoundException;
import com.mbd.auth.user.usecase.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.mbd.auth.user.UserTestFactory.createUserDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("UnitTest - UserDomainRepositoryTest")
class UserDomainRepositoryTest {

    private final UserDomainRepository userRepository = mock(UserDomainRepository.class);

    @Test
    @DisplayName("Deve retornar usuário quando encontrado por ID")
    void shouldReturnUserWhenFoundById() {
        var userId = UserId.generate();
        var expectedUserDto = createUserDto();

        when(this.userRepository.findById(userId)).thenReturn(expectedUserDto);
        when(this.userRepository.findByIdOrThrowNotFound(userId)).thenCallRealMethod();

        UserDto result = this.userRepository.findByIdOrThrowNotFound(userId);

        assertEquals(expectedUserDto.id(), result.id());
        assertEquals(expectedUserDto.name(), result.name());
        assertEquals(expectedUserDto.email(), result.email());
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não for encontrado por ID")
    void shouldThrowExceptionWhenUserNotFoundById() {
        var userId = UserId.generate();

        when(this.userRepository.findById(userId)).thenReturn(null);
        when(this.userRepository.findByIdOrThrowNotFound(userId)).thenCallRealMethod();

        assertThrows(UserNotFoundException.class,
                () -> this.userRepository.findByIdOrThrowNotFound(userId));
    }
}

