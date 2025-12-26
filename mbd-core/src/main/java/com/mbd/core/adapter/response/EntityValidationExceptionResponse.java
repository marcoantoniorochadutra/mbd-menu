package com.mbd.core.adapter.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(Include.NON_NULL)
public record EntityValidationExceptionResponse(String message, List<ValidationErrorResponse> errors) {}