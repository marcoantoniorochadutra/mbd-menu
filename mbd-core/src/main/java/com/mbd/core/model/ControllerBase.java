package com.mbd.core.model;

import org.springframework.http.ResponseEntity;

import java.net.URI;

public interface ControllerBase {

    default <T> ResponseEntity<T> createdResponse(String endpoint, Object value) {
        return ResponseEntity.created(URI.create(this.buildUrl(endpoint, value.toString()))).build();
    }

    default <T> ResponseEntity<T> successResponse(T body) {
        return ResponseEntity.ok(body);
    }

    default <T> ResponseEntity<T> noContentResponse() {
        return ResponseEntity.noContent().build();
    }

    default <T> ResponseEntity<T> notFoundResponse() {
        return ResponseEntity.notFound().build();
    }

    default <T> ResponseEntity<T> badRequestResponse(T response) {
        return ResponseEntity.badRequest().body(response);
    }

    default String buildUrl(String... paths) {
        if (paths == null || paths.length == 0) {
            return "";
        }

        StringBuilder url = new StringBuilder();
        for (int i = 0; i < paths.length; i++) {
            String path = paths[i];
            if (path == null || path.isEmpty()) {
                continue;
            }

            if (i > 0 && path.startsWith("/")) {
                path = path.substring(1);
            }

            if (i < paths.length - 1 && path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }

            if (!url.isEmpty() && !path.isEmpty()) {
                url.append("/");
            }
            url.append(path);
        }

        return url.toString();
    }

}
