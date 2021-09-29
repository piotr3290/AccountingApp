package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.entities.PremisesLandlordEntity;
import pl.wroblewski.simplyaccounting.db.repositories.PremisesLandlordRepository;
import pl.wroblewski.simplyaccounting.exceptions.ObjectNotFoundException;
import pl.wroblewski.simplyaccounting.models.dtos.PremisesLandlordDto;
import pl.wroblewski.simplyaccounting.models.responses.PremisesLandlordResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PremisesLandlordService {

    private final PremisesLandlordRepository premisesLandlordRepository;
    private final DatePeriodService datePeriodService;
    private final ConverterService converterService;
    private final PremisesService premisesService;
    private final LandlordService landlordService;

    public PremisesLandlordDto getPremisesLandlord(int id) {
        return converterService.premisesLandlordEntityToDto(getPremisesLandlordEntity(id));
    }

    private PremisesLandlordEntity getPremisesLandlordEntity(int id) {
        return premisesLandlordRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cannot find the landlord's premises period."));
    }

    public void deletePremisesLandlord(int id) {
        premisesLandlordRepository.delete(getPremisesLandlordEntity(id));
    }

    public List<PremisesLandlordResponse> createPremisesLandlord(PremisesLandlordDto premisesLandlord) {

        var newPremisesLandlord = converterService.premisesLandlordDtoToEntity(premisesLandlord);
        newPremisesLandlord.setLandlord(landlordService.getLandlordEntity(premisesLandlord.getLandlordId()));

        savePremisesLandlord(findAllByPremisesId(premisesLandlord.getPremisesId()), newPremisesLandlord);

        return findAllByPremisesId(premisesLandlord.getPremisesId())
                .stream()
                .map(converterService::premisesLandlordEntityToResponse)
                .collect(Collectors.toList());
    }

    private List<PremisesLandlordEntity> findAllByPremisesId(Integer premisesId) {
        premisesService.checkPremisesExists(premisesId);
        return premisesLandlordRepository.findAllByPremises_IdOrderByStartDate(premisesId);
    }

    private void savePremisesLandlord(List<PremisesLandlordEntity> premisesLandlordEntities, PremisesLandlordEntity newPremisesLandlord) {
        datePeriodService.save(premisesLandlordEntities, newPremisesLandlord, premisesLandlordRepository);
    }

    public List<PremisesLandlordResponse> editPremisesLandlord(PremisesLandlordDto premisesLandlord) {
        var premisesLandlordEntity = getPremisesLandlordEntity(premisesLandlord.getId())
                .toBuilder()
                .startDate(premisesLandlord.getStartDate())
                .endDate(premisesLandlord.getEndDate())
                .build();

        var premisesLandlordEntities = findAllByPremisesId(premisesLandlord.getPremisesId());
        premisesLandlordEntities.remove(premisesLandlordEntity);
        savePremisesLandlord(premisesLandlordEntities, premisesLandlordEntity);

        return findAllByPremisesId(premisesLandlord.getPremisesId())
                .stream()
                .map(converterService::premisesLandlordEntityToResponse)
                .collect(Collectors.toList());
    }

}
