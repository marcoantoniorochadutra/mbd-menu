package com.mbd.admin.dev.in.api;

import com.mbd.admin.dev.usecase.DevUseCase;
import com.mbd.admin.utils.ControllerTestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DevController.class)
@DisplayName("Controller Test - DevController")
class DevControllerTest extends ControllerTestUtils {

    @MockitoBean
    private DevUseCase devUseCase;

    @Test
    @DisplayName("Should return default message when publishing debug event")
    void shouldReturnDefaultMessage() throws Exception {
        this.executePost("/api/v1/dev/publicarDebug", Map.of("key", "value"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Debug event published successfully")));
    }

}


