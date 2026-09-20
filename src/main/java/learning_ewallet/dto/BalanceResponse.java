package learning_ewallet.dto;

import java.math.BigDecimal;

public record BalanceResponse(
        Long userId,
        String userName,
        BigDecimal balance
) {
}
