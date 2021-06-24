package pl.wroblewski.simplyaccounting.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wroblewski.simplyaccounting.db.entities.ConsumptionEntity;

import java.util.List;

@Repository
public interface ConsumptionRepository extends JpaRepository<ConsumptionEntity, Integer> {
    List<ConsumptionEntity> findAllByPremises_IdAndChargeType_IdOrderByStartDate(Integer premisesId, Integer chargeTypeId);
    List<ConsumptionEntity> findAllByPremises_IdOrderByStartDate(Integer premisesId);
}
