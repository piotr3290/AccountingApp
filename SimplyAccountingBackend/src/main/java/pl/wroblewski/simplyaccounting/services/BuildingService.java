package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.entities.BuildingEntity;
import pl.wroblewski.simplyaccounting.db.repositories.BuildingRepository;
import pl.wroblewski.simplyaccounting.exceptions.ObjectNotFoundException;
import pl.wroblewski.simplyaccounting.models.dtos.BuildingDto;
import pl.wroblewski.simplyaccounting.models.responses.BuildingResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BuildingService {

    private final BuildingRepository buildingRepository;
    private final CooperativeService cooperativeService;
    private final ConverterService converterService;

    public BuildingDto createBuilding(BuildingDto buildingDto) {
        cooperativeService.checkCooperativeExists(buildingDto.getCooperativeId());
        return converterService.buildingEntityToDto(buildingRepository.save(converterService.buildingDtoToEntity(buildingDto)));
    }

    public BuildingDto updateBuilding(BuildingDto building) {
        return converterService.buildingEntityToDto(
                buildingRepository.save(getBuildingEntity(building.getId())
                        .toBuilder()
                        .city(building.getCity())
                        .houseNumber(building.getHouseNumber())
                        .postalCode(building.getPostalCode())
                        .street(building.getStreet())
                        .build()));
    }

    private BuildingEntity getBuildingEntity(int id) {
        return buildingRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cannot find building."));
    }

    public BuildingDto getBuilding(int id) {
        return converterService.buildingEntityToDto(getBuildingEntity(id));
    }

    public BuildingResponse getBuildingWithPremises(int id) {
        return converterService.buildingEntityToResponse(getBuildingEntity(id));
    }

    public void checkBuildingExists(Integer buildingId) {
        getBuildingEntity(buildingId);
    }

    public List<BuildingDto> getAllBuildingsForCooperative(Integer id) {
        return buildingRepository.findAllByCooperative_IdOrderByHouseNumber(id)
                .stream()
                .map(converterService::buildingEntityToDto)
                .collect(Collectors.toList());
    }
}
