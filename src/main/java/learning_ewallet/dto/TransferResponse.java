package learning_ewallet.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransferResponse(
        UUID reference,
        Long senderId,
        Long receiverId,
        BigDecimal amount,
        LocalDateTime createdAt
) {
}
