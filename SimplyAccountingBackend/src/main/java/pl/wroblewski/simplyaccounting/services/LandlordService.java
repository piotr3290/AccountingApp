package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.entities.LandlordEntity;
import pl.wroblewski.simplyaccounting.db.repositories.LandlordRepository;
import pl.wroblewski.simplyaccounting.exceptions.ObjectNotFoundException;
import pl.wroblewski.simplyaccounting.models.dtos.LandlordDto;
import pl.wroblewski.simplyaccounting.models.responses.LandlordDetailsResponse;
import pl.wroblewski.simplyaccounting.models.responses.LandlordResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LandlordService {

    private final LandlordRepository landlordRepository;
    private final ConverterService converterService;
    private final AccountService accountService;
    private final CooperativeService cooperativeService;
    private final PremisesService premisesService;

    public List<LandlordResponse> getAllLandlords() {
        return landlordRepository
                .findAll()
                .stream()
                .map(converterService::landlordEntityToResponse)
                .collect(Collectors.toList());
    }

    public LandlordDto createLandlord(LandlordDto landlord) {
        var landlordEntity = converterService.landlordDtoToEntity(landlord);
        landlordEntity.setAccount(accountService.createLandlordAccount());
        return converterService.landlordEntityToDto(landlordRepository.save(landlordEntity));
    }

    public LandlordDto updateLandlord(LandlordDto landlord) {
        var landlordEntity = getLandlordEntity(landlord.getId())
                .toBuilder()
                .city(landlord.getCity())
                .emailAddress(landlord.getEmailAddress())
                .firstName(landlord.getFirstName())
                .houseNumber(landlord.getHouseNumber())
                .lastName(landlord.getLastName())
                .middleName(landlord.getMiddleName())
                .phoneNumber(landlord.getPhoneNumber())
                .postalCode(landlord.getPostalCode())
                .premisesNumber(landlord.getPremisesNumber())
                .street(landlord.getStreet())
                .build();
        return converterService.landlordEntityToDto(landlordRepository.save(landlordEntity));
    }

    LandlordEntity getLandlordEntity(int id) {
        return landlordRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cannot find landlord."));
    }

    public LandlordDetailsResponse getLandlordDetails(int id) {
        return converterService.landlordEntityToDetailsResponse(getLandlordEntity(id));
    }

    public LandlordDto getLandlord(int id) {
        return converterService.landlordEntityToDto(getLandlordEntity(id));
    }

    public void checkLandlordExists(int landlordId) {
        this.getLandlordEntity(landlordId);
    }

    public List<LandlordDto> getAllLandlordsForCooperative(Integer cooperativeId) {
        return getAllLandlordEntitiesForCooperative(cooperativeId)
                .stream()
                .map(converterService::landlordEntityToDto)
                .collect(Collectors.toList());
    }

    List<LandlordEntity> getAllLandlordEntitiesForCooperative(Integer cooperativeId) {
        cooperativeService.checkCooperativeExists(cooperativeId);
        return landlordRepository.findAllLandlordsForCooperative(cooperativeId);
    }

    public List<LandlordDto> getAllLandlordsForPremises(Integer premisesId) {
        return getAllLandlordEntitiesForPremises(premisesId)
                .stream()
                .map(converterService::landlordEntityToDto)
                .collect(Collectors.toList());
    }

    List<LandlordEntity> getAllLandlordEntitiesForPremises(Integer premisesId) {
        premisesService.checkPremisesExists(premisesId);
        return landlordRepository.findAllLandlordsForPremises(premisesId);
    }

}
