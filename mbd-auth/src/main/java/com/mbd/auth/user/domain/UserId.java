package com.mbd.auth.user.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.mbd.core.hibernate.UUIDWrapperJavaType;
import com.mbd.core.model.UUIDWrapper;

import java.util.UUID;

import static com.mbd.core.utils.FunctionUtils.getIfNotNullOrDefault;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public record UserId(UUID id) implements UUIDWrapper {

    @JsonCreator
    public UserId(String raw) {
        this(UUID.fromString(raw));
    }

    public static UserId generate() {
        return new UserId(UUID.randomUUID());
    }

    @Override
    @JsonValue
    public String toString() {
        return getIfNotNullOrDefault(this.id, UUID::toString, EMPTY);
    }

    public static class UserIdJavaType extends UUIDWrapperJavaType<UserId> {
        protected UserIdJavaType() {
            super(UserId.class, UserId::new);
        }
    }

}