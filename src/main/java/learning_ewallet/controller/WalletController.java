package learning_ewallet.controller;

import learning_ewallet.dto.BalanceResponse;
import learning_ewallet.dto.WebResponse;
import learning_ewallet.service.WalletService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/{userId}/balance")
    public WebResponse<BalanceResponse> getBalance(@PathVariable Long userId){
        BalanceResponse response = walletService.getBalance(userId);
        return WebResponse.success(response);
    }
}
