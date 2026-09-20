package learning_ewallet.service;

import learning_ewallet.dto.TransferRequest;
import learning_ewallet.dto.TransferResponse;

public interface TransferService {
    public TransferResponse transfer(TransferRequest request);
}
