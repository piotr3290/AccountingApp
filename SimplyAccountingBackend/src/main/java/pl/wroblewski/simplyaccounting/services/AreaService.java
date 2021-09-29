package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.entities.AreaEntity;
import pl.wroblewski.simplyaccounting.db.repositories.AreaRepository;
import pl.wroblewski.simplyaccounting.exceptions.ObjectNotFoundException;
import pl.wroblewski.simplyaccounting.models.dtos.AreaDto;
import pl.wroblewski.simplyaccounting.models.responses.AreaResponse;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AreaService {

    private final AreaRepository areaRepository;
    private final ConverterService converterService;
    private final DatePeriodService datePeriodService;
    private final AreaTypeService areaTypeService;

    public List<AreaResponse> addNewAreaToPremises(AreaDto area) {
        var areaTypeEntity = areaTypeService.getAreaTypeEntity(area.getAreaTypeId());

        var areaEntity = converterService.areaDtoToEntity(area);
        areaEntity.setAreaType(areaTypeEntity);

        var premisesAreas = areaRepository.findAllByPremises_IdAndAreaType_IdOrderByStartDate(area.getPremisesId(), area.getAreaTypeId());
        savePremisesAreas(premisesAreas, areaEntity);

        return areaRepository.findAllByPremises_IdOrderByStartDate(area.getPremisesId())
                .stream()
                .map(converterService::areaEntityToResponse)
                .collect(Collectors.toList());
    }

    public List<AreaResponse> editArea(AreaDto area) {
        var areaEntity = getAreaEntity(area.getId())
                .toBuilder()
                .startDate(area.getStartDate())
                .endDate(area.getEndDate())
                .value(area.getValue())
                .build();

        var premisesAreas = areaRepository.findAllByPremises_IdAndAreaType_IdOrderByStartDate(area.getPremisesId(), area.getAreaTypeId());
        premisesAreas.remove(areaEntity);
        savePremisesAreas(premisesAreas, areaEntity);

        return areaRepository.findAllByPremises_IdOrderByStartDate(area.getPremisesId())
                .stream()
                .map(converterService::areaEntityToResponse)
                .collect(Collectors.toList());
    }

    public void deleteArea(int id) {
        areaRepository.delete(getAreaEntity(id));
    }

    private AreaEntity getAreaEntity(int id) {
        return areaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cannot find the area."));
    }

    private void savePremisesAreas(Collection<AreaEntity> areaEntities, AreaEntity newArea) {
        datePeriodService.save(areaEntities, newArea, areaRepository);
    }

    public AreaDto getArea(int id) {
        return converterService.areaEntityToDto(getAreaEntity(id));
    }
}
