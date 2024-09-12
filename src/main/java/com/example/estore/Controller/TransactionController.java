package com.example.estore.Controller;
import com.example.estore.DTO.request.TransactionDTO;
import com.example.estore.DTO.response.RenderJson;
import com.example.estore.model.User;
import com.example.estore.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions") 
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody TransactionDTO.CreateTransaction request,
            @AuthenticationPrincipal User user
    ) {
        return RenderJson.basicFormat(transactionService.create(request, user), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAll(Pageable pageable) {
        return RenderJson.pageFormat(transactionService.index(pageable), HttpStatus.ACCEPTED);
    }
}
