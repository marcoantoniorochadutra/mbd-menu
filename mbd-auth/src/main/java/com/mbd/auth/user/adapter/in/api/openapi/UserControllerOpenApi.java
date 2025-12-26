package com.mbd.auth.user.adapter.in.api.openapi;

import com.mbd.auth.user.domain.UserId;
import com.mbd.auth.user.usecase.CreateUserUseCase.CreateUserCommand;
import com.mbd.auth.user.usecase.dto.UserDto;
import com.mbd.core.adapter.response.EntityValidationExceptionResponse;
import com.mbd.core.adapter.response.RuntimeExceptionResponse;
import com.mbd.core.model.ControllerBase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserControllerOpenApi extends ControllerBase {

    @Operation(summary = "Creates a new user", method = "POST",
               security = @SecurityRequirement(name = "None"),
               description = "This endpoint is responsible for creating new users.",
               parameters = {
                    @Parameter(in = ParameterIn.HEADER, name = "Accept-Language", schema = @Schema(type = "string"),
                            examples = {
                                    @ExampleObject(value = "pt-BR", name = "Português"),
                                    @ExampleObject(value = "en-US", name = "English")
                            })
               },
               responses = {
                    @ApiResponse(responseCode = "201", description = "User created",
                                 content = @Content(mediaType = "application/json",
                                 schema = @Schema())),
                    @ApiResponse(responseCode = "400", description = "Invalid user data",
                                 content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = EntityValidationExceptionResponse.class))),
               }
    )
    ResponseEntity<UserId> createUser(@Validated @RequestBody CreateUserCommand cmd);

    @Operation(summary = "Find user by ID", method = "GET",
               security = @SecurityRequirement(name = "None"),
               description = "This endpoint is responsible for retrieving a user by their ID.",
               parameters = {
                    @Parameter(in = ParameterIn.HEADER, name = "Accept-Language", schema = @Schema(type = "string"),
                            examples = {
                                    @ExampleObject(value = "pt-BR", name = "Português"),
                                    @ExampleObject(value = "en-US", name = "English")
                            }),
                       @Parameter(in = ParameterIn.PATH, name = "id", description = "The ID of the user to retrieve",
                            required = true, schema = @Schema(type = "string", format = "uuid"),
                            examples = {
                                    @ExampleObject(value = "123e4567-e89b-12d3-a456-426614174000", name = "Sample User ID")
                    })
               },
               responses = {
                    @ApiResponse(responseCode = "200", description = "User found"),
                    @ApiResponse(responseCode = "404", description = "User not found",
                                 content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = RuntimeExceptionResponse.class)))
               }
    )
    ResponseEntity<UserDto> findByIdUser(@PathVariable("id") UserId id);

}
