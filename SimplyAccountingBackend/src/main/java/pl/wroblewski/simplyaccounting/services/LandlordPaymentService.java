package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.db.entities.LandlordPaymentEntity;
import pl.wroblewski.simplyaccounting.db.repositories.LandlordPaymentRepository;
import pl.wroblewski.simplyaccounting.exceptions.ObjectNotFoundException;
import pl.wroblewski.simplyaccounting.models.dtos.LandlordPaymentDto;
import pl.wroblewski.simplyaccounting.models.responses.LandlordPaymentResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LandlordPaymentService {

    private final LandlordPaymentRepository landlordPaymentRepository;
    private final ConverterService converterService;
    private final LandlordService landlordService;
    private final PremisesService premisesService;
    private final CooperativeService cooperativeService;

    public LandlordPaymentDto getLandlordPayment(int id) {
        return converterService.landlordPaymentEntityToDto(getLandlordPaymentEntity(id));
    }

    private LandlordPaymentEntity getLandlordPaymentEntity(int id) {
        return landlordPaymentRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cannot find a payment."));
    }

    public void deleteLandlordPayment(int id) {
        landlordPaymentRepository.delete(getLandlordPaymentEntity(id));
    }

    public LandlordPaymentDto createLandlordPayment(LandlordPaymentDto landlordPayment) {
        landlordService.checkLandlordExists(landlordPayment.getLandlordId());
        premisesService.checkPremisesExists(landlordPayment.getPremisesId());
        return converterService.landlordPaymentEntityToDto(landlordPaymentRepository
                .save(converterService.landlordPaymentDtoToEntity(landlordPayment))
        );
    }

    public LandlordPaymentDto editLandlordPayment(LandlordPaymentDto landlordPayment) {
        return converterService.landlordPaymentEntityToDto(landlordPaymentRepository.save(
                getLandlordPaymentEntity(landlordPayment.getId())
                        .toBuilder()
                        .realizationDate(landlordPayment.getRealizationDate())
                        .value(landlordPayment.getValue())
                        .build())
        );
    }

    public List<LandlordPaymentResponse> getAllLandlordPaymentForYear(Integer cooperativeId, Short year) {
        cooperativeService.checkCooperativeExists(cooperativeId);
        return getAllLandlordPaymentEntityForYear(cooperativeId, year)
                .stream()
                .map(converterService::landlordPaymentEntityToResponse)
                .collect(Collectors.toList());
    }

    List<LandlordPaymentDto> getAllLandlordPaymentForCooperativeAndYear(Integer cooperativeId, Short year){
        return getAllLandlordPaymentEntityForYear(cooperativeId, year)
                .stream()
                .map(converterService::landlordPaymentEntityToDto)
                .collect(Collectors.toList());
    }

    List<LandlordPaymentEntity> getAllLandlordPaymentEntityForYear(Integer cooperativeId, Short year) {
        return landlordPaymentRepository.findAllByYear(cooperativeId, year);
    }
}
