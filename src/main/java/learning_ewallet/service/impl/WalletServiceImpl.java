package learning_ewallet.service.impl;

import learning_ewallet.dto.BalanceResponse;
import learning_ewallet.entity.Wallet;
import learning_ewallet.exception.ResourceNotFoundException;
import learning_ewallet.repository.WalletRepository;
import learning_ewallet.service.WalletService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    @Transactional(readOnly=true)
    public BalanceResponse getBalance(Long userId) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Wallet not found for userId: " + userId
                ));

        return new BalanceResponse(
                wallet.getUser().getId(),
                wallet.getUser().getName(),
                wallet.getBalance()
        );
    }
}
