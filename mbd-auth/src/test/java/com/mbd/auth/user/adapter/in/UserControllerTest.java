package com.mbd.auth.user.adapter.in;

import com.mbd.auth.user.adapter.in.api.UserController;
import com.mbd.auth.user.domain.UserId;
import com.mbd.auth.user.exception.UserNotFoundException;
import com.mbd.auth.user.usecase.CreateUserUseCase;
import com.mbd.auth.user.usecase.CreateUserUseCase.CreateUserCommand;
import com.mbd.auth.user.usecase.GetByIdUserUseCase;
import com.mbd.auth.user.usecase.dto.UserDto;
import com.mbd.utils.ControllerTestUtils;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@DisplayName("Controller Test - User")
class UserControllerTest extends ControllerTestUtils {

    @MockitoBean
    private CreateUserUseCase createUserUseCase;

    @MockitoBean
    private GetByIdUserUseCase getByIdUserUseCase;

    @Test
    @DisplayName("Deve criar um usuário com sucesso")
    void shouldCreateUser() throws Exception {

        var cmd = CreateUserCommand.builder()
                .name("Test User")
                .email("testuser@email.com")
                .password("pass")
                .build();

        var userId = UserId.generate();
        when(this.createUserUseCase.execute(cmd)).thenReturn(userId);

        this.executePost(UserController.ENDPOINT, cmd)
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString(UserController.ENDPOINT + userId)));
    }


    @Test
    @DisplayName("Não deve criar um usuário, body inválido")
    void shouldNotCreateUserInvalidBody() throws Exception {

        var cmd = CreateUserCommand.builder().build();

        var userId = UserId.generate();
        when(this.createUserUseCase.execute(cmd)).thenReturn(userId);


        this.executePost(UserController.ENDPOINT, cmd)
                .andExpect(status().isBadRequest())
                .andExpect(matchValidation(NotBlank.class, "name"))
                .andExpect(matchValidation(NotBlank.class, "email"))
                .andExpect(matchValidation(NotBlank.class, "password"))
                .andExpect(jsonPath("$.message").value("The provided data is invalid. Please check the errors and try again."));
    }

    @Test
    @DisplayName("Não deve retornar um usuário quando o ID não existir")
    void shouldReturnNotFoundWhenUserWithIdDoesNotExist() throws Exception {

        var nonExistentId = UserId.generate();
        when(this.getByIdUserUseCase.execute(nonExistentId)).thenThrow(UserNotFoundException.class);

        this.executeGet(UserController.ENDPOINT + nonExistentId)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("We couldn't find any user registered with the provided email. Please check if the address is correct or sign up to create an account."));

    }

    @Test
    @DisplayName("Deve retornar um usuário com sucesso pelo ID")
    void shouldReturnUserById() throws Exception {

        var userId = UserId.generate();
        var userDto = UserDto.of(userId, "Test User", "testuser@email.com");
        when(this.getByIdUserUseCase.execute(userId)).thenReturn(userDto);

        this.executeGet(UserController.ENDPOINT + userId)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDto.id().toString()))
                .andExpect(jsonPath("$.name").value(userDto.name()))
                .andExpect(jsonPath("$.email").value(userDto.email()));
    }

}
