package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.entities.PeopleNumberEntity;
import pl.wroblewski.simplyaccounting.db.repositories.PeopleNumberRepository;
import pl.wroblewski.simplyaccounting.exceptions.ObjectNotFoundException;
import pl.wroblewski.simplyaccounting.models.dtos.PeopleNumberDto;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PeopleNumberService {

    private final PeopleNumberRepository peopleNumberRepository;
    private final PremisesService premisesService;
    private final ConverterService converterService;
    private final DatePeriodService datePeriodService;

    public List<PeopleNumberDto> addNewPeopleNumber(PeopleNumberDto peopleNumber) {
        var premises = premisesService.getPremisesEntity(peopleNumber.getPremisesId());
        savePremisesPeopleNumber(premises.getPeopleNumbers(), converterService.peopleNumberDtoToEntity(peopleNumber));
        return peopleNumberRepository.findAllByPremises_IdOrderByStartDate(peopleNumber.getPremisesId())
                .stream()
                .map(converterService::peopleNumberEntityToDto)
                .collect(Collectors.toList());
    }

    public List<PeopleNumberDto> editPeopleNumber(PeopleNumberDto peopleNumber) {
        var peopleNumberEntity = getPeopleNumberEntity(peopleNumber.getId())
                .toBuilder()
                .startDate(peopleNumber.getStartDate())
                .endDate(peopleNumber.getEndDate())
                .number(peopleNumber.getNumber())
                .build();

        var premisesPeopleNumbers = peopleNumberRepository.findAllByPremises_IdOrderByStartDate(peopleNumberEntity.getPremises().getId());
        premisesPeopleNumbers.remove(peopleNumberEntity);
        savePremisesPeopleNumber(premisesPeopleNumbers, peopleNumberEntity);

        return peopleNumberRepository.findAllByPremises_IdOrderByStartDate(peopleNumberEntity.getPremises().getId())
                .stream()
                .map(converterService::peopleNumberEntityToDto)
                .collect(Collectors.toList());
    }

    public void deletePeopleNumber(int id) {
        var peopleNumber = getPeopleNumberEntity(id);
        peopleNumberRepository.delete(peopleNumber);
    }

    public PeopleNumberDto getPeopleNumber(int id) {
        return converterService.peopleNumberEntityToDto(getPeopleNumberEntity(id));
    }

    private PeopleNumberEntity getPeopleNumberEntity(int id) {
        return peopleNumberRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cannot find a people number."));
    }

    private void savePremisesPeopleNumber(Collection<PeopleNumberEntity> peopleNumberEntities,
                                          PeopleNumberEntity newPeopleNumber) {
        datePeriodService.save(peopleNumberEntities, newPeopleNumber, peopleNumberRepository);
    }

}
