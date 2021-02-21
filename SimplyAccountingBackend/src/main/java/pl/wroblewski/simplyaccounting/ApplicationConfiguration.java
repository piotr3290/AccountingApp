package pl.wroblewski.simplyaccounting;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import pl.wroblewski.simplyaccounting.db.entities.*;
import pl.wroblewski.simplyaccounting.models.dtos.*;

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

        return modelMapper;
    }

    @Bean
    public PropertyMap<LandlordDto, LandlordEntity> landlordDtoLandlordEntityPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getId());
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
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }
}
