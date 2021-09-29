package pl.wroblewski.simplyaccounting;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import pl.wroblewski.simplyaccounting.db.entities.*;
import pl.wroblewski.simplyaccounting.models.responses.PremisesInBuilding;
import pl.wroblewski.simplyaccounting.models.dtos.*;
import pl.wroblewski.simplyaccounting.models.responses.PremisesInfo;
import pl.wroblewski.simplyaccounting.models.responses.PremisesResponse;
import pl.wroblewski.simplyaccounting.models.responses.RateResponse;

import java.util.List;
import java.util.Locale;


@Configuration
public class ApplicationConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(landlordDtoLandlordEntityPropertyMap());

        modelMapper.addMappings(premisesDtoPremisesEntityPropertyMap());

        modelMapper.addMappings(cooperativeDtoCooperativeEntityPropertyMap());

        modelMapper.addMappings(buildingDtoBuildingEntityPropertyMap());

        modelMapper.addMappings(contractorDtoContractorEntityPropertyMap());

        modelMapper.addMappings(areaDtoAreaEntityPropertyMap());

        modelMapper.addMappings(landlordPaymentDtoLandlordPaymentEntityPropertyMap());

        modelMapper.addMappings(contractorPaymentDtoContractorPaymentEntityPropertyMap());

        modelMapper.addMappings(premisesLandlordDtoPremisesLandlordEntityPropertyMap());

        modelMapper.addMappings(invoiceTypeDtoInvoiceTypeEntityPropertyMap());

        modelMapper.addMappings(invoiceDtoInvoiceEntityPropertyMap());

        modelMapper.addMappings(openingBalanceDtoOpeningBalanceEntityPropertyMap());

        modelMapper.addMappings(premisesTypeDtoPremisesTypeEntityPropertyMap());

        modelMapper.addMappings(chargeTypeDtoChargeTypeEntityPropertyMap());

        modelMapper.addMappings(rateDtoRateEntityPropertyMap());

        modelMapper.addMappings(consumptionDtoConsumptionEntityPropertyMap());

        modelMapper.addMappings(advanceDtoAdvanceEntityPropertyMap());

        modelMapper.addMappings(areaTypeDtoAreaTypeEntityPropertyMap());

        modelMapper.addMappings(chargeElementDtoChargeElementEntityPropertyMap());

        modelMapper.addMappings(rateEntityRateDtoPropertyMap());

        modelMapper.addMappings(rateEntityRateResponsePropertyMap());

        modelMapper.addMappings(premisesEntityPremisesInBuildingPropertyMap());

        modelMapper.addMappings(premisesEntityPremisesResponsePropertyMap());

        modelMapper.addMappings(premisesEntityPremisesInfoPropertyMap());

        return modelMapper;
    }

    @Bean
    public PropertyMap<LandlordDto, LandlordEntity> landlordDtoLandlordEntityPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                skip(destination.getAccount());
            }
        };
    }

    @Bean
    public PropertyMap<PremisesDto, PremisesEntity> premisesDtoPremisesEntityPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        };
    }

    @Bean
    public PropertyMap<CooperativeDto, CooperativeEntity> cooperativeDtoCooperativeEntityPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                skip(destination.getAccount());
            }
        };
    }

    @Bean
    public PropertyMap<AreaDto, AreaEntity> areaDtoAreaEntityPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        };
    }

    @Bean
    public PropertyMap<ContractorDto, ContractorEntity> contractorDtoContractorEntityPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                skip(destination.getAccount());
            }
        };
    }

    @Bean
    public PropertyMap<BuildingDto, BuildingEntity> buildingDtoBuildingEntityPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        };
    }

    @Bean
    public PropertyMap<PeopleNumberDto, PeopleNumberEntity> peopleNumberDtoPeopleNumberEntityPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        };
    }

    @Bean
    public PropertyMap<LandlordPaymentDto, LandlordPaymentEntity> landlordPaymentDtoLandlordPaymentEntityPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        };
    }

    @Bean
    public PropertyMap<ContractorPaymentDto, ContractorPaymentEntity> contractorPaymentDtoContractorPaymentEntityPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        };
    }

    @Bean
    public PropertyMap<PremisesLandlordDto, PremisesLandlordEntity> premisesLandlordDtoPremisesLandlordEntityPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        };
    }

    @Bean
    public PropertyMap<InvoiceTypeDto, InvoiceTypeEntity> invoiceTypeDtoInvoiceTypeEntityPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        };
    }

    @Bean
    public PropertyMap<InvoiceDto, InvoiceEntity> invoiceDtoInvoiceEntityPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        };
    }

    @Bean
    public PropertyMap<OpeningBalanceDto, OpeningBalanceEntity> openingBalanceDtoOpeningBalanceEntityPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        };
    }

    @Bean
    public PropertyMap<PremisesTypeDto, PremisesTypeEntity> premisesTypeDtoPremisesTypeEntityPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        };
    }

    @Bean
    public PropertyMap<ChargeTypeDto, ChargeTypeEntity> chargeTypeDtoChargeTypeEntityPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        };
    }

    @Bean
    public PropertyMap<RateDto, RateEntity> rateDtoRateEntityPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        };
    }

    @Bean
    public PropertyMap<RateEntity, RateDto> rateEntityRateDtoPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setBuildingId(source.getBuildingId());
                map().setPremisesId(source.getPremisesId());
                map().setCooperativeId(source.getCooperativeId());
                map().setHierarchyLevel(source.getHierarchyLevel());
            }
        };
    }

    @Bean
    public PropertyMap<ConsumptionDto, ConsumptionEntity> consumptionDtoConsumptionEntityPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        };
    }

    @Bean
    public PropertyMap<AdvanceDto, AdvanceEntity> advanceDtoAdvanceEntityPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        };
    }

    @Bean
    public PropertyMap<AreaTypeDto, AreaTypeEntity> areaTypeDtoAreaTypeEntityPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        };
    }

    @Bean
    public PropertyMap<ChargeElementDto, ChargeElementEntity> chargeElementDtoChargeElementEntityPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        };
    }

    @Bean
    public PropertyMap<RateEntity, RateResponse> rateEntityRateResponsePropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getBuilding().getId());
                skip(destination.getCooperative().getId());
                skip(destination.getPremises().getId());
            }
        };
    }

    @Bean
    public Converter<List<PremisesLandlordEntity>, String> premisesLandlordConverter() {
        return mappingContext -> mappingContext.getSource().isEmpty()
                ? null
                : mappingContext.getSource()
                .get(mappingContext.getSource().size() - 1)
                .getLandlord()
                .getFullName();
    }

    @Bean
    public PropertyMap<PremisesEntity, PremisesInBuilding> premisesEntityPremisesInBuildingPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                using(premisesLandlordConverter()).map(source.getPremisesLandlords()).setLandlordName(null);
            }
        };
    }

    @Bean
    PropertyMap<PremisesEntity, PremisesResponse> premisesEntityPremisesResponsePropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setCooperativeName(source.getBuilding().getCooperative().getName());
            }
        };
    }

    @Bean
    PropertyMap<PremisesEntity, PremisesInfo> premisesEntityPremisesInfoPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setCooperativeName(source.getBuilding().getCooperative().getName());
            }
        };
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }
}
