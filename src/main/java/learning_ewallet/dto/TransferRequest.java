package learning_ewallet.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferRequest(

        @NotNull(message = "senderId is required")
        @Positive(message = "senderId must be greater than zero")
        Long senderId,

        @NotNull(message = "receiverId is required")
        @Positive(message = "receiverId must be greater than zero")
        Long receiverId,

        @NotNull(message = "amount is required")
        @DecimalMin(
                value = "0.01",
                message = "amount must be at least 0.01"
        )
        @Digits(
                integer = 17,
                fraction = 2,
                message = "amount must have at most 17 integer digits and 2 decimal places"
        )
        BigDecimal amount
) {
}