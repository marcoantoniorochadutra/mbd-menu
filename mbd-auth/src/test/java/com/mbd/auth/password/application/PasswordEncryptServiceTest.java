package com.mbd.auth.password.application;

import com.mbd.infraestructure.config.MbdConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service Test - PasswordEncryptService")
class PasswordEncryptServiceTest {

    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private MbdConfig mbdConfig;
    @Mock
    private MbdConfig.PasswordConfig passwordConfig;

    @InjectMocks
    private PasswordEncryptService passwordEncryptService;

    @Test
    @DisplayName("Deve codificar a senha adicionando o pepper")
    void shouldEncodePasswordWithPepper() {
        String rawPassword = "senha123";
        String pepper = "pepperValue";
        String expectedEncoded = "encodedPassword";
        when(this.mbdConfig.getPassword()).thenReturn(this.passwordConfig);

        when(this.passwordConfig.getPepper()).thenReturn(pepper);
        when(this.passwordEncoder.encode(rawPassword + pepper)).thenReturn(expectedEncoded);

        String result = this.passwordEncryptService.encodePassword(rawPassword);

        assertEquals(expectedEncoded, result);
    }
}
