package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wroblewski.simplyaccounting.models.dtos.*;

import java.util.List;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class CalculateOpeningBalanceService {

    private final CooperativeService cooperativeService;
    private final ContractorPaymentService contractorPaymentService;
    private final LandlordPaymentService landlordPaymentService;
    private final OpeningBalanceService openingBalanceService;
    private final ContractorService contractorService;
    private final InvoiceService invoiceService;
    private final AdvanceService advanceService;
    private final LandlordService landlordService;

    @Transactional
    public void calculateAllClosingBalance(Integer cooperativeId, Short year) {

        var cooperative = cooperativeService.getCooperative(cooperativeId);

        var contractorPayments = contractorPaymentService.getAllContractorPaymentForCooperativeAndYear(cooperativeId, year);
        var landlordPayments = landlordPaymentService.getAllLandlordPaymentForCooperativeAndYear(cooperativeId, year);

        var invoices = invoiceService.getAllInvoicesForCooperativeAndYear(cooperativeId, year);
        var advances = advanceService.getAllAdvancesForCooperativeAndYear(cooperativeId, year);

        calculateClosingBalance(cooperative.getAccountId(), cooperativeId, year,
                landlordPayments.stream()
                        .map(LandlordPaymentDto::getValue)
                        .collect(Collectors.toList()),
                contractorPayments.stream()
                        .map(ContractorPaymentDto::getValue)
                        .collect(Collectors.toList()));

        var contractorsUniqueIds = Stream.concat(
                contractorPayments.stream()
                        .map(ContractorPaymentDto::getContractorId),
                invoices.stream()
                        .map(InvoiceDto::getContractorId))
                .distinct()
                .collect(Collectors.toList());

        var landlordsUniqueIds = Stream.concat(
                landlordPayments.stream()
                        .map(LandlordPaymentDto::getLandlordId),
                advances.stream()
                        .map(AdvanceDto::getLandlordId))
                .distinct()
                .collect(Collectors.toList());

        contractorsUniqueIds.forEach(contractorId -> {
            var contractor = contractorService.getContractor(contractorId);
            calculateClosingBalance(contractor.getAccountId(), cooperativeId, year,
                    contractorPayments.stream()
                            .filter(payment -> payment.getContractorId().equals(contractorId))
                            .map(ContractorPaymentDto::getValue)
                            .collect(Collectors.toList()),
                    invoices.stream()
                            .filter(invoice -> invoice.getContractorId().equals(contractorId))
                            .map(InvoiceDto::getValue)
                            .collect(Collectors.toList()));
        });

        landlordsUniqueIds.forEach(landlordId -> {
            var landlord = landlordService.getLandlord(landlordId);
            calculateClosingBalance(landlord.getAccountId(), cooperativeId, year,
                    advances.stream()
                            .filter(advance -> advance.getLandlordId().equals(landlordId))
                            .map(AdvanceDto::getAmount)
                            .collect(Collectors.toList()),
                    landlordPayments.stream()
                            .filter(payment -> payment.getLandlordId().equals(landlordId))
                            .map(LandlordPaymentDto::getValue)
                            .collect(Collectors.toList()));
        });
    }

    public void calculateClosingBalance(Integer accountId, Integer cooperativeId, Short year,
                                        List<Double> debits, List<Double> credits) {

        var openingBalance = openingBalanceService.getOpeningBalanceByAccountAndYear(accountId, year, cooperativeId);

        var balance = openingBalance != null ? openingBalance.getBalance() : 0.0;

        var closingBalance = OpeningBalanceDto.builder()
                .accountId(accountId)
                .year((short) (year + (short) 1))
                .cooperativeId(cooperativeId)
                .balance(calculateBalance(balance, debits, credits))
                .build();

        openingBalanceService.createEditOpeningBalance(closingBalance);
    }

    private Double calculateBalance(Double openingBalance, List<Double> debits, List<Double> credits) {

        DoubleAdder balance = new DoubleAdder();
        balance.add(openingBalance);

        debits.forEach(balance::add);

        credits.forEach(o -> balance.add(-o));

        return balance.sum();
    }

}
