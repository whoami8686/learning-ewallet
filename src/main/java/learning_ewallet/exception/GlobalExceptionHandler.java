package learning_ewallet.exception;

import learning_ewallet.dto.ErrorResponse;
import learning_ewallet.dto.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<WebResponse<Void>> handleNotFound(
            ResourceNotFoundException exception
    ) {
        return response(
                HttpStatus.NOT_FOUND,
                "RESOURCE_NOT_FOUND",
                exception.getMessage()
        );
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    ResponseEntity<WebResponse<Void>> handleInsufficientBalance(
            InsufficientBalanceException exception
    ) {
        return response(
                HttpStatus.BAD_REQUEST,
                "INSUFFICIENT_BALANCE",
                exception.getMessage()
        );
    }

    @ExceptionHandler(BusinessException.class)
    ResponseEntity<WebResponse<Void>> handleBusiness(
            BusinessException exception
    ) {
        return response(
                HttpStatus.BAD_REQUEST,
                "BUSINESS_ERROR",
                exception.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<WebResponse<Void>> handleValidation(
            MethodArgumentNotValidException exception
    ) {

        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error ->
                        error.getField() + ": " + error.getDefaultMessage()
                )
                .collect(Collectors.joining(", "));

        return response(
                HttpStatus.BAD_REQUEST,
                "VALIDATION_ERROR",
                message
        );
    }

    @ExceptionHandler(NoResourceFoundException.class)
    ResponseEntity<WebResponse<Void>> handleNoResourceFound(
            NoResourceFoundException exception
    ) {
        return response(
                HttpStatus.NOT_FOUND,
                "RESOURCE_NOT_FOUND",
                "Endpoint not found: " + exception.getResourcePath()
        );
    }

    private ResponseEntity<WebResponse<Void>> response(
            HttpStatus status,
            String code,
            String message
    ) {
        return ResponseEntity
                .status(status)
                .body(
                        WebResponse.<Void>error(
                                LocalDateTime.now(),
                                status.value(),
                                code,
                                message
                        )
                );
    }
}
