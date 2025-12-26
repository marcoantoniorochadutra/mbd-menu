package com.mbd.auth.user;

import com.mbd.auth.user.domain.User;
import com.mbd.auth.user.domain.UserDomainRepository;
import com.mbd.auth.user.domain.UserId;
import com.mbd.auth.user.usecase.CreateUserUseCase.CreateUserCommand;
import com.mbd.auth.user.usecase.dto.UserDto;
import org.mockito.ArgumentCaptor;

import static com.mbd.utils.TestUtils.FAKER;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserTestFactory {

    public static User interceptarSave(UserDomainRepository userRepository) {
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());
        return userCaptor.getValue();
    }

    public static CreateUserCommand createUserCommand() {
        return CreateUserCommand.of(
                FAKER.name().fullName(),
                FAKER.internet().emailAddress(),
                FAKER.internet().password());
    }

    public static UserDto createUserDto() {
        return UserDto.of(
                UserId.generate(),
                FAKER.name().fullName(),
                FAKER.internet().emailAddress());
    }
}
