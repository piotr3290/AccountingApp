package pl.wroblewski.simplyaccounting;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wroblewski.simplyaccounting.db.entities.BuildingEntity;
import pl.wroblewski.simplyaccounting.db.entities.CooperativeEntity;
import pl.wroblewski.simplyaccounting.db.entities.LandlordEntity;
import pl.wroblewski.simplyaccounting.db.entities.PremisesEntity;
import pl.wroblewski.simplyaccounting.models.dtos.BuildingDto;
import pl.wroblewski.simplyaccounting.models.dtos.CooperativeDto;
import pl.wroblewski.simplyaccounting.models.dtos.LandlordDto;
import pl.wroblewski.simplyaccounting.models.dtos.PremisesDto;


@Configuration
public class ApplicationConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<LandlordDto, LandlordEntity>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });

        modelMapper.addMappings(new PropertyMap<PremisesDto, PremisesEntity>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });

        modelMapper.addMappings(new PropertyMap<CooperativeDto, CooperativeEntity>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });

        modelMapper.addMappings(new PropertyMap<BuildingDto, BuildingEntity>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });

        return modelMapper;
    }
}
