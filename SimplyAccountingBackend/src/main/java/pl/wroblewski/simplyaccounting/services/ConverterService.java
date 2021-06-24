package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.entities.*;
import pl.wroblewski.simplyaccounting.models.common.PremisesDetails;
import pl.wroblewski.simplyaccounting.models.dtos.*;
import pl.wroblewski.simplyaccounting.models.responses.*;

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

    public AccountResponse accountEntityToResponse(AccountEntity accountEntity) {
        return modelMapper.map(accountEntity, AccountResponse.class);
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

    public PremisesTypeEntity premisesTypeDtoToEntity(PremisesTypeDto premisesTypeDto) {
        return modelMapper.map(premisesTypeDto, PremisesTypeEntity.class);
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

    public PremisesInfo premisesEntityToInfo(PremisesEntity premisesEntity) {
        return modelMapper.map(premisesEntity, PremisesInfo.class);
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

    public LandlordDetailsResponse landlordEntityToDetailsResponse(LandlordEntity landlordEntity) {
        return modelMapper.map(landlordEntity, LandlordDetailsResponse.class);
    }

    public ContractorDto contractorEntityToDto(ContractorEntity contractorEntity) {
        return modelMapper.map(contractorEntity, ContractorDto.class);
    }

    public ContractorEntity contractorDtoToEntity(ContractorDto contractorDto) {
        return modelMapper.map(contractorDto, ContractorEntity.class);
    }

    public AreaEntity areaDtoToEntity(AreaDto areaDto) {
        return modelMapper.map(areaDto, AreaEntity.class);
    }

    public AreaDto areaEntityToDto(AreaEntity areaEntity) {
        return modelMapper.map(areaEntity, AreaDto.class);
    }

    public AreaResponse areaEntityToResponse(AreaEntity areaEntity) {
        return modelMapper.map(areaEntity, AreaResponse.class);
    }

    public PeopleNumberEntity peopleNumberDtoToEntity(PeopleNumberDto peopleNumberDto) {
        return modelMapper.map(peopleNumberDto, PeopleNumberEntity.class);
    }

    public PeopleNumberDto peopleNumberEntityToDto(PeopleNumberEntity peopleNumberEntity) {
        return modelMapper.map(peopleNumberEntity, PeopleNumberDto.class);
    }

    public LandlordPaymentDto landlordPaymentEntityToDto(LandlordPaymentEntity landlordPaymentEntity) {
        return modelMapper.map(landlordPaymentEntity, LandlordPaymentDto.class);
    }

    public LandlordPaymentEntity landlordPaymentDtoToEntity(LandlordPaymentDto landlordPaymentDto) {
        return modelMapper.map(landlordPaymentDto, LandlordPaymentEntity.class);
    }

    public LandlordPaymentResponse landlordPaymentEntityToResponse(LandlordPaymentEntity landlordPaymentEntity) {
        return modelMapper.map(landlordPaymentEntity, LandlordPaymentResponse.class);
    }

    public ContractorPaymentEntity contractorPaymentDtoToEntity(ContractorPaymentDto contractorPaymentDto) {
        return modelMapper.map(contractorPaymentDto, ContractorPaymentEntity.class);
    }

    public ContractorPaymentDto contractorPaymentEntityToDto(ContractorPaymentEntity contractorPaymentEntity) {
        return modelMapper.map(contractorPaymentEntity, ContractorPaymentDto.class);
    }

    public ContractorPaymentResponse contractorPaymentEntityToResponse(ContractorPaymentEntity contractorPaymentEntity) {
        return modelMapper.map(contractorPaymentEntity, ContractorPaymentResponse.class);
    }

    public PremisesLandlordDto premisesLandlordEntityToDto(PremisesLandlordEntity premisesLandlordEntity) {
        return modelMapper.map(premisesLandlordEntity, PremisesLandlordDto.class);
    }

    public PremisesLandlordResponse premisesLandlordEntityToResponse(PremisesLandlordEntity premisesLandlordEntity) {
        return modelMapper.map(premisesLandlordEntity, PremisesLandlordResponse.class);
    }

    public PremisesLandlordEntity premisesLandlordDtoToEntity(PremisesLandlordDto premisesLandlordDto) {
        return modelMapper.map(premisesLandlordDto, PremisesLandlordEntity.class);
    }

    public InvoiceTypeDto invoiceTypeEntityToDto(InvoiceTypeEntity invoiceTypeEntity) {
        return modelMapper.map(invoiceTypeEntity, InvoiceTypeDto.class);
    }

    public InvoiceTypeEntity invoiceTypeDtoToEntity(InvoiceTypeDto invoiceTypeDto) {
        return modelMapper.map(invoiceTypeDto, InvoiceTypeEntity.class);
    }

    public InvoiceDto invoiceEntityToDto(InvoiceEntity invoiceEntity) {
        return modelMapper.map(invoiceEntity, InvoiceDto.class);
    }

    public InvoiceEntity invoiceDtoToEntity(InvoiceDto invoiceDto) {
        return modelMapper.map(invoiceDto, InvoiceEntity.class);
    }

    public OpeningBalanceDto openingBalanceEntityToDto(OpeningBalanceEntity openingBalanceEntity) {
        return modelMapper.map(openingBalanceEntity, OpeningBalanceDto.class);
    }

    public OpeningBalanceResponse openingBalanceEntityToResponse(OpeningBalanceEntity openingBalanceEntity) {
        return modelMapper.map(openingBalanceEntity, OpeningBalanceResponse.class);
    }

    public OpeningBalanceEntity openingBalanceDtoToEntity(OpeningBalanceDto openingBalanceDto) {
        return modelMapper.map(openingBalanceDto, OpeningBalanceEntity.class);
    }

    public ChargeTypeEntity chargeTypeDtoToEntity(ChargeTypeDto chargeTypeDto) {
        return modelMapper.map(chargeTypeDto, ChargeTypeEntity.class);
    }

    public ChargeTypeDto chargeTypeEntityToDto(ChargeTypeEntity chargeTypeEntity) {
        return modelMapper.map(chargeTypeEntity, ChargeTypeDto.class);
    }

    public RateDto rateEntityToDto(RateEntity rateEntity) {
        return modelMapper.map(rateEntity, RateDto.class);
    }

    public RateEntity rateDtoToEntity(RateDto rateDto) {
        return modelMapper.map(rateDto, RateEntity.class);
    }

    public RateResponse rateEntityToResponse(RateEntity rateEntity) {
        return modelMapper.map(rateEntity, RateResponse.class);
    }

    public ConsumptionDto consumptionEntityToDto(ConsumptionEntity consumptionEntity) {
        return modelMapper.map(consumptionEntity, ConsumptionDto.class);
    }

    public ConsumptionEntity consumptionDtoToEntity(ConsumptionDto consumptionDto) {
        return modelMapper.map(consumptionDto, ConsumptionEntity.class);
    }

    public ConsumptionResponse consumptionEntityToResponse(ConsumptionEntity consumptionEntity) {
        return modelMapper.map(consumptionEntity, ConsumptionResponse.class);
    }

    public AdvanceDto advanceEntityToDto(AdvanceEntity advanceEntity) {
        return modelMapper.map(advanceEntity, AdvanceDto.class);
    }

    public AdvancePremisesResponse advanceEntityToResponse(AdvanceEntity advanceEntity) {
        return modelMapper.map(advanceEntity, AdvancePremisesResponse.class);
    }

    public AdvanceEntity advanceDtoToEntity(AdvanceDto advanceDto) {
        return modelMapper.map(advanceDto, AdvanceEntity.class);
    }

    public AreaTypeDto areaTypeEntityToDto(AreaTypeEntity areaTypeEntity) {
        return modelMapper.map(areaTypeEntity, AreaTypeDto.class);
    }

    public AreaTypeEntity areaTypeDtoToEntity(AreaTypeDto areaTypeDto) {
        return modelMapper.map(areaTypeDto, AreaTypeEntity.class);
    }

    public ChargeElementDto chargeElementEntityToDto(ChargeElementEntity chargeElementEntity) {
        return modelMapper.map(chargeElementEntity, ChargeElementDto.class);
    }

    public ChargeElementEntity chargeElementDtoToEntity(ChargeElementDto chargeElementDto) {
        return modelMapper.map(chargeElementDto, ChargeElementEntity.class);
    }

    public ChargeElementResponse chargeElementEntityToResponse(ChargeElementEntity chargeElementEntity) {
        return modelMapper.map(chargeElementEntity, ChargeElementResponse.class);
    }

    public PremisesDetails premisesEntityToDetails(PremisesEntity premisesEntity) {
        return modelMapper.map(premisesEntity, PremisesDetails.class);
    }

    public InvoiceResponse invoiceEntityToResponse(InvoiceEntity invoiceEntity) {
        return modelMapper.map(invoiceEntity, InvoiceResponse.class);
    }

}
