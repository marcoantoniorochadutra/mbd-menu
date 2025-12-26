package com.mbd.core.adapter.handler;

import com.mbd.core.adapter.response.RuntimeExceptionResponse;
import com.mbd.core.annotation.BadRequestException;
import com.mbd.core.annotation.InternalErrorException;
import com.mbd.core.annotation.NotFoundException;
import com.mbd.core.utils.MessageTranslateHelper;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.util.Objects.nonNull;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class RuntimeExceptionHandler {

    private final MessageTranslateHelper messageTranslateHelper;

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    ResponseEntity<RuntimeExceptionResponse> exceptionHandler(RuntimeException ex) {
        log.debug("{} - {}", this.getClass().getSimpleName(), ex.getMessage(), ex);

        var badRequestAnnotation = ex.getClass().getAnnotation(BadRequestException.class);
        if (nonNull(badRequestAnnotation)) {
            return this.buildErrorResponse(
                    badRequestAnnotation.exception(),
                    HttpStatus.BAD_REQUEST
            );
        }

        var notFoundAnnotation = ex.getClass().getAnnotation(NotFoundException.class);
        if (nonNull(notFoundAnnotation)) {
            return this.buildErrorResponse(
                    notFoundAnnotation.exception(),
                    HttpStatus.NOT_FOUND
            );
        }

        var internalErrorAnnotation = ex.getClass().getAnnotation(InternalErrorException.class);
        if (nonNull(internalErrorAnnotation)) {
            return this.buildErrorResponse(
                    internalErrorAnnotation.exception(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        return this.buildErrorResponse(
                RuntimeException.class.getSimpleName(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private ResponseEntity<RuntimeExceptionResponse> buildErrorResponse(String exceptionName, HttpStatus status) {
        var exceptionMessage = this.messageTranslateHelper.getErrorMessage(exceptionName);
        return ResponseEntity.status(status)
                .body(RuntimeExceptionResponse.builder()
                        .code(status.value())
                        .message(exceptionMessage)
                        .build());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    ResponseEntity<RuntimeExceptionResponse> exceptionHandler(Exception ex) {
        log.debug("Exception: {}", ex.getMessage(), ex);
        var exceptionMessage = this.messageTranslateHelper.getErrorMessage(RuntimeException.class.getSimpleName());
        return defaultError(exceptionMessage);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ApiResponse(responseCode = "400", description = "Bad Request")
    ResponseEntity<RuntimeExceptionResponse> exceptionHandler(HttpMessageNotReadableException ex) {
        log.debug("{} - {}", this.getClass().getSimpleName(), ex.getMessage(), ex);
        if (nonNull(ex.getMessage()) && ex.getMessage().contains("Required request body is missing")) {
            return ResponseEntity.badRequest()
                    .body(RuntimeExceptionResponse.builder()
                            .code(HttpStatus.BAD_REQUEST.value())
                            .message("Envia o body capeta")
                            .build());

        }
        var exceptionMessage = this.messageTranslateHelper.getErrorMessage(RuntimeException.class.getSimpleName());
        return defaultError(exceptionMessage);
    }

    public static ResponseEntity<RuntimeExceptionResponse> defaultError(String error) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                RuntimeExceptionResponse.builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(error)
                        .build());
    }

}
