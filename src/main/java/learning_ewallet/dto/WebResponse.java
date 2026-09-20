package learning_ewallet.dto;

import java.time.LocalDateTime;

public record WebResponse<T>(
        T data,
        ErrorResponse error
) {
    public static <T> WebResponse<T> success(T data) {
        return new WebResponse<>(data, null);
    }

    public static <T> WebResponse<T> error(
            LocalDateTime timestamp,
            int status,
            String code,
            String message
    ) {
        return new WebResponse<>(
                null,
                new ErrorResponse(timestamp, status, code, message)
        );
    }
}
