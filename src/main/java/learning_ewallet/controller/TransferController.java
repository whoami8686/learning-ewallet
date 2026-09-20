package learning_ewallet.controller;

import jakarta.validation.Valid;
import learning_ewallet.dto.TransferRequest;
import learning_ewallet.dto.TransferResponse;
import learning_ewallet.dto.WebResponse;
import learning_ewallet.service.TransferService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transfers")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<TransferResponse> transfer(@Valid @RequestBody TransferRequest request){
        TransferResponse response = transferService.transfer(request);
        return WebResponse.success(response);
    }
}
