package pl.wroblewski.simplyaccounting.services;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.entities.CooperativeEntity;
import pl.wroblewski.simplyaccounting.db.repositories.CooperativeRepository;
import pl.wroblewski.simplyaccounting.exceptions.ObjectNotFoundException;
import pl.wroblewski.simplyaccounting.models.dtos.CooperativeDto;
import pl.wroblewski.simplyaccounting.models.responses.CooperativeResponse;

@Service
@AllArgsConstructor
public class CooperativeService {

    private final int COOPERATIVE_ACCOUNT_TYPE_ID = 1;
    private final CooperativeRepository cooperativeRepository;
    private final ConverterService converterService;
    private final AccountService accountService;


    public Page<CooperativeDto> getAll(int pageNumber, int itemsPerPage) {
        return cooperativeRepository.findAll(PageRequest.of(pageNumber - 1, itemsPerPage))
                .map(converterService::cooperativeEntityToDto);
    }

    public CooperativeDto createCooperative(CooperativeDto cooperativeDto) {
        var cooperative = converterService.cooperativeDtoToEntity(cooperativeDto);
        cooperative.setAccount(accountService.createAccount(COOPERATIVE_ACCOUNT_TYPE_ID));
        return converterService.cooperativeEntityToDto(cooperativeRepository.save(cooperative));
    }

    public CooperativeDto getCooperative(int id) {
        return converterService.cooperativeEntityToDto(getCooperativeEntity(id));
    }

    public CooperativeDto updateCooperative(CooperativeDto cooperative) {
        return converterService.cooperativeEntityToDto(cooperativeRepository.save(
                getCooperativeEntity(cooperative.getId())
                .toBuilder()
                .city(cooperative.getCity())
                .houseNumber(cooperative.getHouseNumber())
                .name(cooperative.getName())
                .nip(cooperative.getNip())
                .postalCode(cooperative.getPostalCode())
                .regon(cooperative.getRegon())
                .street(cooperative.getStreet())
                .build()));
    }

    CooperativeEntity getCooperativeEntity(int id) {
        return cooperativeRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cannot find cooperative."));
    }

    public CooperativeResponse getCooperativeWithBuildings(int id){
        return converterService.cooperativeEntityToResponse(getCooperativeEntity(id));
    }
}

