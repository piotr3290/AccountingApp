package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.entities.*;
import pl.wroblewski.simplyaccounting.models.dtos.*;
import pl.wroblewski.simplyaccounting.models.responses.BuildingResponse;
import pl.wroblewski.simplyaccounting.models.responses.CooperativeResponse;
import pl.wroblewski.simplyaccounting.models.responses.LandlordResponse;
import pl.wroblewski.simplyaccounting.models.responses.PremisesResponse;

@Service
@AllArgsConstructor
public class ConverterService {
    private final ModelMapper modelMapper;

    public CooperativeDto cooperativeEntityToDto(CooperativeEntity cooperativeEntity) {
        return modelMapper.map(cooperativeEntity, CooperativeDto.class);
    }

    public AccountTypeDto accountTypeEntityToDto(AccountTypeEntity accountTypeEntity) {
        return modelMapper.map(accountTypeEntity, AccountTypeDto.class);
    }

    public AccountDto accountEntityToDto(AccountEntity accountEntity) {
        return modelMapper.map(accountEntity, AccountDto.class);
    }

    public CooperativeEntity cooperativeDtoToEntity(CooperativeDto cooperativeDto) {
        return modelMapper.map(cooperativeDto, CooperativeEntity.class);
    }

    public CooperativeResponse cooperativeEntityToResponse(CooperativeEntity cooperativeEntity) {
        return modelMapper.map(cooperativeEntity, CooperativeResponse.class);
    }

    public BuildingEntity buildingDtoToEntity(BuildingDto buildingDto) {
        return modelMapper.map(buildingDto, BuildingEntity.class);
    }

    public BuildingDto buildingEntityToDto(BuildingEntity buildingEntity) {
        return modelMapper.map(buildingEntity, BuildingDto.class);
    }

    public PremisesTypeDto premisesTypeEntityToDto(PremisesTypeEntity premisesTypeEntity) {
        return modelMapper.map(premisesTypeEntity, PremisesTypeDto.class);
    }

    public BuildingResponse buildingEntityToResponse(BuildingEntity buildingEntity) {
        return modelMapper.map(buildingEntity, BuildingResponse.class);
    }

    public PremisesDto premisesEntityToDto(PremisesEntity premisesEntity) {
        return modelMapper.map(premisesEntity, PremisesDto.class);
    }

    public PremisesEntity premisesDtoToEntity(PremisesDto premisesDto) {
        return modelMapper.map(premisesDto, PremisesEntity.class);
    }

    public PremisesResponse premisesEntityToResponse(PremisesEntity premisesEntity) {
        return modelMapper.map(premisesEntity, PremisesResponse.class);
    }

    public LandlordResponse landlordEntityToResponse(LandlordEntity landlordEntity) {
        return modelMapper.map(landlordEntity, LandlordResponse.class);
    }

    public LandlordEntity landlordDtoToEntity(LandlordDto landlordDto) {
        return modelMapper.map(landlordDto, LandlordEntity.class);
    }

    public LandlordDto landlordEntityToDto(LandlordEntity landlordEntity) {
        return modelMapper.map(landlordEntity, LandlordDto.class);
    }

    public UserEntity userDtoToEntity(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }
}
