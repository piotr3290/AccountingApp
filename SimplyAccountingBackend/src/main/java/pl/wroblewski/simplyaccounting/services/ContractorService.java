package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.entities.ContractorEntity;
import pl.wroblewski.simplyaccounting.db.repositories.ContractorRepository;
import pl.wroblewski.simplyaccounting.exceptions.ObjectNotFoundException;
import pl.wroblewski.simplyaccounting.models.dtos.ContractorDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContractorService {

    private final ContractorRepository contractorRepository;
    private final ConverterService converterService;
    private final AccountService accountService;

    public List<ContractorDto> getAllContractors() {
        return contractorRepository
                .findAll()
                .stream()
                .map(converterService::contractorEntityToDto)
                .collect(Collectors.toList());
    }

    public ContractorDto getContractor(Integer id) {
        return converterService.contractorEntityToDto(getContractorEntity(id));
    }

    private ContractorEntity getContractorEntity(Integer id) {
        return contractorRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cannot find the contractor."));
    }

    public ContractorDto createContractor(ContractorDto contractor) {
        var contractorEntity = converterService.contractorDtoToEntity(contractor);
        contractorEntity.setAccount(accountService.createContractorAccount());
        return converterService.contractorEntityToDto(contractorRepository.save(contractorEntity));
    }

    public ContractorDto updateContractor(ContractorDto contractor) {
        var contractorEntity = getContractorEntity(contractor.getId())
                .toBuilder()
                .name(contractor.getName())
                .nip(contractor.getNip())
                .build();
        return converterService.contractorEntityToDto(contractorRepository.save(contractorEntity));
    }

    public void checkContractorExists(Integer contractorId) {
        getContractorEntity(contractorId);
    }
}
