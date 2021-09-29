package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.entities.ContractorPaymentEntity;
import pl.wroblewski.simplyaccounting.db.repositories.ContractorPaymentRepository;
import pl.wroblewski.simplyaccounting.exceptions.ObjectNotFoundException;
import pl.wroblewski.simplyaccounting.models.dtos.ContractorPaymentDto;
import pl.wroblewski.simplyaccounting.models.responses.ContractorPaymentResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContractorPaymentService {

    private final ContractorPaymentRepository contractorPaymentRepository;
    private final ConverterService converterService;
    private final ContractorService contractorService;
    private final CooperativeService cooperativeService;

    public ContractorPaymentDto getContractorPayment(int id) {
        return converterService.contractorPaymentEntityToDto(getContractorPaymentEntity(id));
    }

    private ContractorPaymentEntity getContractorPaymentEntity(int id) {
        return contractorPaymentRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cannot find a contractor payment."));
    }

    public void deleteContractorPayment(int id) {
        contractorPaymentRepository.delete(getContractorPaymentEntity(id));
    }

    public ContractorPaymentDto createContractorPayment(ContractorPaymentDto contractorPayment) {
        cooperativeService.checkCooperativeExists(contractorPayment.getCooperativeId());
        contractorService.checkContractorExists(contractorPayment.getContractorId());
        return converterService.contractorPaymentEntityToDto(
                contractorPaymentRepository.save(converterService.contractorPaymentDtoToEntity(contractorPayment))
        );
    }

    public ContractorPaymentDto editContractorPayment(ContractorPaymentDto contractorPayment) {
        return converterService.contractorPaymentEntityToDto(contractorPaymentRepository.save(
                getContractorPaymentEntity(contractorPayment.getId())
                        .toBuilder()
                        .realizationDate(contractorPayment.getRealizationDate())
                        .value(contractorPayment.getValue())
                        .build()
        ));
    }

    public List<ContractorPaymentResponse> getAllContractorPaymentForYear(Integer cooperativeId, Short year) {
        cooperativeService.checkCooperativeExists(cooperativeId);
        return getAllContractorPaymentEntityForYear(cooperativeId, year)
                .stream()
                .map(converterService::contractorPaymentEntityToResponse)
                .collect(Collectors.toList());
    }

    List<ContractorPaymentDto> getAllContractorPaymentForCooperativeAndYear(Integer cooperativeId, Short year){
        return getAllContractorPaymentEntityForYear(cooperativeId, year)
                .stream()
                .map(converterService::contractorPaymentEntityToDto)
                .collect(Collectors.toList());
    }

    List<ContractorPaymentEntity> getAllContractorPaymentEntityForYear(Integer cooperativeId, Short year) {
        return contractorPaymentRepository.findAllByYear(cooperativeId, year);
    }
}
