package com.mbd.auth.password.application;

import com.mbd.auth.password.usecase.PasswordEncoderUseCase;
import com.mbd.infraestructure.config.MbdConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordEncryptService implements PasswordEncoderUseCase {

    private final BCryptPasswordEncoder passwordEncoder;
    private final MbdConfig mbdConfig;

    @Override
    public String encodePassword(String rawPassword) {
        String pepperedPassword = this.addPepper(rawPassword);
        return this.passwordEncoder.encode(pepperedPassword);
    }

    private String addPepper(String rawPassword) {
        return rawPassword + this.mbdConfig.getPassword().getPepper();
    }
}
