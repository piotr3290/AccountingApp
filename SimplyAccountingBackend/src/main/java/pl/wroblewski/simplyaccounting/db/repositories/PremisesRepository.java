package pl.wroblewski.simplyaccounting.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wroblewski.simplyaccounting.db.entities.PremisesEntity;

import java.util.List;

@Repository
public interface PremisesRepository extends JpaRepository<PremisesEntity, Integer> {

    List<PremisesEntity> findAllByBuilding_Cooperative_Id(Integer cooperativeId);

    List<PremisesEntity> findAllByBuilding_Id(Integer buildingId);

    List<PremisesEntity> findAllByBuilding_IdOrderByPremisesNumber(Integer buildingId);

}
