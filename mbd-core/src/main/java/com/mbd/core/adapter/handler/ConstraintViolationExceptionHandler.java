package com.mbd.core.adapter.handler;

import com.mbd.core.adapter.response.EntityValidationExceptionResponse;
import com.mbd.core.adapter.response.ValidationErrorResponse;
import com.mbd.core.model.ControllerBase;
import com.mbd.core.utils.MessageTranslateHelper;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Comparator;
import java.util.List;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class ConstraintViolationExceptionHandler implements ControllerBase {

    private final MessageTranslateHelper messageTranslateHelper;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ApiResponse(responseCode = "400", description = "Bad Request - Validation errors occurred")
    ResponseEntity<EntityValidationExceptionResponse> exceptionHandler(ConstraintViolationException ex) {
        log.debug("{} - {}", this.getClass().getSimpleName(), ex.getMessage(), ex);
        String message = this.messageTranslateHelper.getErrorMessage(ex);

        var entity = EntityValidationExceptionResponse.builder()
                .message(message)
                .errors(this.buildValidationErrors(ex))
                .build();

        return this.badRequestResponse(entity);
    }

    private List<ValidationErrorResponse> buildValidationErrors(ConstraintViolationException e) {
        return e.getConstraintViolations().stream().map(violation ->
                        ValidationErrorResponse.builder()
                                .field(this.extractFieldName(violation))
                                .code(violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName())
                                .message(violation.getMessage())
                                .build())
                .sorted(Comparator.comparing(ValidationErrorResponse::getField))
                .toList();
    }

    private String extractFieldName(ConstraintViolation<?> violation) {
        String propertyPath = violation.getPropertyPath().toString();
        String[] parts = propertyPath.split("\\.");
        return parts[parts.length - 1];
    }
}

