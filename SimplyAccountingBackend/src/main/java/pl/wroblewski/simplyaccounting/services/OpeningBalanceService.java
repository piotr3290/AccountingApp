package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.entities.OpeningBalanceEntity;
import pl.wroblewski.simplyaccounting.db.repositories.OpeningBalanceRepository;
import pl.wroblewski.simplyaccounting.exceptions.ObjectAlreadyExistsException;
import pl.wroblewski.simplyaccounting.exceptions.ObjectNotFoundException;
import pl.wroblewski.simplyaccounting.models.dtos.OpeningBalanceDto;
import pl.wroblewski.simplyaccounting.models.responses.OpeningBalanceResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OpeningBalanceService {

    private final OpeningBalanceRepository openingBalanceRepository;
    private final ConverterService converterService;
    private final AccountService accountService;
    private final CooperativeService cooperativeService;
    private final LandlordService landlordService;
    private final ContractorService contractorService;

    public void deleteOpeningBalance(int id) {
        openingBalanceRepository.delete(getOpeningBalanceEntity(id));
    }

    public OpeningBalanceDto getOpeningBalance(int id) {
        return converterService.openingBalanceEntityToDto(getOpeningBalanceEntity(id));
    }

    public List<OpeningBalanceResponse> createOpeningBalance(OpeningBalanceDto openingBalance) {
        accountService.checkAccountExists(openingBalance.getAccountId());
        checkOpeningBalanceExists(openingBalance.getAccountId(),
                openingBalance.getYear(), openingBalance.getCooperativeId());

        var openingBalanceEntity = converterService.openingBalanceDtoToEntity(openingBalance);
        openingBalanceEntity.setCooperative(cooperativeService.getCooperativeEntity(openingBalance.getCooperativeId()));

        openingBalanceRepository.saveAndFlush(openingBalanceEntity);

        return openingBalanceRepository.findAllByAccount_IdOrderByYear(openingBalance.getAccountId())
                .stream()
                .map(converterService::openingBalanceEntityToResponse)
                .collect(Collectors.toList());
    }

    public List<OpeningBalanceResponse> editOpeningBalance(OpeningBalanceDto openingBalance) {
        var openingBalanceEntity = getOpeningBalanceEntity(openingBalance.getId())
                .toBuilder()
                .balance(openingBalance.getBalance())
                .build();

        openingBalanceRepository.saveAndFlush(openingBalanceEntity);

        return openingBalanceRepository.findAllByAccount_IdOrderByYear(openingBalance.getAccountId())
                .stream()
                .map(converterService::openingBalanceEntityToResponse)
                .collect(Collectors.toList());
    }

    public void createEditOpeningBalance(OpeningBalanceDto openingBalance) {
        var openingBalanceEntity =
                getOptionalOpeningBalanceEntityByAccountAndYear(openingBalance.getAccountId(),
                        openingBalance.getYear(), openingBalance.getCooperativeId());

        if (openingBalanceEntity.isPresent()) {
            openingBalanceRepository.save(openingBalanceEntity.get()
                    .toBuilder()
                    .balance(openingBalance.getBalance())
                    .build());
        } else {
            openingBalanceRepository.save(converterService.openingBalanceDtoToEntity(openingBalance));
        }
    }

    private OpeningBalanceEntity getOpeningBalanceEntity(int id) {
        return openingBalanceRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cannot find the opening balance."));
    }

    public OpeningBalanceDto getOpeningBalanceByAccountAndYear(Integer accountId, Short year, Integer cooperativeId) {
        var openingBalance = getOpeningBalanceEntityByAccountAndYearAndCooperative(
                accountId, year, cooperativeId);
        return openingBalance.map(converterService::openingBalanceEntityToDto).orElse(null);
    }

    private Optional<OpeningBalanceEntity> getOpeningBalanceEntityByAccountAndYearAndCooperative(Integer accountId,
                                                                                                 Short year, Integer cooperativeId) {
        return openingBalanceRepository.findByAccount_IdAndYearAndCooperative_Id(accountId, year, cooperativeId);
    }

    Optional<OpeningBalanceEntity> getOptionalOpeningBalanceEntityByAccountAndYear(Integer accountId, Short year, Integer cooperativeId) {
        return openingBalanceRepository.findByAccount_IdAndYearAndCooperative_Id(accountId, year, cooperativeId);
    }

    private boolean isOpeningBalanceExists(Integer accountId, Short year, Integer cooperativeId) {
        return openingBalanceRepository.existsByAccount_IdAndYearAndCooperative_Id(accountId, year, cooperativeId);
    }

    private void checkOpeningBalanceExists(Integer accountId, Short year, Integer cooperativeId) {
        if (isOpeningBalanceExists(accountId, year, cooperativeId)) {
            throw new ObjectAlreadyExistsException("The account already has opening balance in that year.");
        }
    }

    public List<OpeningBalanceDto> getOpeningBalancesForCooperative(int cooperativeId) {
        cooperativeService.checkCooperativeExists(cooperativeId);
        return openingBalanceRepository.findAllByAccount_Cooperative_IdOrderByYear(cooperativeId)
                .stream()
                .map(converterService::openingBalanceEntityToDto)
                .collect(Collectors.toList());
    }

    public List<OpeningBalanceDto> getOpeningBalancesForLandlord(int landlordId) {
        landlordService.checkLandlordExists(landlordId);
        return openingBalanceRepository.findAllByAccount_Landlord_IdOrderByYear(landlordId)
                .stream()
                .map(converterService::openingBalanceEntityToDto)
                .collect(Collectors.toList());
    }

    public List<OpeningBalanceDto> getOpeningBalancesForContractor(int contractorId) {
        contractorService.checkContractorExists(contractorId);
        return openingBalanceRepository.findAllByAccount_Contractor_IdOrderByYear(contractorId)
                .stream()
                .map(converterService::openingBalanceEntityToDto)
                .collect(Collectors.toList());
    }

    public List<OpeningBalanceResponse> getOpeningBalancesForAccount(int accountId) {
        accountService.checkAccountExists(accountId);
        return openingBalanceRepository.findAllByAccount_IdOrderByYear(accountId)
                .stream()
                .map(converterService::openingBalanceEntityToResponse)
                .collect(Collectors.toList());
    }
}
