package com.mbd.auth.user.adapter.in.api;

import com.mbd.auth.user.domain.UserId;
import com.mbd.auth.user.adapter.in.api.openapi.UserControllerOpenApi;
import com.mbd.auth.user.usecase.CreateUserUseCase;
import com.mbd.auth.user.usecase.CreateUserUseCase.CreateUserCommand;
import com.mbd.auth.user.usecase.GetByIdUserUseCase;
import com.mbd.auth.user.usecase.dto.UserDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UserController.ENDPOINT)
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Operações relacionadas ao gerenciamento de usuários")
public class UserController implements UserControllerOpenApi {

    public static final String ENDPOINT = "/api/v1/user/";

    private final CreateUserUseCase createUserUseCase;
    private final GetByIdUserUseCase getByIdUserUseCase;

    @PostMapping
    public ResponseEntity<UserId> createUser(@Validated @RequestBody CreateUserCommand cmd) {
        UserId userId = this.createUserUseCase.execute(cmd);
        return this.createdResponse(ENDPOINT, userId);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDto> findByIdUser(@PathVariable("id") UserId id) {
        UserDto userDtoRec = this.getByIdUserUseCase.execute(id);
        return this.successResponse(userDtoRec);
    }


}
