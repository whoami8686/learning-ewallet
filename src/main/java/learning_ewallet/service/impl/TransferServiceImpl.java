package learning_ewallet.service.impl;

import learning_ewallet.dto.TransferRequest;
import learning_ewallet.dto.TransferResponse;
import learning_ewallet.entity.Transfer;
import learning_ewallet.entity.User;
import learning_ewallet.entity.Wallet;
import learning_ewallet.exception.BusinessException;
import learning_ewallet.exception.InsufficientBalanceException;
import learning_ewallet.exception.ResourceNotFoundException;
import learning_ewallet.repository.TransferRepository;
import learning_ewallet.repository.UserRepository;
import learning_ewallet.repository.WalletRepository;
import learning_ewallet.service.TransferService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransferServiceImpl implements TransferService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransferRepository transferRepository;

    public TransferServiceImpl(
            UserRepository userRepository,
            WalletRepository walletRepository,
            TransferRepository transferRepository
    ) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.transferRepository = transferRepository;
    }

    @Override
    @Transactional
    public TransferResponse transfer(TransferRequest request) {

        validateDifferentUsers(
                request.senderId(),
                request.receiverId()
        );

        User sender = getUser(request.senderId());
        User receiver = getUser(request.receiverId());

        Long firstUserId = Math.min(
                sender.getId(),
                receiver.getId()
        );

        Long secondUserId = Math.max(
                sender.getId(),
                receiver.getId()
        );

        Wallet firstWallet = getWalletForUpdate(firstUserId);
        Wallet secondWallet = getWalletForUpdate(secondUserId);

        Wallet senderWallet = sender.getId().equals(firstUserId)
                ? firstWallet
                : secondWallet;

        Wallet receiverWallet = receiver.getId().equals(firstUserId)
                ? firstWallet
                : secondWallet;

        validateBalance(
                senderWallet.getBalance(),
                request.amount()
        );

        senderWallet.debit(request.amount());
        receiverWallet.credit(request.amount());

        Transfer transfer = transferRepository.save(
                new Transfer(
                        sender,
                        receiver,
                        request.amount()
                )
        );

        return new TransferResponse(
                transfer.getReference(),
                sender.getId(),
                receiver.getId(),
                transfer.getAmount(),
                transfer.getCreatedAt()
        );
    }

    private void validateDifferentUsers(
            Long senderId,
            Long receiverId
    ) {
        if (senderId.equals(receiverId)) {
            throw new BusinessException(
                    "Sender and receiver must be different"
            );
        }
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found for userId: " + userId
                        )
                );
    }

    private Wallet getWalletForUpdate(Long userId) {
        return walletRepository.findByUserIdForUpdate(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Wallet not found for userId: " + userId
                        )
                );
    }

    private void validateBalance(
            BigDecimal balance,
            BigDecimal amount
    ) {
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException(
                    "Insufficient balance"
            );
        }
    }
}