package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.entities.AreaEntity;
import pl.wroblewski.simplyaccounting.db.repositories.AreaRepository;
import pl.wroblewski.simplyaccounting.exceptions.IncorrectDateException;
import pl.wroblewski.simplyaccounting.exceptions.ObjectNotFoundException;
import pl.wroblewski.simplyaccounting.models.dtos.AreaDto;
import pl.wroblewski.simplyaccounting.utils.DateUtilsComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AreaService {

    private final AreaRepository areaRepository;
    private final PremisesService premisesService;
    private final ConverterService converterService;
    private final DateUtilsComponent dateUtilsComponent;

    public List<AreaDto> addNewAreaToPremises(AreaDto area) {
        var premises = premisesService.getPremisesEntity(area.getPremisesId());
        savePremisesAreas(new ArrayList<>(premises.getAreas()), converterService.areaDtoToEntity(area));
        return areaRepository.findAllByPremises_IdOrderByStartDate(area.getPremisesId())
                .stream()
                .map(converterService::areaEntityToDto)
                .collect(Collectors.toList());
    }

    public List<AreaDto> editArea(AreaDto area) {
        var areaEntity = getAreaEntity(area.getId())
                .toBuilder()
                .startDate(area.getStartDate())
                .endDate(area.getEndDate())
                .value(area.getValue())
                .build();

        var premisesAreas = areaRepository.findAllByPremises_IdOrderByStartDate(areaEntity.getPremises().getId());
        premisesAreas.remove(areaEntity);
        savePremisesAreas(premisesAreas, areaEntity);

        return areaRepository.findAllByPremises_IdOrderByStartDate(area.getPremisesId())
                .stream()
                .map(converterService::areaEntityToDto)
                .collect(Collectors.toList());
    }

    public void deleteArea(int id) {
        var area = getAreaEntity(id);
        areaRepository.delete(area);
    }

    private AreaEntity getAreaEntity(int id) {
        return areaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cannot find"));
    }

    private void savePremisesAreas(List<AreaEntity> areaEntities, AreaEntity newArea) {

        if (areaEntities.size() == 0) {
            areaRepository.saveAndFlush(newArea);
            return;
        }

        if (newArea.getStartDate() == null && newArea.getEndDate() == null) {
            throw new IncorrectDateException("Incorrect dates");
        }

        if (newArea.getStartDate() == null) {

            var earliestDateArea = findEarliestDateArea(areaEntities);

            if (earliestDateArea.getEndDate() != null && !earliestDateArea.getEndDate().isAfter(newArea.getEndDate())) {
                throw new IncorrectDateException("Incorrect dates.");
            }

            if (earliestDateArea.getStartDate() == null) {
                earliestDateArea.setStartDate(newArea.getEndDate().plusDays(1));
                areaRepository.saveAndFlush(earliestDateArea);
            } else if (!earliestDateArea.getStartDate().isAfter(newArea.getEndDate())) {
                throw new IncorrectDateException("Incorrect dates.");
            }

        } else if (newArea.getEndDate() == null) {

            var latestDateArea = findLatestDateArea(areaEntities);

            if (latestDateArea.getEndDate() != null && !latestDateArea.getStartDate().isBefore(newArea.getStartDate())) {
                throw new IncorrectDateException("Incorrect dates.");
            }

            if (latestDateArea.getEndDate() == null) {
                latestDateArea.setEndDate(newArea.getStartDate().minusDays(1));
                areaRepository.saveAndFlush(latestDateArea);
            } else if (!latestDateArea.getEndDate().isBefore(newArea.getStartDate())) {
                throw new IncorrectDateException("Incorrect dates.");
            }

        } else {
            checkDatesOverlapping(areaEntities, newArea);
        }
        areaRepository.saveAndFlush(newArea);
    }

    private void checkDatesOverlapping(List<AreaEntity> areaEntities, AreaEntity newArea) {
        areaEntities.forEach(o -> {
            if (dateUtilsComponent.areNullablePeriodsOverlapping(o.getStartDate(), o.getEndDate(),
                    newArea.getStartDate(), newArea.getEndDate())) {
                throw new IncorrectDateException("Incorrect dates.");
            }
        });
    }

    private AreaEntity findEarliestDateArea(List<AreaEntity> areaEntities) {
        return areaEntities.stream()
                .min((o1, o2) -> {

                    if (o1.getStartDate() == null) {
                        return -1;
                    }

                    if (o2.getStartDate() == null) {
                        return 1;
                    }

                    return o1.getStartDate().compareTo(o2.getStartDate());
                })
                .orElseThrow();
    }

    private AreaEntity findLatestDateArea(List<AreaEntity> areaEntities) {
        return areaEntities.stream()
                .max((o1, o2) -> {

                    if (o1.getEndDate() == null) {
                        return 1;
                    }

                    if (o2.getEndDate() == null) {
                        return -1;
                    }

                    return o1.getEndDate().compareTo(o2.getEndDate());
                })
                .orElseThrow();
    }

    public AreaDto getArea(int id) {
        return converterService.areaEntityToDto(getAreaEntity(id));
    }
}
