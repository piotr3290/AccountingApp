package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.entities.InvoiceTypeEntity;
import pl.wroblewski.simplyaccounting.db.repositories.InvoiceTypeRepository;
import pl.wroblewski.simplyaccounting.exceptions.ObjectNotFoundException;
import pl.wroblewski.simplyaccounting.exceptions.ObjectReferenceException;
import pl.wroblewski.simplyaccounting.models.dtos.InvoiceTypeDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InvoiceTypeService {

    private final InvoiceTypeRepository invoiceTypeRepository;
    private final ConverterService converterService;

    public InvoiceTypeDto getInvoiceType(int id) {
        return converterService.invoiceTypeEntityToDto(getInvoiceTypeEntity(id));
    }

    InvoiceTypeEntity getInvoiceTypeEntity(int id) {
        return invoiceTypeRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cannot find the invoice's type."));
    }

    public void checkInvoiceTypeExists(int id) {
        getInvoiceTypeEntity(id);
    }

    public void deleteInvoiceType(int id) {
        var invoiceTypeEntity = getInvoiceTypeEntity(id);
        if (!invoiceTypeEntity.getInvoices().isEmpty()) {
            throw new ObjectReferenceException("Cannot delete invoice's type.");
        }
        invoiceTypeRepository.delete(invoiceTypeEntity);
    }

    public InvoiceTypeDto createInvoiceType(InvoiceTypeDto invoiceType) {
        return converterService.invoiceTypeEntityToDto(
                invoiceTypeRepository.save(converterService.invoiceTypeDtoToEntity(invoiceType))
        );
    }

    public InvoiceTypeDto editInvoiceType(InvoiceTypeDto invoiceType) {
        var invoiceTypeEntity = getInvoiceTypeEntity(invoiceType.getId())
                .toBuilder()
                .name(invoiceType.getName())
                .number(invoiceType.getNumber())
                .build();
        return converterService.invoiceTypeEntityToDto(invoiceTypeRepository.save(invoiceTypeEntity));
    }

    public List<InvoiceTypeDto> getAllInvoiceTypes() {
        return invoiceTypeRepository.findAll()
                .stream()
                .map(converterService::invoiceTypeEntityToDto)
                .collect(Collectors.toList());
    }
}
