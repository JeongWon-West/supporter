package com.project.supporter.common.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;

    private String message;

    private T data;

    private HttpStatus status;

    public static <T> ApiResponse<T> ok() {
        return ApiResponse.<T>builder()
                .success(true)
                .message("Success")
                .status(HttpStatus.OK)
                .build();
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        return
                ApiResponse.<T>builder()
                        .success(true)
                        .message(message)
                        .data(data)
                        .status(HttpStatus.OK)
                        .build();
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .status(status)
                .build();
    }
}
