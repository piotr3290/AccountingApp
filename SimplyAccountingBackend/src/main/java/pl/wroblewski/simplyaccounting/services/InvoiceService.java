package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.entities.InvoiceEntity;
import pl.wroblewski.simplyaccounting.db.repositories.InvoiceRepository;
import pl.wroblewski.simplyaccounting.exceptions.ObjectNotFoundException;
import pl.wroblewski.simplyaccounting.models.dtos.InvoiceDto;
import pl.wroblewski.simplyaccounting.models.responses.InvoiceResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ConverterService converterService;
    private final InvoiceTypeService invoiceTypeService;
    private final CooperativeService cooperativeService;
    private final ContractorService contractorService;

    public InvoiceDto getInvoice(int id) {
        return converterService.invoiceEntityToDto(getInvoiceEntity(id));
    }

    private InvoiceEntity getInvoiceEntity(int id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cannot find the invoice."));
    }

    public void deleteInvoice(int id) {
        invoiceRepository.delete(getInvoiceEntity(id));
    }

    public InvoiceDto createInvoice(InvoiceDto invoice) {

        invoiceTypeService.checkInvoiceTypeExists(invoice.getInvoiceTypeId());
        cooperativeService.checkCooperativeExists(invoice.getContractorId());
        contractorService.checkContractorExists(invoice.getContractorId());

        return converterService.invoiceEntityToDto(
                invoiceRepository.save(converterService.invoiceDtoToEntity(invoice))
        );
    }

    public InvoiceDto editInvoice(InvoiceDto invoice) {

        var invoiceEntity = getInvoiceEntity(invoice.getId())
                .toBuilder()
                .value(invoice.getValue())
                .realizationDate(invoice.getRealizationDate())
                .invoiceType(invoiceTypeService.getInvoiceTypeEntity(invoice.getInvoiceTypeId()))
                .build();

        return converterService.invoiceEntityToDto(invoiceRepository.save(invoiceEntity));
    }

    public List<InvoiceResponse> getAllInvoicesForCooperative(int cooperativeId) {

        cooperativeService.checkCooperativeExists(cooperativeId);

        return getAllInvoiceEntitiesForCooperative(cooperativeId)
                .stream()
                .map(converterService::invoiceEntityToResponse)
                .collect(Collectors.toList());
    }

    public List<InvoiceDto> getAllInvoicesForCooperativeAndYear(Integer cooperativeId, Short year) {
        return getAllInvoiceEntitiesForCooperativeAndYear(cooperativeId, year)
                .stream()
                .map(converterService::invoiceEntityToDto)
                .collect(Collectors.toList());
    }

    List<InvoiceEntity> getAllInvoiceEntitiesForCooperativeAndYear(Integer cooperativeId, Short year) {
        return invoiceRepository.findAllByCooperativeAndYear(cooperativeId, year);
    }

    private List<InvoiceEntity> getAllInvoiceEntitiesForCooperative(Integer cooperativeId) {
        return invoiceRepository.findAllByCooperative_IdOrderByRealizationDate(cooperativeId);
    }
}
