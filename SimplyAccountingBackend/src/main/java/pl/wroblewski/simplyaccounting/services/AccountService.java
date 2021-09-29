package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.entities.AccountEntity;
import pl.wroblewski.simplyaccounting.db.entities.AccountTypeEntity;
import pl.wroblewski.simplyaccounting.db.repositories.AccountRepository;
import pl.wroblewski.simplyaccounting.db.repositories.AccountTypeRepository;
import pl.wroblewski.simplyaccounting.exceptions.ObjectNotFoundException;
import pl.wroblewski.simplyaccounting.exceptions.ObjectReferenceException;
import pl.wroblewski.simplyaccounting.models.dtos.AccountDto;
import pl.wroblewski.simplyaccounting.models.dtos.AccountTypeDto;
import pl.wroblewski.simplyaccounting.models.responses.AccountResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountService {

    private final int COOPERATIVE_ACCOUNT_TYPE_ID = 1;
    private final int LANDLORD_ACCOUNT_TYPE_ID = 2;
    private final int CONTRACTOR_ACCOUNT_TYPE_ID = 3;
    private final AccountTypeRepository accountTypeRepository;
    private final AccountRepository accountRepository;
    private final ConverterService converterService;

    public List<AccountTypeDto> getAllAccountTypes() {
        return accountTypeRepository.findAll().stream()
                .map(converterService::accountTypeEntityToDto)
                .collect(Collectors.toList());
    }

    public AccountTypeDto getAccountType(int id) {
        return converterService.accountTypeEntityToDto(getAccountTypeEntity(id));
    }

    AccountTypeEntity getAccountTypeEntity(Integer id) {
        return accountTypeRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cannot find account type."));
    }

    public Page<AccountDto> getAllAccounts(int pageNumber, int itemsPerPage) {
        return accountRepository.findAll(PageRequest.of(pageNumber - 1, itemsPerPage))
                .map(converterService::accountEntityToDto);
    }

    public AccountDto getAccount(int id) {
        return converterService.accountEntityToDto(getAccountEntity(id));
    }

    public void checkAccountExists(int id) {
        getAccountEntity(id);
    }

    private AccountEntity getAccountEntity(int id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cannot find account."));
    }

    public void deleteAccountById(int id) {
        if (accountRepository.deleteById(id) != 1) {
            throw new ObjectNotFoundException("Cannot find account.");
        }
    }

    public void deleteAccountTypeById(int id) {
        var accountType = accountTypeRepository.findById(id);
        if (accountType.isEmpty()) {
            throw new ObjectNotFoundException("Cannot find account type.");
        }
        if (accountType.get().getAccounts().size() > 0) {
            throw new ObjectReferenceException("Cannot delete account type.");
        }
        accountTypeRepository.deleteById(id);
    }

    public AccountDto createAccount(AccountDto account) {
        return converterService.accountEntityToDto(this.createAccount(account.getAccountType().getId()));
    }

    public AccountEntity createAccount(int accountTypeId) {
        var accountType = accountTypeRepository.findById(accountTypeId);
        if (accountType.isEmpty()) {
            throw new ObjectNotFoundException("Cannot find account type.");
        }
        return accountRepository.save(AccountEntity
                .builder()
                .accountType(accountType.get())
                .build());
    }

    public AccountEntity createCooperativeAccount() {
        return createAccount(COOPERATIVE_ACCOUNT_TYPE_ID);
    }

    public AccountEntity createLandlordAccount() {
        return createAccount(LANDLORD_ACCOUNT_TYPE_ID);
    }

    public AccountEntity createContractorAccount() {
        return createAccount(CONTRACTOR_ACCOUNT_TYPE_ID);
    }

    public AccountTypeDto getCooperativeAccountType() {
        return getAccountType(COOPERATIVE_ACCOUNT_TYPE_ID);
    }

    public AccountTypeDto getLandlordAccountType() {
        return getAccountType(LANDLORD_ACCOUNT_TYPE_ID);
    }

    public AccountTypeDto getContractorAccountType() {
        return getAccountType(CONTRACTOR_ACCOUNT_TYPE_ID);
    }

    public AccountTypeDto editAccountType(AccountTypeDto accountType) {
        var accountTypeEntity = getAccountTypeEntity(accountType.getId())
                .toBuilder()
                .name(accountType.getName())
                .number(accountType.getNumber())
                .build();
        return converterService.accountTypeEntityToDto(accountTypeRepository.save(accountTypeEntity));
    }

    public AccountResponse getAccountResponse(Integer id) {
        return converterService.accountEntityToResponse(getAccountEntity(id));
    }
}
