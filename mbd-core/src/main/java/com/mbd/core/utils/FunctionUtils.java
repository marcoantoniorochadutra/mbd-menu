package com.mbd.core.utils;

import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.Objects.isNull;
import static java.util.stream.Stream.empty;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class FunctionUtils {

    public static <T> Stream<T> nullSafeStream(Collection<T> value) {
        return isNull(value)
                ? empty()
                : cleansedStream(value.stream());
    }

    private static <T> Stream<T> cleansedStream(Stream<T> stream) {
        return stream.filter(Objects::nonNull);
    }

    public static <T> Stream<T> nullSafeStream(T[] array) {
        return isNull(array)
                ? empty()
                : stream(array);
    }
}
