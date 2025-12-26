package com.mbd.auth.user.usecase;

import com.mbd.auth.user.domain.UserId;
import com.mbd.auth.user.usecase.CreateUserUseCase.CreateUserCommand;
import com.mbd.core.model.UseCase;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public interface CreateUserUseCase extends UseCase<CreateUserCommand, UserId> {

    @Builder
    record CreateUserCommand(

        @NotBlank(message = "{UserCommand.name.NotBlank}")
        @Size(min = 1, message = "{UserCommand.name.Size.min}")
        @Size(max = 250, message = "{UserCommand.name.Size.max}")
        @Schema(example = "John Doe", requiredMode = RequiredMode.REQUIRED)
        String name,

        @NotBlank(message = "{UserCommand.password.NotBlank}")
        @Schema(example = "johnDoe123$", requiredMode = RequiredMode.REQUIRED)
        String password,

        @NotBlank(message = "{UserCommand.email.NotBlank}")
        @Email(message = "{UserCommand.email.Email}", regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        @Schema(example = "johnDoe@email.com", requiredMode = RequiredMode.REQUIRED)
        String email) {

        public static CreateUserCommand of(String name, String password, String email) {
            return CreateUserCommand.builder()
                    .name(name)
                    .password(password)
                    .email(email)
                    .build();
        }
    }

}
