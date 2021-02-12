package pl.wroblewski.simplyaccounting.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.simplyaccounting.models.dtos.AccountDto;
import pl.wroblewski.simplyaccounting.models.dtos.AccountTypeDto;
import pl.wroblewski.simplyaccounting.services.AccountService;
import pl.wroblewski.simplyaccounting.validation.CreateAccountInfo;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    @GetMapping(path = "/types")
    public ResponseEntity<List<AccountTypeDto>> getAllAccountType() {
        return ResponseEntity.ok(accountService.getAllAccountTypes());
    }

    @GetMapping(path = "/types/{id}")
    public ResponseEntity<AccountTypeDto> getAccountType(@PathVariable int id) {
        return ResponseEntity.ok(accountService.getAccountType(id));
    }

    @DeleteMapping(path = "/types/{id}")
    public ResponseEntity<String> deleteAccountType(@PathVariable int id) {
        accountService.deleteAccountTypeById(id);
        return ResponseEntity.ok("Successfully deleted an object.");
    }

    @GetMapping
    public ResponseEntity<Page<AccountDto>> getAllAccounts(
            @RequestHeader(value = "page-number", required = false, defaultValue = "1") int pageNumber,
            @RequestHeader(value = "items-per-page", required = false, defaultValue = "10") int itemsPerPage) {
        return ResponseEntity.ok(accountService.getAllAccounts(pageNumber, itemsPerPage));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteAccountById(@PathVariable int id) {
        accountService.deleteAccountById(id);
        return ResponseEntity.ok("Successfully deleted an object.");
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable int id) {
        return ResponseEntity.ok(accountService.getAccount(id));
    }

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@Validated(CreateAccountInfo.class) @RequestBody AccountDto account) {
        return ResponseEntity.ok(accountService.createAccount(account));
    }

}
