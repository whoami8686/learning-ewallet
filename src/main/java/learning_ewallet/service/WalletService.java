package learning_ewallet.service;

import learning_ewallet.dto.BalanceResponse;

public interface WalletService {
    public BalanceResponse getBalance(Long userId);
}
