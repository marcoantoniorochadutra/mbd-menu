package com.mbd.core.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class JsonUtils {

    private static final JsonUtils INSTANCE = new JsonUtils();

    private final Gson gson;

    private JsonUtils() {
        this.gson = new GsonBuilder().create();
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json == null || clazz == null) return null;
        return INSTANCE.gson.fromJson(json, clazz);
    }

    public static String toJson(Object obj) {
        if (Objects.isNull(obj)) return null;
        return INSTANCE.gson.toJson(obj);
    }

    public static Gson gson() {
        return INSTANCE.gson;
    }
}
