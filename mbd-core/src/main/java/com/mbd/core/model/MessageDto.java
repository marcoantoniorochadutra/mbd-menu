package com.mbd.core.model;

public record MessageDto(String message) {
    public static MessageDto of(String message) {
        return new MessageDto(message);
    }
}
