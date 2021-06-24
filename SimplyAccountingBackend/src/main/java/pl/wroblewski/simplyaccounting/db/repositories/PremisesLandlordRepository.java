package pl.wroblewski.simplyaccounting.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wroblewski.simplyaccounting.db.entities.PremisesLandlordEntity;

import java.util.List;

@Repository
public interface PremisesLandlordRepository extends JpaRepository<PremisesLandlordEntity, Integer> {
    List<PremisesLandlordEntity> findAllByPremises_IdOrderByStartDate(Integer premisesId);
}
