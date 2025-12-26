package com.mbd.core.adapter.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RuntimeExceptionResponse(
        Integer code,
        String message) {

}
