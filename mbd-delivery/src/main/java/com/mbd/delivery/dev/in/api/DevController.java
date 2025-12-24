package com.mbd.delivery.dev.in.api;


import com.mbd.core.model.MessageDto;
import com.mbd.delivery.dev.usecase.DevUseCase;
import com.mbd.delivery.dev.usecase.DevUseCase.DebugEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(DevController.ENDPOINT)
public class DevController {

    public static final String ENDPOINT = "/api/v1/dev";

    private final DevUseCase devUseCase;

    @PostMapping(path = "/publicarDebug")
    public ResponseEntity<MessageDto> publicarEventos(@RequestBody DebugEvent event) {
        this.devUseCase.publishDebugEvent(event);
        return ResponseEntity.ok(MessageDto.of("Debug event published successfully"));
    }

}
